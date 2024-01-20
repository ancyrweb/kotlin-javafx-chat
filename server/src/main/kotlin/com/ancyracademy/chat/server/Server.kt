package com.ancyracademy.chat.server

import com.ancyracademy.chat.protocol.ProtocolMessage
import com.ancyracademy.chat.protocol.commands.GetMessagesCommand
import com.ancyracademy.chat.protocol.commands.GetUsersCommand
import com.ancyracademy.chat.protocol.commands.SendMessageCommand
import com.ancyracademy.chat.protocol.events.NewMessageEvent
import com.ancyracademy.chat.protocol.events.NewUserEvent
import com.ancyracademy.chat.protocol.events.UserDisconnectedEvent
import com.ancyracademy.chat.protocol.responses.MessageListResponse
import com.ancyracademy.chat.protocol.responses.UserListResponse
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Executors

class Server {
  private val executorService = Executors.newFixedThreadPool(5)
  private val clients = mutableListOf<ClientHandler>()
  private var clientIndex = 0
  private val messages = mutableListOf<Message>()

  data class Message(
    var author: String,
    var message: String
  )
  
  fun start() {
    val port = 19999
    val serverSocket = ServerSocket(port)

    println("Server listening on port $port")

    while (true) {
      val socket = serverSocket.accept()
      println("New client connected: ${socket.inetAddress.hostAddress}")

      val clientInfo = ClientInfo("anonymous-${clientIndex++}")
      val client =
        ClientHandler(socket, clientInfo)

      broadcast(NewUserEvent(clientInfo.name))

      clients.add(client)
      executorService.execute(client)
    }
  }

  private fun broadcast(message: ProtocolMessage) {
    clients.forEach { it.send(message) }
  }

  inner class ClientInfo(public val name: String) {}

  inner class ClientHandler(
    private val socket: Socket,
    private val info: ClientInfo
  ) : Runnable {
    private val reader: ObjectInputStream =
      ObjectInputStream(socket.getInputStream())
    private val writer: ObjectOutputStream =
      ObjectOutputStream(socket.getOutputStream())

    override fun run() {
      try {
        var rawMessage: Any
        while (reader.readObject().also { rawMessage = it } != null) {
          when (rawMessage) {
            is SendMessageCommand -> {
              val command = rawMessage as SendMessageCommand
              messages.add(
                Message(
                  info.name,
                  command.message
                )
              )

              broadcast(
                NewMessageEvent(
                  info.name,
                  command.message
                )
              )

            }

            is GetUsersCommand -> {
              val users =
                clients.map { UserListResponse.User(it.info.name) }
                  .toTypedArray()

              val response = UserListResponse(users)
              send(response)
            }

            is GetMessagesCommand -> {
              val messages =
                messages.map {
                  MessageListResponse.Message(
                    it.author,
                    it.message
                  )
                }
                  .toTypedArray()

              val response = MessageListResponse(messages)
              send(response)
            }
          }
        }
      } catch (e: IOException) {
        println("Client disconnected: ${socket.inetAddress.hostAddress}")
        clients.remove(this)
        socket.close()

        broadcast(UserDisconnectedEvent(info.name))
      }
    }

    fun send(message: ProtocolMessage) {
      writer.writeObject(message)
    }
  }
}
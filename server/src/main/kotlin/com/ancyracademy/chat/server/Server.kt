package com.ancyracademy.chat.server

import com.ancyracademy.chat.protocol.ProtocolMessage
import com.ancyracademy.chat.protocol.commands.GetMessagesCommand
import com.ancyracademy.chat.protocol.commands.GetUsersCommand
import com.ancyracademy.chat.protocol.commands.SendMessageCommand
import com.ancyracademy.chat.protocol.events.NewMessageEvent
import com.ancyracademy.chat.protocol.events.NewUserEvent
import com.ancyracademy.chat.protocol.events.UserDisconnectedEvent
import com.ancyracademy.chat.server.core.App
import com.ancyracademy.chat.server.core.Message
import com.ancyracademy.chat.server.core.User
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.Volatile
import kotlin.concurrent.thread

class Server {
  private val executorService = Executors.newFixedThreadPool(5)
  private val clients = mutableListOf<ClientConnection>()

  private val app = App()
  private var serverSocket: ServerSocket? = null

  @Volatile
  private var running = false

  init {
    // Note : the server is running at all times, so we can safely add a listener
    // Otherwise we would have to clear the listener when the server is stopped.
    app.addListener(object : App.Listener {
      override fun onNewMessage(message: Message) {
        broadcast(
          NewMessageEvent(
            message.author.id.toString(),
            message.content,
          )
        )
      }

      override fun onNewUser(user: User) {
        broadcast(NewUserEvent(user.id.toString()))
      }

      override fun onUserDisconnected(user: User) {
        broadcast(UserDisconnectedEvent(user.id.toString()))
      }
    })
  }

  fun start() {
    val port = 19999
    running = true

    thread {
      serverSocket = ServerSocket(port)
      println("Server listening on port $port")

      while (running) {
        val socket = serverSocket?.accept() ?: continue

        println("New client connected: ${socket.inetAddress.hostAddress}")

        val user = app.connect()
        val client =
          ClientConnection(socket, user)

        clients.add(client)
        executorService.execute(client)
      }
    }
  }

  fun stop() {
    running = false
    serverSocket?.close()

    executorService.shutdown()

    try {
      if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
        executorService.shutdownNow()
      }
    } catch (e: InterruptedException) {
      executorService.shutdownNow()
    }
  }

  private fun broadcast(message: ProtocolMessage) {
    clients.forEach { it.send(message) }
  }

  inner class ClientConnection(
    private val socket: Socket,
    private val user: User
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
              app.handle(rawMessage as SendMessageCommand, user)
            }

            is GetUsersCommand -> {
              send(app.handle(rawMessage as GetUsersCommand))
            }

            is GetMessagesCommand -> {
              send(app.handle(rawMessage as GetMessagesCommand))
            }
          }
        }
      } catch (e: IOException) {
        println("Client disconnected: ${socket.inetAddress.hostAddress}")
        clients.remove(this)
        app.disconnect(user)
        socket.close()
      }
    }

    fun send(message: ProtocolMessage) {
      writer.writeObject(message)
    }
  }
}
package com.ancyracademy.chat.client

import com.ancyracademy.chat.protocol.ProtocolMessage
import com.ancyracademy.chat.protocol.events.NewMessageEvent
import com.ancyracademy.chat.protocol.events.NewUserEvent
import com.ancyracademy.chat.protocol.events.UserDisconnectedEvent
import com.ancyracademy.chat.protocol.responses.MessageListResponse
import com.ancyracademy.chat.protocol.responses.UserListResponse
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket
import kotlin.concurrent.thread

class Client() {
  private lateinit var socket: Socket
  private lateinit var reader: ObjectInputStream
  private lateinit var writer: ObjectOutputStream

  init {
    println("Client initialized")
  }

  fun connect(serverAddress: String, serverPort: Int) {
    socket = Socket(serverAddress, serverPort)
    writer = ObjectOutputStream(socket.getOutputStream())

    thread {
      reader = ObjectInputStream(socket.getInputStream())

      var message: Any
      while (reader.readObject().also { message = it } != null) {
        when (message) {
          is NewMessageEvent -> {
            val protocolMessage = message as NewMessageEvent
            println("${protocolMessage.author}: ${protocolMessage.message}")
          }

          is NewUserEvent -> {
            val protocolMessage = message as NewUserEvent
            println("${protocolMessage.name} joined the chat")
          }

          is UserDisconnectedEvent -> {
            val protocolMessage = message as UserDisconnectedEvent
            println("${protocolMessage.name} left the chat")
          }

          is MessageListResponse -> {
            val protocolMessage = message as MessageListResponse
            protocolMessage.list.forEach {
              println("${it.author}: ${it.message}")
            }
          }

          is UserListResponse -> {
            val protocolMessage = message as UserListResponse
            protocolMessage.list.forEach {
              println(it)
            }
          }
        }
      }
    }
  }

  fun send(message: ProtocolMessage) {
    writer.writeObject(message)
  }
}
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
  interface Listener {
    fun onNewMessage(message: NewMessageEvent)
    fun onNewUser(user: NewUserEvent)
    fun onUserDisconnected(user: UserDisconnectedEvent)
    fun onMessageList(messageList: MessageListResponse)
    fun onUserList(userList: UserListResponse)
  }

  private var listeners = mutableListOf<Listener>()

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
            listeners.forEach {
              it.onNewMessage(protocolMessage)
            }
          }

          is NewUserEvent -> {
            val protocolMessage = message as NewUserEvent
            listeners.forEach {
              it.onNewUser(protocolMessage)
            }
          }

          is UserDisconnectedEvent -> {
            val protocolMessage = message as UserDisconnectedEvent
            listeners.forEach {
              it.onUserDisconnected(protocolMessage)
            }
          }

          is MessageListResponse -> {
            val protocolMessage = message as MessageListResponse
            listeners.forEach {
              it.onMessageList(protocolMessage)
            }
          }

          is UserListResponse -> {
            val protocolMessage = message as UserListResponse
            listeners.forEach {
              it.onUserList(protocolMessage)
            }
          }
        }
      }
    }
  }

  fun send(message: ProtocolMessage) {
    writer.writeObject(message)
  }

  fun addListener(listener: Listener) {
    listeners.add(listener)
  }

  fun removeListener(listener: Listener) {
    listeners.remove(listener)
  }
}
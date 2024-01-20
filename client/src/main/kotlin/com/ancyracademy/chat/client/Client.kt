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

object Client {
  interface Listener {
    fun onNewMessage(message: NewMessageEvent)
    fun onNewUser(user: NewUserEvent)
    fun onUserDisconnected(user: UserDisconnectedEvent)
    fun onMessageList(messageList: MessageListResponse)
    fun onUserList(userList: UserListResponse)
  }

  private var listeners = mutableListOf<Listener>()
  private lateinit var socket: Socket
  private lateinit var writer: ObjectOutputStream
  private lateinit var receiver: MessageReceiverThread

  internal class MessageReceiverThread(private val socket: Socket) : Thread() {
    private var reader = ObjectInputStream(socket.getInputStream())
    private var running = true

    override fun run() {
      var message: Any? = null
      try {
        while (running && reader.readObject().also { message = it } != null) {
          when (message) {
            is NewMessageEvent -> {
              listeners.forEach {
                it.onNewMessage(message as NewMessageEvent)
              }
            }

            is NewUserEvent -> {
              listeners.forEach {
                it.onNewUser(message as NewUserEvent)
              }
            }

            is UserDisconnectedEvent -> {
              listeners.forEach {
                it.onUserDisconnected(message as UserDisconnectedEvent)
              }
            }

            is MessageListResponse -> {
              listeners.forEach {
                it.onMessageList(message as MessageListResponse)
              }
            }

            is UserListResponse -> {
              listeners.forEach {
                it.onUserList(message as UserListResponse)
              }
            }
          }
        }
      } catch (_: Exception) {
      }
    }

    fun disconnect() {
      running = false
      reader.close()
    }
  }

  fun connect(serverAddress: String, serverPort: Int) {
    socket = Socket(serverAddress, serverPort)
    writer = ObjectOutputStream(socket.getOutputStream())

    receiver = MessageReceiverThread(socket)
    receiver.start()
  }

  fun disconnect() {
    receiver.disconnect()
    writer.close()
    socket.close()
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
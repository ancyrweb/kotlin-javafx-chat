package com.ancyracademy.chat.client.client

import com.ancyracademy.chat.protocol.ProtocolMessage
import com.ancyracademy.chat.protocol.events.NewMessageEvent
import com.ancyracademy.chat.protocol.events.NewUserEvent
import com.ancyracademy.chat.protocol.events.UserDisconnectedEvent
import com.ancyracademy.chat.protocol.responses.MessageListResponse
import com.ancyracademy.chat.protocol.responses.UserListResponse

class GatewayObservable {
  interface Listener {
    fun onNewMessage(message: NewMessageEvent)
    fun onNewUser(user: NewUserEvent)
    fun onUserDisconnected(user: UserDisconnectedEvent)
    fun onMessageList(messageList: MessageListResponse)
    fun onUserList(userList: UserListResponse)
  }

  private var listeners = mutableListOf<Listener>()

  fun add(listener: Listener) {
    listeners.add(listener)
  }

  fun remove(listener: Listener) {
    listeners.remove(listener)
  }

  fun dispatch(event: ProtocolMessage) {
    when (event) {
      is NewMessageEvent -> {
        listeners.forEach { it.onNewMessage(event) }
      }

      is NewUserEvent -> {
        listeners.forEach { it.onNewUser(event) }
      }

      is UserDisconnectedEvent -> {
        listeners.forEach { it.onUserDisconnected(event) }
      }

      is MessageListResponse -> {
        listeners.forEach { it.onMessageList(event) }
      }

      is UserListResponse -> {
        listeners.forEach { it.onUserList(event) }
      }
    }

  }
}
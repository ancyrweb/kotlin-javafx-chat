package com.ancyracademy.chat.server.core

import com.ancyracademy.chat.protocol.commands.GetMessagesCommand
import com.ancyracademy.chat.protocol.commands.GetUsersCommand
import com.ancyracademy.chat.protocol.commands.SendMessageCommand
import com.ancyracademy.chat.protocol.responses.MessageListResponse
import com.ancyracademy.chat.protocol.responses.UserListResponse
import java.time.LocalDateTime
import java.util.*

class App {
  interface Listener {
    fun onNewMessage(message: Message)
    fun onNewUser(user: User)
    fun onUserDisconnected(user: User)
  }

  private val listeners = mutableListOf<Listener>()
  private val messages = MessageRepository()
  private val users = UserRepository()

  fun handle(command: SendMessageCommand, session: User): Message {
    val message = Message(
      session,
      command.message,
      LocalDateTime.now()
    )

    messages.add(
      message
    )

    listeners.forEach {
      it.onNewMessage(message)
    }

    return message
  }

  fun handle(command: GetUsersCommand): UserListResponse {
    val formattedUsers =
      users.getAll()
        .map { UserListResponse.User(it.id.toString(), it.username) }
        .toTypedArray()

    return UserListResponse(formattedUsers)
  }

  fun handle(command: GetMessagesCommand): MessageListResponse {
    val messages = messages.getAll().map {
      MessageListResponse.Message(
        it.author.username,
        it.content,
        it.date
      )
    }

    return MessageListResponse(messages.toTypedArray())
  }

  fun connect(): User {
    val user = User(UUID.randomUUID(), "anonymous")
    users.add(user)

    listeners.forEach {
      it.onNewUser(user)
    }

    return user
  }

  fun disconnect(user: User) {
    users.remove(user)

    listeners.forEach {
      it.onUserDisconnected(user)
    }
  }

  fun addListener(listener: Listener) {
    listeners.add(listener)
  }

  fun removeListener(listener: Listener) {
    listeners.remove(listener)
  }
}
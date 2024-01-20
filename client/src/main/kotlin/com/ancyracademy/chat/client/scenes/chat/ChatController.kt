package com.ancyracademy.chat.client.scenes.chat

import com.ancyracademy.chat.client.Client
import com.ancyracademy.chat.client.interfaces.Disposable
import com.ancyracademy.chat.protocol.commands.GetMessagesCommand
import com.ancyracademy.chat.protocol.events.NewMessageEvent
import com.ancyracademy.chat.protocol.events.NewUserEvent
import com.ancyracademy.chat.protocol.events.UserDisconnectedEvent
import com.ancyracademy.chat.protocol.responses.MessageListResponse
import com.ancyracademy.chat.protocol.responses.UserListResponse
import javafx.scene.control.TextArea

class ChatController : Disposable {
  private val root = TextArea()
  private val listener = object : Client.Listener {
    override fun onNewMessage(message: NewMessageEvent) {
      root.appendText("${message.author}: ${message.message}\n")
    }

    override fun onNewUser(user: NewUserEvent) {
      root.appendText("${user.name} joined the chat\n")
    }

    override fun onUserDisconnected(user: UserDisconnectedEvent) {
      root.appendText("${user.name} left the chat\n")
    }

    override fun onMessageList(messageList: MessageListResponse) {
      root.clear()
      messageList.list.forEach {
        root.appendText("${it.author}: ${it.message}\n")
      }
    }

    override fun onUserList(userList: UserListResponse) {

    }
  }

  init {
    root.isEditable = false
    Client.addListener(listener)
    Client.send(GetMessagesCommand())
  }

  fun getView() = root

  override fun dispose() {
    Client.removeListener(listener)
  }
}
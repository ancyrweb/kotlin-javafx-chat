package com.ancyracademy.chat.client.scenes.chat

import com.ancyracademy.chat.client.client.Gateway
import com.ancyracademy.chat.client.client.GatewayObservable
import com.ancyracademy.chat.client.interfaces.Disposable
import com.ancyracademy.chat.protocol.commands.GetMessagesCommand
import com.ancyracademy.chat.protocol.events.NewMessageEvent
import com.ancyracademy.chat.protocol.events.NewUserEvent
import com.ancyracademy.chat.protocol.events.UserDisconnectedEvent
import com.ancyracademy.chat.protocol.responses.MessageListResponse
import com.ancyracademy.chat.protocol.responses.UserListResponse
import javafx.application.Platform
import javafx.scene.control.TextArea

class ChatController : Disposable {
  private val root = TextArea()
  private val listener = object : GatewayObservable.Listener {
    override fun onNewMessage(message: NewMessageEvent) {
      Platform.runLater {
        root.appendText("${message.author}: ${message.message}\n")
      }
    }

    override fun onNewUser(user: NewUserEvent) {
      Platform.runLater {
        root.appendText("${user.name} joined the chat\n")
      }
    }

    override fun onUserDisconnected(user: UserDisconnectedEvent) {
      Platform.runLater {
        root.appendText("${user.name} left the chat\n")
      }
    }

    override fun onMessageList(messageList: MessageListResponse) {
      Platform.runLater {
        root.clear()
        root.appendText(messageList.list.joinToString("\n") {
          "${it.author}: ${it.message}"
        })
      }
    }

    override fun onUserList(userList: UserListResponse) {

    }
  }

  init {
    root.isEditable = false
    Gateway.addListener(listener)
    Gateway.send(GetMessagesCommand())
  }

  fun getView() = root

  override fun dispose() {
    Gateway.removeListener(listener)
  }
}
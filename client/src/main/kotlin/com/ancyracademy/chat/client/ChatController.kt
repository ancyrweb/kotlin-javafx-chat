package com.ancyracademy.chat.client

import com.ancyracademy.chat.protocol.commands.GetMessagesCommand
import com.ancyracademy.chat.protocol.events.NewMessageEvent
import com.ancyracademy.chat.protocol.events.NewUserEvent
import com.ancyracademy.chat.protocol.events.UserDisconnectedEvent
import com.ancyracademy.chat.protocol.responses.MessageListResponse
import com.ancyracademy.chat.protocol.responses.UserListResponse
import javafx.scene.control.TextArea

class ChatController(private val client: Client) {
  var root = TextArea()

  init {
    root.isEditable = false

    client.addListener(
      object : Client.Listener {
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
    )

    client.send(GetMessagesCommand())
  }

  fun getView() = root
}
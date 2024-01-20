package com.ancyracademy.chat.client

import com.ancyracademy.chat.protocol.events.NewMessageEvent
import com.ancyracademy.chat.protocol.events.NewUserEvent
import com.ancyracademy.chat.protocol.events.UserDisconnectedEvent
import com.ancyracademy.chat.protocol.responses.MessageListResponse
import com.ancyracademy.chat.protocol.responses.UserListResponse
import javafx.scene.control.ListView

class UsersListController(private val client: Client) {
  val root: ListView<String> = ListView()

  init {
    client.addListener(object : Client.Listener {
      override fun onNewMessage(message: NewMessageEvent) {}
      override fun onNewUser(user: NewUserEvent) {
        root.items.add(user.name)
      }

      override fun onUserDisconnected(user: UserDisconnectedEvent) {
        root.items.remove(user.name)
      }

      override fun onMessageList(messageList: MessageListResponse) {}
      override fun onUserList(userList: UserListResponse) {
        root.items.clear()
        root.items.addAll(userList.list.map { it.name })
      }
    })
  }

  fun getView() = root
}
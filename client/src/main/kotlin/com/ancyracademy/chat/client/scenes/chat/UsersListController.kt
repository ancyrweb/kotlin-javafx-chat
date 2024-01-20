package com.ancyracademy.chat.client.scenes.chat

import com.ancyracademy.chat.client.Client
import com.ancyracademy.chat.client.interfaces.Disposable
import com.ancyracademy.chat.protocol.events.NewMessageEvent
import com.ancyracademy.chat.protocol.events.NewUserEvent
import com.ancyracademy.chat.protocol.events.UserDisconnectedEvent
import com.ancyracademy.chat.protocol.responses.MessageListResponse
import com.ancyracademy.chat.protocol.responses.UserListResponse
import javafx.scene.control.ListView

class UsersListController : Disposable {
  private val root: ListView<String> = ListView()
  private val listener = object : Client.Listener {
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
  }

  init {
    Client.addListener(listener)
  }

  fun getView() = root

  override fun dispose() {
    Client.removeListener(listener)
  }
}
package com.ancyracademy.chat.client.scenes.chat

import com.ancyracademy.chat.client.Client
import com.ancyracademy.chat.client.interfaces.Disposable
import com.ancyracademy.chat.protocol.commands.GetUsersCommand
import com.ancyracademy.chat.protocol.events.NewMessageEvent
import com.ancyracademy.chat.protocol.events.NewUserEvent
import com.ancyracademy.chat.protocol.events.UserDisconnectedEvent
import com.ancyracademy.chat.protocol.responses.MessageListResponse
import com.ancyracademy.chat.protocol.responses.UserListResponse
import javafx.application.Platform
import javafx.scene.control.ListView

class UsersListController : Disposable {
  private val root: ListView<String> = ListView()
  private val listener = object : Client.Listener {
    override fun onNewMessage(message: NewMessageEvent) {}
    override fun onNewUser(user: NewUserEvent) {
      Platform.runLater {
        root.items.add(user.name)
      }
    }

    override fun onUserDisconnected(user: UserDisconnectedEvent) {
      Platform.runLater {
        root.items.remove(user.name)
      }
    }

    override fun onMessageList(messageList: MessageListResponse) {}
    override fun onUserList(userList: UserListResponse) {
      Platform.runLater {
        root.items.clear()
        root.items.addAll(*userList.list.map { it.name }.toTypedArray())
      }
    }
  }

  init {
    Client.addListener(listener)
    Client.send(GetUsersCommand())
  }

  fun getView() = root

  override fun dispose() {
    Client.removeListener(listener)
  }
}
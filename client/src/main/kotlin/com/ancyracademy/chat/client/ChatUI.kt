package com.ancyracademy.chat.client

import javafx.scene.Parent
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.layout.BorderPane

class ChatUI(private val client: Client) {
  private lateinit var messagesArea: TextArea
  private lateinit var usersList: ListView<String>
  private lateinit var root: BorderPane

  fun start(): Parent {
    root = BorderPane()

    val messageSenderUi = PromptController(client)
    val userListUi = UsersListController(client)
    val chat = ChatController(client)

    root.center = chat.getView()
    root.right = userListUi.getView()
    root.bottom = messageSenderUi.getView()
    
    return root
  }
}
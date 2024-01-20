package com.ancyracademy.chat.client.scenes.chat

import com.ancyracademy.chat.client.interfaces.AppScene
import javafx.scene.Scene
import javafx.scene.layout.BorderPane

class ChatScene : AppScene() {
  private lateinit var messageSenderUi: PromptController
  private lateinit var userListUi: UsersListController
  private lateinit var chat: ChatController
  
  override fun render(): Scene {
    val root = BorderPane()

    messageSenderUi = PromptController()
    userListUi = UsersListController()
    chat = ChatController()

    root.center = chat.getView()
    root.right = userListUi.getView()
    root.bottom = messageSenderUi.getView()

    return Scene(root)
  }

  override fun onLeave() {
    userListUi.dispose()
    chat.dispose()
  }
}
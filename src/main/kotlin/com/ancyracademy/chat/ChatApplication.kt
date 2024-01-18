package com.ancyracademy.chat

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class ChatApplication : Application() {
  override fun start(stage: Stage) {
    val root = BorderPane()
    val scene = Scene(root
    )
    stage.title = "Chat"
    stage.scene = scene
    stage.show()
  }
}

fun main() {
  Application.launch(ChatApplication::class.java)
}
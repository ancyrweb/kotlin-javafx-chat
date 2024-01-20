package com.ancyracademy.chat.server

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class MainApplication : Application() {
  private val server = Server()
  override fun start(stage: Stage) {
    server.start()

    val root = BorderPane()
    val scene = Scene(root)

    stage.title = "Chat - Serveur"
    stage.scene = scene
    stage.show()
  }
}

fun main() {
  Application.launch(MainApplication::class.java)
}
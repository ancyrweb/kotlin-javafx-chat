package com.ancyracademy.chat.server

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import kotlin.concurrent.thread

class MainApplication : Application() {
  override fun start(stage: Stage) {
    thread {
      Server().start()
    }

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
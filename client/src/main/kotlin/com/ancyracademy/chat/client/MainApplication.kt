package com.ancyracademy.chat.client

import javafx.application.Application
import javafx.stage.Stage

class MainApplication : Application() {

  override fun start(stage: Stage) {
    stage.title = "Chat - Client"

    Client.connect("localhost", 19999)
    Navigator.initialize(stage)
    Navigator.start()

    stage.setOnCloseRequest {
      Client.disconnect()
      Navigator.clear()
    }
  }
}

fun main() {
  Application.launch(MainApplication::class.java)
}
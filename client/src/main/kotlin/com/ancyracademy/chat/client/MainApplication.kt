package com.ancyracademy.chat.client

import com.ancyracademy.chat.client.client.Gateway
import javafx.application.Application
import javafx.stage.Stage

class MainApplication : Application() {

  override fun start(stage: Stage) {
    stage.title = "Chat - Client"

    Gateway.connect("localhost", 19999)
    Navigator.initialize(stage)
    Navigator.start()

    stage.setOnCloseRequest {
      Gateway.disconnect()
      Navigator.clear()
    }
  }
}

fun main() {
  Application.launch(MainApplication::class.java)
}
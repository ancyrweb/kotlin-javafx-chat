package com.ancyracademy.chat.client

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class MainApplication : Application() {
  private var client = Client()

  override fun start(stage: Stage) {
    val ui = ChatUI(client)
    val scene = Scene(ui.start())

    stage.title = "Chat - Client"
    stage.scene = scene
    stage.show()
    println("Showing")

    connect()
    println("Done connecting")
  }

  private fun connect() {
    client.connect("localhost", 19999)
  }
}

fun main() {
  Application.launch(MainApplication::class.java)
}
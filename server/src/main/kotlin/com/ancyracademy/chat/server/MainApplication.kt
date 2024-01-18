package com.ancyracademy.chat.server

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class MainApplication : Application() {
    override fun start(stage: Stage) {
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
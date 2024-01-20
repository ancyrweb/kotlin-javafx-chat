package com.ancyracademy.chat.client

import com.ancyracademy.chat.client.interfaces.AppScene
import com.ancyracademy.chat.client.scenes.chat.ChatScene
import javafx.stage.Stage

object Navigator {
  internal data class CurrentScene(val scene: AppScene, val key: String)

  private lateinit var stage: Stage
  private var currentScene: CurrentScene? = null
  private val map = mutableMapOf<String, () -> AppScene>(
    "Chat" to { ChatScene() },
  )

  fun initialize(stage: Stage) {
    this.stage = stage
  }

  fun start() {
    currentScene = CurrentScene(map["Chat"]!!(), "Chat")

    stage.scene = currentScene!!.scene.render()
    stage.show()
  }

  fun clear() {
    currentScene?.scene?.onLeave()
    currentScene = null
  }
}
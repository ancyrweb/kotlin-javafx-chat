package com.ancyracademy.chat.client

import com.ancyracademy.chat.client.interfaces.AppScene
import com.ancyracademy.chat.client.scenes.chat.ChatScene
import com.ancyracademy.chat.client.scenes.login.LoginScene
import javafx.stage.Stage

object Navigator {
  internal data class CurrentScene(val scene: AppScene, val key: String)

  private lateinit var stage: Stage
  private var currentScene: CurrentScene? = null
  private val map = mutableMapOf(
    "Chat" to { ChatScene() },
    "Login" to { LoginScene() }
  )

  fun initialize(stage: Stage) {
    this.stage = stage
  }

  fun start() {
    currentScene = CurrentScene(map["Login"]!!(), "Login")

    stage.scene = currentScene!!.scene.render()
    stage.show()
  }

  fun navigate(name: String) {
    if (currentScene?.key == name) {
      return
    }

    if (map.containsKey(name).not()) {
      throw Exception("Scene $name not found")
    }

    val scene = map[name]!!()
    currentScene?.scene?.onLeave()
    currentScene = CurrentScene(scene, name)
    stage.scene = scene.render()
    stage.show()
  }

  fun clear() {
    currentScene?.scene?.onLeave()
    currentScene = null
  }
}
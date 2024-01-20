package com.ancyracademy.chat.client.interfaces

import javafx.scene.Scene

abstract class AppScene {
  abstract fun render(): Scene

  open fun onLeave() {}
}
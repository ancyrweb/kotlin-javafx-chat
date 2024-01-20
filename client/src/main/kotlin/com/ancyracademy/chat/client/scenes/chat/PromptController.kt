package com.ancyracademy.chat.client.scenes.chat

import com.ancyracademy.chat.client.client.Gateway
import com.ancyracademy.chat.protocol.commands.SendMessageCommand
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority

class PromptController {
  var root: HBox

  init {
    val input = TextField()
    input.onKeyPressed = EventHandler<KeyEvent> { event ->
      if (event.code == KeyCode.ENTER) {
        onSendMessage(input)
      }
    }

    val sendButton = Button("Send")
    sendButton.setOnAction {
      onSendMessage(input)
    }

    root = HBox(input, sendButton)
    root.alignment = Pos.CENTER
    HBox.setHgrow(input, Priority.ALWAYS)
  }

  fun getView() = root

  fun onSendMessage(input: TextField) {
    val message = input.text
    input.clear()

    Gateway.send(SendMessageCommand(message))
  }
}
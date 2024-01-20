package com.ancyracademy.chat.client

import com.ancyracademy.chat.protocol.commands.SendMessageCommand
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority

class ChatUI(private val client: Client) {
  private lateinit var messagesArea: TextArea
  private lateinit var usersList: ListView<String>
  private lateinit var input: TextField
  private lateinit var root: BorderPane

  fun start(): Parent {
    root = BorderPane()

    messagesArea = TextArea()
    messagesArea.isEditable = false

    usersList = ListView()

    input = TextField()
    input.onKeyPressed = EventHandler<KeyEvent> { event ->
      if (event.code == KeyCode.ENTER) {
        onSendMessage()
      }
    }

    val sendButton = Button("Send")
    sendButton.setOnAction {
      onSendMessage()
    }

    val bottomBox = HBox(input, sendButton)
    bottomBox.alignment = Pos.CENTER

    HBox.setHgrow(input, Priority.ALWAYS)

    root.center = messagesArea
    root.right = usersList
    root.bottom = bottomBox
    return root
  }

  fun onSendMessage() {
    val message = input.text
    input.clear()

    client.send(SendMessageCommand(message))
  }
}
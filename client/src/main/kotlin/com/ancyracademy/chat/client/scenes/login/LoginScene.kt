package com.ancyracademy.chat.client.scenes.login

import com.ancyracademy.chat.client.Navigator
import com.ancyracademy.chat.client.client.Gateway
import com.ancyracademy.chat.client.interfaces.AppScene
import com.ancyracademy.chat.protocol.commands.LoginCommand
import javafx.geometry.Insets
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox

class LoginScene : AppScene() {
  override fun render(): Scene {
    val root = BorderPane()

    val usernameInput = TextField()
    usernameInput.promptText = "Username"
    usernameInput.setOnKeyPressed {
      if (it.code.name == "ENTER") {
        login(usernameInput.text)
      }
    }
    val loginButton = Button("Login")
    loginButton.setOnMouseClicked {
      login(usernameInput.text)
    }

    val group = VBox()
    group.children.addAll(usernameInput, loginButton)
    VBox.setMargin(group, Insets(10.0))

    root.top = Group()
    root.left = Group()
    root.right = Group()
    root.bottom = Group()
    root.center = group

    return Scene(root, 400.0, 300.0)
  }

  fun login(username: String) {
    if (username.isEmpty() || username.length < 5) {
      val alert = Alert(Alert.AlertType.ERROR)
      alert.title = "Error"
      alert.headerText = "Invalid username"
      alert.contentText = "Username must be at least 5 characters long"
      alert.showAndWait()
      return
    }

    Gateway.send(LoginCommand(username))
    Navigator.navigate("Chat")
  }
}
module com.ancyracademy.chat.client {
  requires javafx.controls;
  requires javafx.fxml;
  requires kotlin.stdlib;

  requires org.controlsfx.controls;
  requires com.ancyracademy.chat.protocol;

  opens com.ancyracademy.chat.client to javafx.fxml;
  exports com.ancyracademy.chat.client;
}
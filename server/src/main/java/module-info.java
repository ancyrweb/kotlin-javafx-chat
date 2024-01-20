module com.ancyracademy.chat.server {
  requires javafx.controls;
  requires javafx.fxml;
  requires kotlin.stdlib;

  requires org.controlsfx.controls;
  requires com.ancyracademy.chat.protocol;

  opens com.ancyracademy.chat.server to javafx.fxml;
  exports com.ancyracademy.chat.server;
}
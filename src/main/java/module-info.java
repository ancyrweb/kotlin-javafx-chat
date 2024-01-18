module com.ancyracademy.chat {
  requires javafx.controls;
  requires javafx.fxml;
  requires kotlin.stdlib;

  requires org.controlsfx.controls;

  opens com.ancyracademy.chat to javafx.fxml;
  exports com.ancyracademy.chat;
}
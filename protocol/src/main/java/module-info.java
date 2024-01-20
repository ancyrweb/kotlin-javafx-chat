module com.ancyracademy.chat.protocol {
  requires java.base;
  requires kotlin.stdlib;

  exports com.ancyracademy.chat.protocol.commands;
  exports com.ancyracademy.chat.protocol.events;
  exports com.ancyracademy.chat.protocol.responses;
  exports com.ancyracademy.chat.protocol;
}
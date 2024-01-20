package com.ancyracademy.chat.client.client

import com.ancyracademy.chat.protocol.ProtocolMessage
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

class SocketClient(private val listener: Listener) {
  private lateinit var socket: Socket
  private lateinit var writer: ObjectOutputStream
  private lateinit var receiver: ThreadHandler

  interface Listener {
    fun onMessage(message: ProtocolMessage)
  }

  inner class ThreadHandler(private val socket: Socket) : Thread() {
    private var reader = ObjectInputStream(socket.getInputStream())
    private var running = true

    override fun run() {
      var message: Any? = null
      try {
        while (running && reader.readObject().also { message = it } != null) {
          when (message) {
            is ProtocolMessage -> {
              listener.onMessage(message as ProtocolMessage)
            }
          }
        }
      } catch (_: Exception) {
        // Do Nothing
      }
    }

    fun disconnect() {
      running = false
      reader.close()
    }
  }

  fun connect(serverAddress: String, serverPort: Int) {
    socket = Socket(serverAddress, serverPort)
    writer = ObjectOutputStream(socket.getOutputStream())

    receiver = ThreadHandler(socket)
    receiver.start()
  }

  fun disconnect() {
    receiver.disconnect()
    writer.close()
    socket.close()
  }

  fun send(message: ProtocolMessage) {
    writer.writeObject(message)
  }
}
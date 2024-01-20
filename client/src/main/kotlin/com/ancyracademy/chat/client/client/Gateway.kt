package com.ancyracademy.chat.client.client

import com.ancyracademy.chat.protocol.ProtocolMessage

object Gateway {
  private var observable = GatewayObservable()
  private var socket = SocketClient(object : SocketClient.Listener {
    override fun onMessage(message: ProtocolMessage) {
      observable.dispatch(message)
    }
  })

  fun connect(serverAddress: String, serverPort: Int) {
    socket.connect(serverAddress, serverPort)
  }

  fun disconnect() {
    socket.disconnect()
  }

  fun send(message: ProtocolMessage) {
    socket.send(message)
  }

  fun addListener(listener: GatewayObservable.Listener) {
    observable.add(listener)
  }

  fun removeListener(listener: GatewayObservable.Listener) {
    observable.remove(listener)
  }
}
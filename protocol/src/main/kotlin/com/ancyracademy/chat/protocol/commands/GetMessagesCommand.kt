package com.ancyracademy.chat.protocol.commands

import com.ancyracademy.chat.protocol.ProtocolMessage

class GetMessagesCommand : ProtocolMessage() {
  class Message(
    var author: String,
    var message: String
  )
}
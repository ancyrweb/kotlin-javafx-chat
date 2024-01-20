package com.ancyracademy.chat.protocol.responses

import com.ancyracademy.chat.protocol.ProtocolMessage
import java.time.LocalDateTime

class MessageListResponse(val list: Array<Message>) : ProtocolMessage() {
  class Message(
    var author: String,
    var message: String,
    var date: LocalDateTime
  )
}
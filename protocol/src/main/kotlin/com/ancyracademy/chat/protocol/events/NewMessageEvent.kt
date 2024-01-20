package com.ancyracademy.chat.protocol.events

import com.ancyracademy.chat.protocol.ProtocolMessage

class NewMessageEvent(
  val author: String,
  val message: String
) :
  ProtocolMessage() {}
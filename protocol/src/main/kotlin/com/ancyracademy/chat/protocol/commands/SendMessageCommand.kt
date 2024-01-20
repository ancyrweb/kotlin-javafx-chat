package com.ancyracademy.chat.protocol.commands

import com.ancyracademy.chat.protocol.ProtocolMessage

class SendMessageCommand(public val message: String) :
  ProtocolMessage() {}
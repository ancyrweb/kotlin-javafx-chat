package com.ancyracademy.chat.protocol.events

import com.ancyracademy.chat.protocol.ProtocolMessage

class UserDisconnectedEvent(val name: String) : ProtocolMessage() {

}
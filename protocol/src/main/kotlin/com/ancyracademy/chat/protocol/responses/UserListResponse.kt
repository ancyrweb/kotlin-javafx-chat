package com.ancyracademy.chat.protocol.responses

import com.ancyracademy.chat.protocol.ProtocolMessage
import java.io.Serializable

class UserListResponse(val list: Array<User>) : ProtocolMessage() {
  class User(
    var id: String,
    var name: String
  ) : Serializable
}
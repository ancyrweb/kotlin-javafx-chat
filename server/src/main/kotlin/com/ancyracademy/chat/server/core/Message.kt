package com.ancyracademy.chat.server.core

import java.time.LocalDateTime

class Message(val author: User, val content: String, val date: LocalDateTime) {

}
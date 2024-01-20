package com.ancyracademy.chat.server.core

abstract class Repository<T> {
  private val data = mutableListOf<T>()

  fun add(message: T) {
    data.add(message)
  }

  fun remove(message: T) {
    data.remove(message)
  }

  fun clear() {
    data.clear()
  }

  fun getAll(): List<T> {
    return data.toList()
  }
}
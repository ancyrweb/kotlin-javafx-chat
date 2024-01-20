package com.ancyracademy.chat.server.core

class ObservableCollection<T> {
  interface ChangeListener<T> {
    fun onAdd(item: T)
    fun onRemove(item: T)
  }

  private val listeners = mutableListOf<ChangeListener<T>>()
  private val items = mutableListOf<T>()

  fun addListener(listener: ChangeListener<T>) {
    listeners.add(listener)
  }

  fun removeListener(listener: ChangeListener<T>) {
    listeners.remove(listener)
  }

  fun add(item: T) {
    items.add(item)
    listeners.forEach { it.onAdd(item) }
  }

  fun remove(item: T) {
    items.remove(item)
    listeners.forEach { it.onRemove(item) }
  }

  fun clear() {
    items.clear()
  }
}
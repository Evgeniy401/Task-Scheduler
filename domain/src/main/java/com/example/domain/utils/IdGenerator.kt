package com.example.domain.utils

object IdGenerator {
    private var counter = 0

    fun generateId(): Int = ++counter
}
package com.example.projetsession

data class Request(
    val model: String,
    val max_tokens: Int,
    val messages: List<Content>
)

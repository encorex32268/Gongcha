package com.lihan.auth.intro

data class IntroState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)
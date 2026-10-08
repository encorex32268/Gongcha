package com.lihan.gongcha

import com.lihan.gongcha.navigation.AuthGraph

data class MainState(
    val isReady: Boolean = false,
    val startDestination: Any = AuthGraph,
    val cartItemCount: Int = 0,
)

package com.lihan.core.domain.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object TimerFlow {

    fun timeAndEmit(): Flow<Duration> = flow{
        var lastTime = Clock.System.now().toEpochMilliseconds()
        while (true){
            delay(300L.milliseconds)
            val currentTime = Clock.System.now().toEpochMilliseconds()
            val duration = (currentTime - lastTime).milliseconds
            emit(duration)
            lastTime = currentTime
        }
    }
}
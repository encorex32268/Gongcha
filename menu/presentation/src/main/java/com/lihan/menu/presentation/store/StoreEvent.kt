package com.lihan.menu.presentation.store

sealed interface StoreEvent{
    data object OnSavedSelectedStore: StoreEvent
}
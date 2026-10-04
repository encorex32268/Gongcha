package com.lihan.menu.store

sealed interface StoreEvent{
    data object OnSavedSelectedStore: StoreEvent
}
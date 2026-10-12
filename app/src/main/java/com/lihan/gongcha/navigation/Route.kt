package com.lihan.gongcha.navigation

import kotlinx.serialization.Serializable

// Top Level Graphs
@Serializable
data object MenuGraph

@Serializable
data object ShoppingCartGraph

@Serializable
data object HistoryGraph

@Serializable
data object MemberGraph

// Screen Destinations
@Serializable
data object MenuRoute

@Serializable
data object ShoppingCartRoute

@Serializable
data object HistoryRoute

@Serializable
data object MemberRoute

@Serializable
data object SelectStoreRoute

@Serializable
data class TeaOrderRoute(val storeId: String,val teaId: String)

@Serializable
data object AuthGraph

@Serializable
data object LoginRoute

@Serializable
data object CodeVerifyRoute


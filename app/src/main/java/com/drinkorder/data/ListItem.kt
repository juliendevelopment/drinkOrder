package com.drinkorder.data

import kotlinx.serialization.Serializable

@Serializable
data class ListItem(
    val id: String,
    val text: String,
    val order: Int
)
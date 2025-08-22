package com.cmp.starter_pack.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val clickedTimes: Int,
)
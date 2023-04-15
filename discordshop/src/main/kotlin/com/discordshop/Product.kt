package com.discordshop

import kotlinx.serialization.Serializable

@Serializable
data class Product(val id: Int, val name: String, val category: String)

package com.example.shop.domain

data class ShopItem(
    val name: String,
    val count: Int,
    val active: Boolean,
    var id: Long = UNDEFINED,
)
{
    companion object {
        const val UNDEFINED = -1L
    }
}
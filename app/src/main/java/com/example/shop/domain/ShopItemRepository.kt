package com.example.shop.domain

import androidx.lifecycle.LiveData

interface ShopItemRepository {
    suspend fun addShopItem(item: ShopItem)
    suspend fun editShopItem(item: ShopItem)
    suspend fun getItem(id: Long) : ShopItem
    fun getItems() : LiveData<List<ShopItem>>
    suspend fun removeShopItem(item: ShopItem)
}

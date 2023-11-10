package com.example.shop.domain

import androidx.lifecycle.LiveData

interface ShopItemRepository {
    fun addShopItem(item: ShopItem)
    fun editShopItem(item: ShopItem)
    fun getItem(id: Long) : ShopItem
    fun getItems() : LiveData<List<ShopItem>>
    fun removeShopItem(item: ShopItem)
}

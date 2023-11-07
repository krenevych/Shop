package com.example.shop.domain

interface ShopItemRepository {
    fun addShopItem(item: ShopItem)
    fun editShopItem(item: ShopItem)
    fun getItem(id: Long) : ShopItem
    fun getItems() : List<ShopItem>
    fun removeShopItem(item: ShopItem)
}

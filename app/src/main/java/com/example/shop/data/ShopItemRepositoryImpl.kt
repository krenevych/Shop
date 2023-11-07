package com.example.shop.data

import com.example.shop.domain.ShopItem
import com.example.shop.domain.ShopItemRepository

object ShopItemRepositoryImpl : ShopItemRepository {

    private val shopItems = mutableListOf<ShopItem>()
    private var currentId = 0L

    init {
        for (i in 1..5) {
            addShopItem(
                ShopItem(
                    "Item_$i",
                    i,
                    true,
                    currentId++
                )
            )
        }
    }

    override fun addShopItem(item: ShopItem) {
        shopItems.add(item)
    }

    override fun editShopItem(item: ShopItem) {
        val id = item.id
        val oldItem = getItem(id)
        shopItems.remove(oldItem)
        addShopItem(item)
    }

    override fun getItem(id: Long): ShopItem {
        return shopItems.find { it.id == id }
            ?: throw RuntimeException("Item not found")
    }

    override fun getItems(): List<ShopItem> {
        return shopItems.toList()
    }

    override fun removeShopItem(item: ShopItem) {
        shopItems.remove(item)
    }
}
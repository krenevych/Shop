package com.example.shop.domain.usecase

import com.example.shop.domain.ShopItemRepository
import com.example.shop.domain.ShopItem

class GetShopItemList(
    private val repository: ShopItemRepository
) {
    fun getItems() : List<ShopItem> {
        return repository.getItems()
    }
}
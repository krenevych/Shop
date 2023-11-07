package com.example.shop.domain.usecase

import com.example.shop.domain.ShopItemRepository
import com.example.shop.domain.ShopItem

class AddShopItem(
    private val repository: ShopItemRepository
) {
    fun addShopItem(item: ShopItem){
        repository.addShopItem(item)
    }
}
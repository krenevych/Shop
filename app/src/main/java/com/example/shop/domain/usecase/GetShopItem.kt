package com.example.shop.domain.usecase

import com.example.shop.domain.ShopItemRepository
import com.example.shop.domain.ShopItem

class GetShopItem(
    private val repository: ShopItemRepository
) {
    fun getItem(id: Long) : ShopItem {
        return repository.getItem(id)
    }
}
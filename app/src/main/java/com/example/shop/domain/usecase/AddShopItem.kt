package com.example.shop.domain.usecase

import com.example.shop.domain.ShopItemRepository
import com.example.shop.domain.ShopItem

class AddShopItem(
    private val repository: ShopItemRepository
) {
    suspend operator fun invoke(item: ShopItem){
        repository.addShopItem(item)
    }
}
package com.example.shop.domain.usecase

import com.example.shop.domain.ShopItemRepository
import com.example.shop.domain.ShopItem

class EditShopItem(
    private val repository: ShopItemRepository
) {
    fun editShopItem(item: ShopItem){
        repository.editShopItem(item)
    }
}
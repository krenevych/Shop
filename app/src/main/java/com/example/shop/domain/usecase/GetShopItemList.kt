package com.example.shop.domain.usecase

import com.example.shop.domain.Repository
import com.example.shop.domain.ShopItem

class GetShopItemList(
    private val repository: Repository
) {
    fun getItems() : List<ShopItem> {
        return repository.getItems()
    }
}
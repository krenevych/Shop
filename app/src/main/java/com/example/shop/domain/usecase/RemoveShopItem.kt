package com.example.shop.domain.usecase

import com.example.shop.domain.Repository
import com.example.shop.domain.ShopItem

class RemoveShopItem(
    private val repository: Repository
) {
    fun removeShopItem(item: ShopItem){
        repository.removeShopItem(item)
    }
}
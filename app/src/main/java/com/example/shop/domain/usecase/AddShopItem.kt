package com.example.shop.domain.usecase

import com.example.shop.domain.Repository
import com.example.shop.domain.ShopItem

class AddShopItem(
    private val repository: Repository
) {
    fun addShopItem(item: ShopItem){
        repository.addShopItem(item)
    }
}
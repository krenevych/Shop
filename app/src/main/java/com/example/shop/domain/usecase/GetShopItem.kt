package com.example.shop.domain.usecase

import com.example.shop.domain.Repository
import com.example.shop.domain.ShopItem

class GetShopItem(
    private val repository: Repository
) {
    fun getItem(id: Long) : ShopItem {
        return repository.getItem(id)
    }
}
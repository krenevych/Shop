package com.example.shop.domain.usecase

import com.example.shop.domain.Repository
import com.example.shop.domain.ShopItem

class EditShopItem(
    private val repository: Repository
) {
    fun editShopItem(item: ShopItem){
        repository.editShopItem(item)
    }
}
package com.example.shop.domain.usecase

import androidx.lifecycle.LiveData
import com.example.shop.domain.ShopItemRepository
import com.example.shop.domain.ShopItem

class GetShopItemList(
    private val repository: ShopItemRepository
) {
    fun getItems() : LiveData<List<ShopItem>> {
        return repository.getItems()
    }
}
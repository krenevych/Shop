package com.example.shop.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shop.data.ShopItemRepositoryImpl
import com.example.shop.domain.ShopItem
import com.example.shop.domain.usecase.EditShopItem
import com.example.shop.domain.usecase.GetShopItemList
import com.example.shop.domain.usecase.RemoveShopItem

class MainViewModel: ViewModel() {

    private val repository = ShopItemRepositoryImpl   // FIXME: це на зараз!!! Це не правильно з точки зору чистої архітектури,
    // FIXME: бо presentation модуль стає залежним від data модуля

    private val getShopItemListUseCase = GetShopItemList(repository)
    private val editShopItemUseCase = EditShopItem(repository)
    private val removeShopItemUseCase = RemoveShopItem(repository)

    val liveData: LiveData<List<ShopItem>>
        get() = getShopItemListUseCase.getItems()

    fun getShopItemList() {
        getShopItemListUseCase.getItems()
    }

    fun toggleItemActivity(item: ShopItem) {
        editShopItemUseCase.editShopItem(item)
    }
    fun removeShopItem(item: ShopItem) {
        removeShopItemUseCase.removeShopItem(item)
    }

}
package com.example.shop.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shop.data.ShopItemRepositoryImpl
import com.example.shop.domain.ShopItem
import com.example.shop.domain.usecase.AddShopItem
import com.example.shop.domain.usecase.EditShopItem
import com.example.shop.domain.usecase.GetShopItem

class ShopItemViewModel: ViewModel() {
    private val repository = ShopItemRepositoryImpl   // FIXME: це на зараз!!! Це не правильно з точки зору чистої архітектури,
    // FIXME: бо presentation модуль стає залежним від data модуля

    private val editShopItemUseCase = EditShopItem(repository)
    private val addShopItemUseCase = AddShopItem(repository)
    private val getShopItemUseCase = GetShopItem(repository)

    private var _itemLiveData = MutableLiveData<ShopItem>()
    val itemLiveData: LiveData<ShopItem>
        get() = _itemLiveData

    fun getItem(id: Long){
        val shopItem = getShopItemUseCase.getItem(id)
        _itemLiveData.value = shopItem
    }

    fun editItem(name: String, count: Int){
        val item = _itemLiveData.value
        item?.let {
            val shopItem = it.copy(name = name, count = count)
            editShopItemUseCase.editShopItem(shopItem)
        }
    }
    fun addItem(name: String, count: Int){
        val shopItem = ShopItem(name, count)
        addShopItemUseCase.addShopItem(shopItem)
    }

}
package com.example.shop.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.shop.data.DataBaseRepository
import com.example.shop.domain.ShopItem
import com.example.shop.domain.usecase.EditShopItem
import com.example.shop.domain.usecase.GetShopItemList
import com.example.shop.domain.usecase.RemoveShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

//    private val repository = ShopItemRepositoryImpl   // FIXME: це на зараз!!! Це не правильно з точки зору чистої архітектури,
//    // FIXME: бо presentation модуль стає залежним від data модуля

    private val repository = DataBaseRepository(application)

    private val getShopItemListUseCase = GetShopItemList(repository)
    private val editShopItemUseCase = EditShopItem(repository)
    private val removeShopItemUseCase = RemoveShopItem(repository)

    val liveData: LiveData<List<ShopItem>>
        get() = getShopItemListUseCase.getItems()


    fun toggleItemActivity(item: ShopItem) {
        viewModelScope.launch {
            val newItem = item.copy(active = !item.active)
            editShopItemUseCase.editShopItem(newItem)
        }
    }
    fun removeShopItem(item: ShopItem) {
        viewModelScope.launch {
            removeShopItemUseCase.removeShopItem(item)
        }
    }

}
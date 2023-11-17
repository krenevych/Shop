package com.example.shop.presentation

import android.text.Editable
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

    private val _itemLiveData = MutableLiveData<ShopItem>()
    val itemLiveData: LiveData<ShopItem>
        get() = _itemLiveData

    fun getItem(id: Long){
        val shopItem = getShopItemUseCase.getItem(id)
        _itemLiveData.value = shopItem
    }

    private fun validate(name: String, count: Int): Boolean {
        var res = true
        if (name == ""){
            res = false
        }
        if (count <= 0){
            res = false
        }

        return res
    }

    private fun parseInputName(inputName: Editable?) = inputName?.toString() ?: ""

    private fun parseInputCount(inputCount: Editable?): Int {
        return inputCount?.toString()?.toInt() ?: 0
    }

    fun editItem(inputName: Editable?, inputCount: Editable?){
        val item = _itemLiveData.value

        val name = parseInputName(inputName)
        val count = parseInputCount(inputCount)
        if (validate(name, count)){
            item?.let {
                val shopItem = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(shopItem)
            }
        }

    }
    fun addItem(inputName: Editable?, inputCount: Editable?){
        val name = parseInputName(inputName)
        val count = parseInputCount(inputCount)

        if (validate(name, count)) {
            val shopItem = ShopItem(name, count)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

}
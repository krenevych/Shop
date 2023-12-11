package com.example.shop.presentation

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shop.data.DataBaseRepository
import com.example.shop.domain.ShopItem
import com.example.shop.domain.usecase.AddShopItem
import com.example.shop.domain.usecase.EditShopItem
import com.example.shop.domain.usecase.GetShopItem
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application): AndroidViewModel(application) {
//    private val repository = ShopItemRepositoryImpl   // FIXME: це на зараз!!! Це не правильно з точки зору чистої архітектури,
//    // FIXME: бо presentation модуль стає залежним від data модуля

    private val repository = DataBaseRepository(application)

    private val editShopItemUseCase = EditShopItem(repository)
    private val addShopItemUseCase = AddShopItem(repository)
    private val getShopItemUseCase = GetShopItem(repository)

    private val _itemLiveData = MutableLiveData<ShopItem>()
    val itemLiveData: LiveData<ShopItem>
        get() = _itemLiveData

    fun getItem(id: Long){
        viewModelScope.launch {
            val shopItem = getShopItemUseCase.getItem(id)
            _itemLiveData.value = shopItem
        }

    }

    private val _errorNameLD = MutableLiveData<Boolean>()
    val errorNameLD: LiveData<Boolean>
        get() = _errorNameLD

    private val _errorCountLD = MutableLiveData<Boolean>()
    val errorCountLD: LiveData<Boolean>
        get() = _errorCountLD


    private fun validate(name: String, count: Int): Boolean {
        var res = true
        if (name == ""){
            res = false
            _errorNameLD.value = false
        }
        if (count <= 0){
            _errorCountLD.value = false
            res = false
        }

        return res
    }

    private fun parseInputName(inputName: Editable?) = inputName?.toString() ?: ""

    private fun parseInputCount(inputCount: Editable?) = try {
        inputCount?.toString()?.toInt() ?: 0
    } catch (er: NumberFormatException){
        0
    }

    private val _finishActivityLD = MutableLiveData<Unit>()
    val finishActivityLD: LiveData<Unit>
        get() = _finishActivityLD

    fun editItem(inputName: Editable?, inputCount: Editable?){
        val item = _itemLiveData.value

        val name = parseInputName(inputName)
        val count = parseInputCount(inputCount)
        if (validate(name, count)){
            viewModelScope.launch {
                item?.let {
                    val shopItem = it.copy(name = name, count = count)
                    editShopItemUseCase.editShopItem(shopItem)
                }
                _finishActivityLD.value = Unit
            }
        }

    }
    fun addItem(inputName: Editable?, inputCount: Editable?){
        val name = parseInputName(inputName)
        val count = parseInputCount(inputCount)

        if (validate(name, count)) {
            viewModelScope.launch {
                val shopItem = ShopItem(name, count)
                addShopItemUseCase(shopItem)
                _finishActivityLD.value = Unit
            }
        }
    }

}
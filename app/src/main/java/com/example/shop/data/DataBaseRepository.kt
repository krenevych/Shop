package com.example.shop.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shop.domain.ShopItem
import com.example.shop.domain.ShopItemRepository

class DataBaseRepository(context: Context): ShopItemRepository {

    private val dao = ShopItemDB.getInstance(context).getDao()
    override suspend fun addShopItem(item: ShopItem) {
        dao.addShopItem(ShopItemMapper.shopItemToEntity(item))
    }

    override suspend fun editShopItem(item: ShopItem) {
        dao.editShopItem(ShopItemMapper.shopItemToEntity(item))
    }

    override suspend fun getItem(id: Long): ShopItem {
        val entity = dao.getItem(id)
        return ShopItemMapper.entityToShopItem(entity)
    }

    override fun getItems(): LiveData<List<ShopItem>> {

        return MediatorLiveData<List<ShopItem>>().apply {
            addSource(dao.getItems()){
                value = ShopItemMapper.entitiesToShooItem(it)
            }
        }
    }

    override suspend fun removeShopItem(item: ShopItem) {
        dao.removeShopItem(ShopItemMapper.shopItemToEntity(item))
    }
}
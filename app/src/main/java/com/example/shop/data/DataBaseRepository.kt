package com.example.shop.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shop.domain.ShopItem
import com.example.shop.domain.ShopItemRepository

class DataBaseRepository(context: Context): ShopItemRepository {

    private val dao = ShopItemDB.getInstance(context).getDao()
    override fun addShopItem(item: ShopItem) {
        dao.addShopItem(ShopItemMapper.shopItemToEntity(item))
    }

    override fun editShopItem(item: ShopItem) {
        dao.editShopItem(ShopItemMapper.shopItemToEntity(item))
    }

    override fun getItem(id: Long): ShopItem {
        val entity = dao.getItem(id)
        return ShopItemMapper.entityToShopItem(entity)
    }

    override fun getItems(): LiveData<List<ShopItem>> {
        val entities = dao.getItems()
//        Transformations.map(dao.getItems()) {
//            ShopItemMapper.entitiesToShooItem(it)
//        }
        val liveData = MediatorLiveData<List<ShopItem>>()
        liveData.addSource(entities) {
            ShopItemMapper.entitiesToShooItem(it)
        }
        return liveData
    }

    override fun removeShopItem(item: ShopItem) {
        dao.removeShopItem(ShopItemMapper.shopItemToEntity(item))
    }
}
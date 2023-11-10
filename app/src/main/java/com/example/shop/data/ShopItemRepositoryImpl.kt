package com.example.shop.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shop.domain.ShopItem
import com.example.shop.domain.ShopItem.Companion.UNDEFINED
import com.example.shop.domain.ShopItemRepository

object ShopItemRepositoryImpl : ShopItemRepository {

    private val liveData = MutableLiveData<List<ShopItem>>()

    private val shopItems = sortedSetOf<ShopItem>({ o1, o2 ->
        o1.id.compareTo(o2.id)
    })

    private var currentId = 0L

    init {
        for (i in 1..25) {
            addShopItem(
                ShopItem(
                    "Item_$i",
                    i,
                    true,
                )
            )
        }
    }

    override fun addShopItem(item: ShopItem) {
        if (item.id == UNDEFINED) item.id = currentId++
        shopItems.add(item)

        update()
    }

    override fun editShopItem(item: ShopItem) {
        val oldItem = getItem(item.id)
        shopItems.remove(oldItem)
        addShopItem(item)
    }

    override fun getItem(id: Long): ShopItem {
        return shopItems.find { it.id == id }
            ?: throw RuntimeException("Item not found")
    }

    override fun getItems(): LiveData<List<ShopItem>> {
        return liveData.apply {
            update()
        }
    }

    override fun removeShopItem(item: ShopItem) {
        shopItems.remove(item)
        update()
    }

    private fun update(){
        liveData.value = shopItems.toList()
    }
}
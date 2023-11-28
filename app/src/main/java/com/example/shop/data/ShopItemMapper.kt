package com.example.shop.data

import com.example.shop.domain.ShopItem

object ShopItemMapper {
    fun entityToShopItem(entity: ShopItemEntity): ShopItem {
        return ShopItem(
            entity.name,
            entity.count,
            entity.active,
            entity.id
        )

    }

    fun shopItemToEntity(item: ShopItem): ShopItemEntity {
        return ShopItemEntity(
            item.id,
            item.name,
            item.count,
            item.active
        )
    }

    fun entitiesToShooItem(list: List<ShopItemEntity>): List<ShopItem> {
        return list.map { entityToShopItem(it) }
    }
}
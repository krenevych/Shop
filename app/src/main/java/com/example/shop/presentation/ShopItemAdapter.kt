package com.example.shop.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.R
import com.example.shop.domain.ShopItem


class ShopItemAdapter: RecyclerView.Adapter<ShopItemAdapter.ShopItemViewHolder>() {

    var shopItems = listOf<ShopItem>()
        set(value) {
            val diffUtilCallback = ShopItemsDiffUtilCallback(shopItems, value)
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtilCallback)
            field = value

            diffResult.dispatchUpdatesTo(this)
//            notifyDataSetChanged()  // FIXME:
        }

    class ShopItemViewHolder(view: View):
        RecyclerView.ViewHolder(view) {
            val name: TextView = view.findViewById(R.id.textViewName)
            val count: TextView = view.findViewById(R.id.textViewCount)
            val cardView: CardView = view.findViewById(R.id.cardView_shopItem)
        }

    companion object {
        const val ITEM_ACTIVE = 0
        const val ITEM_DISABLED = 1
        const val TAG = "XXXXX"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val itemLayout = when (viewType){
            ITEM_ACTIVE -> R.layout.shop_item
            ITEM_DISABLED -> R.layout.shop_item_disabled
            else -> throw IllegalArgumentException("Unknown type")
        }

        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(itemLayout, parent, false)

        return ShopItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopItems[position].active) {
            ITEM_ACTIVE
        } else {
            ITEM_DISABLED
        }
    }

    override fun getItemCount() = shopItems.size

    var clickListener: ((view: View, item: ShopItem)->Unit  )? = null
    var longClickListener: ((view: View, item: ShopItem)->Unit  )? = null
    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopItems[position]
        Log.e(TAG, "onBindViewHolder: ${shopItem.id}", )
        with(holder){
            name.text = "${shopItem.name} : ${shopItem.active}"
            count.text = shopItem.count.toString()

            cardView.setOnClickListener {
                clickListener?.invoke(cardView, shopItem)
            }
            cardView.setOnLongClickListener {
                longClickListener?.invoke(cardView, shopItem)
                true
            }
        }
    }

    var swipeListener: ((item: ShopItem) -> Unit)? = null
    var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//                    or ItemTouchHelper.DOWN or ItemTouchHelper.UP
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            val position = viewHolder.adapterPosition
            val shopItem = shopItems[position]
            swipeListener?.invoke(shopItem)
        }
    }

}
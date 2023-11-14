package com.example.shop.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.R
import com.example.shop.domain.ShopItem


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShopItemAdapter

    var item: ShopItem?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.shop_item_recycler_view)
        adapter = ShopItemAdapter()
        recyclerView.adapter = adapter
        adapter.clickListener = { view: View, item: ShopItem->
            Toast.makeText(this@MainActivity, item.toString(), Toast.LENGTH_SHORT).show()
        }

        adapter.longClickListener = { view: View, item: ShopItem->
            viewModel.toggleItemActivity(item)
        }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.liveData.observe(this) {
            adapter.submitList(it)
        }

        adapter.swipeListener = {
            viewModel.removeShopItem(it)
        }
        val itemTouchHelper = ItemTouchHelper(adapter.simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)




    }

    companion object {
        val TAG = "XXXXXX"
    }
}
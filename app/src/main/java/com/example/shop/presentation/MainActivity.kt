package com.example.shop.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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
            adapter.shopItems = it
        }




    }

    companion object {
        val TAG = "XXXXXX"
    }
}
package com.example.shop.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.R
import com.example.shop.domain.ShopItem
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShopItemAdapter
    private lateinit var buttonAdd: FloatingActionButton

    var item: ShopItem?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAdd = findViewById(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            lunchActivityForAdd()
        }

        recyclerView = findViewById(R.id.shop_item_recycler_view)
        adapter = ShopItemAdapter()
        recyclerView.adapter = adapter
        adapter.clickListener = { view: View, item: ShopItem->
//            Toast.makeText(this@MainActivity, item.toString(), Toast.LENGTH_SHORT).show()
            lunchActivityForEdit(item.id)
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

    private fun lunchActivityForAdd(){
        val intent = Intent(this, ShopItemActivity::class.java)
        startActivity(intent)
    }

    private fun lunchActivityForEdit(itemId: Long){
        val intent = Intent(this, ShopItemActivity::class.java)
        startActivity(intent)
    }

    companion object {
        val TAG = "XXXXXX"
    }
}
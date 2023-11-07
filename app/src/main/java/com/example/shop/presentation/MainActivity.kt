package com.example.shop.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.shop.R
import com.example.shop.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var buttonRemove: Button
    private lateinit var viewModel: MainViewModel

    var item: ShopItem?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.liveData.observe(this) {
            Log.d(TAG, "ShopList: $it")
            item = if (it.isNotEmpty()) it[0] else null

        }

        button = findViewById(R.id.button)
        button.setOnClickListener {
            viewModel.getShopItemList()
        }
        buttonRemove = findViewById(R.id.buttonRemove)
        buttonRemove.setOnClickListener {
            item?.let {
                viewModel.removeShopItem(it)
            }

        }

    }

    companion object {
        val TAG = "XXXXXX"
    }
}
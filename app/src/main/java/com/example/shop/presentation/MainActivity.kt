package com.example.shop.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.shop.R

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.liveData.observe(this) {
            Log.d(TAG, "ShopList: $it")
        }

        button = findViewById(R.id.button)
        button.setOnClickListener {
            viewModel.getShopItemList()
        }

    }

    companion object {
        val TAG = "XXXXXX"
    }
}
package com.example.shop.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.shop.R
import com.example.shop.domain.ShopItem

class MainActivity : AppCompatActivity() {

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



    }

    companion object {
        val TAG = "XXXXXX"
    }
}
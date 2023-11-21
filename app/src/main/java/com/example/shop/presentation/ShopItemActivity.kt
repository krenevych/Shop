package com.example.shop.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.shop.R
import com.example.shop.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
    }

    private fun setupFragment(fragment: ShopItemFragment){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .commit()
    }

    private var mode: String = MODE_UNDEF
    private var itemId: Long = ShopItem.UNDEFINED
    private fun parseIntent(){
        if (intent.hasExtra(EXTRA_MODE)){
            mode = intent.getStringExtra(EXTRA_MODE) ?: MODE_UNDEF
            Log.d(TAG, "parseIntent: mode = $mode")
            if (mode == MODE_ADD){
                setupFragment(ShopItemFragment.newInstanceFragmentAdd())
            }
            else if (mode == MODE_EDIT && intent.hasExtra(EXTRA_ITEM_ID)){
                itemId = intent.getLongExtra(EXTRA_ITEM_ID, ShopItem.UNDEFINED)
                Log.d(TAG, "parseIntent: itemId = $itemId")
                setupFragment(ShopItemFragment.newInstanceFragmentEdit(itemId))
            } else {
                throw IllegalArgumentException("Item Id is not defined")
            }
        } else {
            throw IllegalArgumentException("Mode is not defined")
        }
    }


    companion object {
        const val TAG = "XXXXXX"
    }
}
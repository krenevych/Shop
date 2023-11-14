package com.example.shop.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.shop.R
import com.example.shop.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout

    private lateinit var editName: TextInputEditText
    private lateinit var editCount: TextInputEditText

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
//        tilCount.error = "Error"

        editName = findViewById(R.id.edit_text_name)
        editCount = findViewById(R.id.edit_text_count)

        button = findViewById(R.id.buttonSave)
        button.setOnClickListener {
//            tilCount.error = null
        }

        parseIntent()

    }

    private var mode: String = MODE_UNDEF
    private var itemId: Long = ShopItem.UNDEFINED
    private fun parseIntent(){
        if (intent.hasExtra(EXTRA_MODE)){
            mode = intent.getStringExtra(EXTRA_MODE) ?: MODE_UNDEF
            Log.d(TAG, "parseIntent: mode = $mode")
            if (mode == MODE_EDIT && intent.hasExtra(EXTRA_ITEM_ID)){
                itemId = intent.getLongExtra(EXTRA_ITEM_ID, ShopItem.UNDEFINED)
                Log.d(TAG, "parseIntent: itemId = $itemId")
            }
        }
    }

    companion object {

        val TAG = "XXXXXX"
    }
}
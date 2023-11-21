package com.example.shop.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shop.R
import com.example.shop.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout

    private lateinit var editName: TextInputEditText
    private lateinit var editCount: TextInputEditText

    private lateinit var buttonSave: Button

    private lateinit var viewModel: ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

//        parseIntent()
        registerLiveData()

    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)

        editName = view.findViewById(R.id.edit_text_name)

        editName.addTextChangedListener {
            tilName.error = null
        }

        editCount = view.findViewById(R.id.edit_text_count)
        editCount.addTextChangedListener {
            tilCount.error = null
        }

        buttonSave = view.findViewById(R.id.buttonSave)
    }

//    private fun lunchActivityForAdd(){
//        buttonSave.setOnClickListener {
//            viewModel.addItem(editName.text, editCount.text)
//        }
//    }
//    private fun lunchActivityForEdit(){
//        viewModel.getItem(itemId)
//
//        buttonSave.setOnClickListener {
//            viewModel.editItem(editName.text, editCount.text)
//        }
//    }

//    private var mode: String = MODE_UNDEF
//    private var itemId: Long = ShopItem.UNDEFINED
//    private fun parseIntent(){
//        if (intent.hasExtra(EXTRA_MODE)){
//            mode = intent.getStringExtra(EXTRA_MODE) ?: MODE_UNDEF
//            Log.d(TAG, "parseIntent: mode = $mode")
//            if (mode == MODE_ADD){
//                lunchActivityForAdd()
//            }
//            else if (mode == MODE_EDIT && intent.hasExtra(EXTRA_ITEM_ID)){
//                itemId = intent.getLongExtra(EXTRA_ITEM_ID, ShopItem.UNDEFINED)
//                Log.d(TAG, "parseIntent: itemId = $itemId")
//                lunchActivityForEdit()
//            } else {
//                throw IllegalArgumentException("Item Id is not defined")
//            }
//        } else {
//            throw IllegalArgumentException("Mode is not defined")
//        }
//    }

    private fun registerLiveData(){
        viewModel.itemLiveData.observe(this){
            editName.setText(it.name)
            editCount.setText(it.count.toString())
        }

        viewModel.errorNameLD.observe(this){
            tilName.error = if (it){
                null
            } else {
                getString(R.string.name_error)
            }
        }
        viewModel.errorCountLD.observe(this){
            tilCount.error = if (it){
                null
            } else {
                getString(R.string.count_error)
            }
        }

        viewModel.finishActivityLD.observe(this){
//            finish()
//            onBackPressed()
        }
    }

}
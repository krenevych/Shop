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

class ShopItemFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    private var mode: String = MODE_UNDEF
    private var itemId: Long = ShopItem.UNDEFINED

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        setupMode()
        registerLiveData()
    }

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout

    private lateinit var editName: TextInputEditText
    private lateinit var editCount: TextInputEditText

    private lateinit var buttonSave: Button

    private lateinit var viewModel: ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(requireArguments()) {
            if (containsKey(EXTRA_MODE)) {
                mode = getString(EXTRA_MODE) ?: MODE_UNDEF
            } else throw IllegalArgumentException("Unknown type")

            if (mode == MODE_EDIT && containsKey(EXTRA_ITEM_ID)) {
                itemId = getLong(EXTRA_ITEM_ID)
            } else {
                throw IllegalArgumentException("Id for item is not defined")
            }
        }
    }

    private fun setupMode() {
        when (mode) {
            MODE_ADD -> setupAdd()
            MODE_EDIT -> setupEdit()
            else -> throw IllegalArgumentException("Unknown type")
        }
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

    private fun setupAdd(){
        buttonSave.setOnClickListener {
            viewModel.addItem(editName.text, editCount.text)
        }
    }
    private fun setupEdit(){
        viewModel.getItem(itemId)

        buttonSave.setOnClickListener {
            viewModel.editItem(editName.text, editCount.text)
        }
    }

    private fun registerLiveData(){
        viewModel.itemLiveData.observe(viewLifecycleOwner){
            editName.setText(it.name)
            editCount.setText(it.count.toString())
        }

        viewModel.errorNameLD.observe(viewLifecycleOwner){
            tilName.error = if (it){
                null
            } else {
                getString(R.string.name_error)
            }
        }
        viewModel.errorCountLD.observe(viewLifecycleOwner){
            tilCount.error = if (it){
                null
            } else {
                getString(R.string.count_error)
            }
        }

        viewModel.finishActivityLD.observe(viewLifecycleOwner){
            activity?.onBackPressed()  // FIXME:
        }
    }

    companion object {

        fun newInstanceFragmentAdd() = ShopItemFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_MODE, MODE_ADD)
            }
        }
        fun newInstanceFragmentEdit(id: Long)= ShopItemFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_MODE, MODE_EDIT)
                putLong(EXTRA_ITEM_ID, id)
            }
        }

    }

}
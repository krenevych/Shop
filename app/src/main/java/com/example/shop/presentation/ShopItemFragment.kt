package com.example.shop.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shop.R
import com.example.shop.databinding.FragmentShopItemBinding
import com.example.shop.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var binding: FragmentShopItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var mode: String = MODE_UNDEF
    private var itemId: Long = ShopItem.UNDEFINED

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()
        setupMode()
        registerLiveData()
    }

    private lateinit var viewModel: ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e(TAG, "onCreate", )


        (activity as? FinishEditListener)?.let{
            finishEditListener = it
        } ?: throw IllegalArgumentException("Activity has to implement FinishEditListener")


        with(requireArguments()) {
            if (containsKey(EXTRA_MODE)) {
                mode = getString(EXTRA_MODE) ?: MODE_UNDEF
            } else throw IllegalArgumentException("Unknown type")

            if (mode == MODE_EDIT) {
                if (containsKey(EXTRA_ITEM_ID)) {
                    itemId = getLong(EXTRA_ITEM_ID)
                } else {
                    throw IllegalArgumentException("Id for item is not defined")
                }
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

    private fun initViews() {


        with(binding){
            editTextName.addTextChangedListener {
                tilName.error = null
            }

            editTextCount.addTextChangedListener {
                tilCount.error = null
            }
        }


    }

    private fun setupAdd(){
        with(binding){
            buttonSave.setOnClickListener {
                viewModel.addItem(editTextName.text, editTextCount.text)
            }
        }

    }
    private fun setupEdit(){
        viewModel.getItem(itemId)

        with(binding) {
            buttonSave.setOnClickListener {
                viewModel.editItem(editTextName.text, editTextCount.text)
            }
        }
    }

    private fun registerLiveData(){
        viewModel.itemLiveData.observe(viewLifecycleOwner){
            with(binding) {
                editTextName.setText(it.name)
                editTextCount.setText(it.count.toString())
            }

        }

        viewModel.errorNameLD.observe(viewLifecycleOwner){
            binding.tilName.error = if (it){
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
//            activity?.onBackPressed()  // FIXME:
            finishEditListener?.onFinishEdit()
        }
    }

    var finishEditListener: FinishEditListener? = null
    fun interface FinishEditListener {
        fun onFinishEdit()
    }

    private val tilCount: TextInputLayout
        get() = binding.tilCount

    companion object {

        const val TAG = "RRRRR"

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
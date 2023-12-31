package com.example.shop.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.R
import com.example.shop.databinding.ActivityMainBinding
import com.example.shop.domain.ShopItem
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), ShopItemFragment.FinishEditListener {

    private lateinit var viewModel: MainViewModel
//    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShopItemAdapter
//    private lateinit var buttonAdd: FloatingActionButton
//    private var fragmentContainer: FragmentContainerView? = null

    var item: ShopItem?= null

    lateinit var binding: ActivityMainBinding

    private fun setupFragment(fragment: ShopItemFragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
//            .add(R.id.shop_item_container, fragment)
            .replace(R.id.shop_item_container, fragment)
            .commit()
    }

    private val isPortrait : Boolean
        get() = binding.shopItemContainer  == null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)


//        fragmentContainer = findViewById(R.id.shop_item_container)
//
//        buttonAdd = findViewById(R.id.buttonAdd)
        binding.buttonAdd.setOnClickListener {
            if (isPortrait){
                lunchActivityForAdd()
            } else {
                setupFragment(ShopItemFragment.newInstanceFragmentAdd())
            }
        }

        adapter = ShopItemAdapter()
        binding.shopItemRecyclerView.adapter = adapter
        adapter.clickListener = { view: View, item: ShopItem->
//            Toast.makeText(this@MainActivity, item.toString(), Toast.LENGTH_SHORT).show()
            if (isPortrait) {
                lunchActivityForEdit(item.id)
            } else {
                setupFragment(ShopItemFragment.newInstanceFragmentEdit(item.id))
            }
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
        itemTouchHelper.attachToRecyclerView(binding.shopItemRecyclerView)

    }

    private fun lunchActivityForAdd(){
        val intent = Intent(this, ShopItemActivity::class.java)
        intent.putExtra(EXTRA_MODE, MODE_ADD)
        startActivity(intent)
    }

    private fun lunchActivityForEdit(itemId: Long){
        val intent = Intent(this, ShopItemActivity::class.java)
        intent.putExtra(EXTRA_MODE, MODE_EDIT)
        intent.putExtra(EXTRA_ITEM_ID, itemId)
        startActivity(intent)
    }

    companion object {

        val TAG = "XXXXXX"
    }

    override fun onFinishEdit() {
        onBackPressed()
    }
}
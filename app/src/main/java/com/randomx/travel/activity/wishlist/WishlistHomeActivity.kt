package com.randomx.travel.activity.wishlist

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.randomx.travel.R
import com.randomx.travel.activity.BaseActivity
import com.randomx.travel.adapter.WishlistAdapter
import com.randomx.travel.helper.SwipeItemTouchHelper
import com.randomx.travel.model.ProductModel
import com.randomx.travel.network.ApiResponse
import kotlinx.coroutines.runBlocking

class WishlistHomeActivity : BaseActivity() {
    private lateinit var parentView: View

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: WishlistAdapter
    private lateinit var mItemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist_home)
        parentView = findViewById(android.R.id.content)
        findViewById<TextView>(R.id.toolbar_title).text = getString(R.string.wishlist_title)

        initComponent()
    }


    private fun initComponent() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val items = getData().toMutableList()

        // Set data and list adapter
        mAdapter = WishlistAdapter(this, items)
        recyclerView.adapter = mAdapter

        // On item list clicked
        mAdapter.setOnItemClickListener(object : WishlistAdapter.OnItemClickListener {
            override fun onItemClick(view: View, obj: ProductModel, position: Int) {
                Snackbar.make(parentView, "Item ${obj.productName} clicked", Snackbar.LENGTH_SHORT).show()
            }
        })

        val callback: ItemTouchHelper.Callback = SwipeItemTouchHelper(mAdapter)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
                super.onOptionsItemSelected(item)
            }
        }
    }

    fun getData(): List<ProductModel> = runBlocking {
        val response: ApiResponse<List<ProductModel>> = apiProducts().getRandom(10)
        return@runBlocking response.data?:emptyList<ProductModel>()
    }

}

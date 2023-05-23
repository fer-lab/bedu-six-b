package com.randomx.travel.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewActivity: BaseActivity()
{

    protected lateinit var recyclerView: RecyclerView
    protected lateinit var adapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())

        initComponent()

        recyclerView = findViewById(getRecyclerViewResourceId())
        adapter = getAdapterInstance()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    protected abstract fun initComponent()

    protected abstract fun getLayoutResourceId(): Int

    protected abstract fun getRecyclerViewResourceId(): Int

    protected abstract fun getAdapterInstance(): RecyclerView.Adapter<*>

    protected abstract fun getData(): List<*>

}
package com.randomx.travel.adapter

import android.content.Context
import android.content.Intent
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder>(
    private val context: Context,
    private val data: List<T>,
    @LayoutRes private val layout_id: Int
) : RecyclerView.Adapter<VH>() {

    protected fun setItemClickListener(viewHolder: VH, item: T, destinationActivity: Class<*>, activityEntity: T) {
        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, destinationActivity)
            val json = Gson().toJson(activityEntity)
            intent.putExtra("entity", json)
            context.startActivity(intent)
        }

    }

}
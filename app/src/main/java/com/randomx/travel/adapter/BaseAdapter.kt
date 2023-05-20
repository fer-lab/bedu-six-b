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

    protected abstract fun entityCode(): String
    protected fun setItemClickListener(viewHolder: VH, item: T, activity: Class<*>, activityEntity: T) {
        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, activity)
            val json = Gson().toJson(activityEntity)
            intent.putExtra(entityCode(), json)
            context.startActivity(intent)
        }

    }

}
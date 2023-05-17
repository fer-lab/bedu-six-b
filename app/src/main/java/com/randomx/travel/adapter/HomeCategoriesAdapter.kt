package com.randomx.travel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.randomx.travel.R
import com.randomx.travel.model.CategoryModel
import com.randomx.travel.utils.ImageUtils

class HomeCategoriesAdapter(private val categories: List<CategoryModel>) : RecyclerView.Adapter<HomeCategoriesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryNameTextView: TextView = itemView.findViewById(R.id.home_category_name)
        private val categoryImageView: ImageView = itemView.findViewById(R.id.home_category_image)

        fun bind(category: CategoryModel) {
            Log.d("HomeCategoriesAdapter", "bind: ${category.categoryName}")
            categoryNameTextView.text = category.categoryName?:""
            // Load the image using a library like Glide or Picasso
            if (!category.categoryImage.isNullOrEmpty())
            {
                ImageUtils.loadImageFromUrl(itemView.context, category.categoryImage, categoryImageView)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_home_categories, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}

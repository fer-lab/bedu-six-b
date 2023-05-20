package com.randomx.travel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.randomx.travel.R
import com.randomx.travel.activity.category.CategoryActivity
import com.randomx.travel.model.CategoryModel
import com.randomx.travel.model.CategoryViewModel
import com.randomx.travel.model.DestinationModel
import com.randomx.travel.model.DestinationViewModel
import com.randomx.travel.utils.ImageUtils

class HomeCategoriesAdapter(private val context: Context, private val data: List<CategoryModel>, @LayoutRes private val layout_id: Int) : BaseAdapter<CategoryModel, HomeCategoriesAdapter.ViewHolder>(context, data, layout_id) {


    override fun entityCode(): String {
        return "category"
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryNameTextView: TextView = itemView.findViewById(R.id.home_category_name)
        private val categoryImageView: ImageView = itemView.findViewById(R.id.home_category_image)

        fun bind(category: CategoryModel) {
            categoryNameTextView.text = category.categoryName?:""

            if (!category.categoryImage.isNullOrEmpty())
            {
                ImageUtils.loadImageFromUrl(itemView.context, category.categoryImage, categoryImageView)
            }

            setItemClickListener(this, category, CategoryActivity::class.java, category)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layout_id, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

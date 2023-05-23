package com.randomx.travel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.randomx.travel.R
import com.randomx.travel.activity.category.CategoryProductActivity
import com.randomx.travel.activity.destination.DestinationProductActivity
import com.randomx.travel.model.ProductModel
import com.randomx.travel.utils.ImageUtils

class ProductsAdapter(private val context: Context, private val data: List<ProductModel>, @LayoutRes private val layout_id: Int, private val owner: String, private val ownerSerialized: String) : BaseAdapter<ProductModel, ProductsAdapter.ViewHolder>(context, data, layout_id) {

    override fun entityCode(): String {
        return "product"
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productNameTextView: TextView = itemView.findViewById(R.id.product_name)
        private val productImageView: ImageView = itemView.findViewById(R.id.product_image)

        fun bind(product: ProductModel) {

            productNameTextView.text = product.productName?:"Hola"

            if (!product.productImage.isNullOrEmpty())
            {
                ImageUtils.loadImageFromUrl(itemView.context, product.productImage, productImageView)
            }

            if (owner === "destination")
            {
                setItemClickListener(this, product, DestinationProductActivity::class.java, product, owner, ownerSerialized)
            }
            else if (owner === "category")
            {
                setItemClickListener(this, product, CategoryProductActivity::class.java, product, owner, ownerSerialized)
            }
            else
            {
                throw NoSuchFieldError("Caller not found")
            }


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

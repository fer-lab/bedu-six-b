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
import com.randomx.travel.activity.destination.DestinationActivity
import com.randomx.travel.model.DestinationModel
import com.randomx.travel.model.DestinationViewModel
import com.randomx.travel.utils.ImageUtils

class HomeDestinationsAdapter(private val context: Context, private val data: List<DestinationModel>, @LayoutRes private val layout_id: Int) : BaseAdapter<DestinationModel, HomeDestinationsAdapter.ViewHolder>(context, data, layout_id) {

    override fun entityCode(): String {
        return "destination"
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val destinationNameTextView: TextView = itemView.findViewById(R.id.home_destination_name)
        private val destinationImageView: ImageView = itemView.findViewById(R.id.home_destination_image)

        fun bind(destination: DestinationModel) {

            destinationNameTextView.text = destination.destinationName?:"Hola"

            if (!destination.destinationImage.isNullOrEmpty())
            {
                ImageUtils.loadImageFromUrl(itemView.context, destination.destinationImage, destinationImageView)
            }

            setItemClickListener(this, destination, DestinationActivity::class.java, destination)
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

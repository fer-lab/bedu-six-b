package com.randomx.travel.activity.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.randomx.travel.data.local.Wishlist
import com.randomx.travel.databinding.WishlistItemBinding

class WishlistAdapter(
    private val viewModel: WishlistListViewModel
) :
    ListAdapter<Wishlist, WishlistAdapter.ViewHolder>(WishDiffCallback()) {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder private constructor(private val binding: WishlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: WishlistListViewModel, item: Wishlist) {
            binding.viewModel = viewModel
            binding.product = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WishlistItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(viewGroup)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.bind(viewModel, item)
    }

    class WishDiffCallback : DiffUtil.ItemCallback<Wishlist>() {
        override fun areItemsTheSame(oldItem: Wishlist, newItem: Wishlist): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Wishlist, newItem: Wishlist): Boolean {
            return oldItem == newItem
        }
    }

}

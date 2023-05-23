package com.randomx.travel.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.randomx.travel.R
import com.randomx.travel.helper.SwipeItemTouchHelper
import com.randomx.travel.model.ProductModel
import com.randomx.travel.utils.ImageUtils

class WishlistAdapter(private val ctx: Context, private val items: MutableList<ProductModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), SwipeItemTouchHelper.SwipeHelperAdapter {

    private val itemsSwiped = ArrayList<ProductModel>()
    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: ProductModel, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mItemClickListener
    }

    inner class OriginalViewHolder(v: View) : RecyclerView.ViewHolder(v), SwipeItemTouchHelper.TouchViewHolder {
        val image: ImageView = v.findViewById(R.id.image)
        val name: TextView = v.findViewById(R.id.name)
        val btMove: ImageButton = v.findViewById(R.id.bt_move)
        val btUndo: Button = v.findViewById(R.id.bt_undo)
        val lytParent: View = v.findViewById(R.id.lyt_parent)

        override fun onItemSelected() {
            itemView.setBackgroundColor(ctx.resources.getColor(R.color.grey_5))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.wishlist_item, parent, false)
        return OriginalViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OriginalViewHolder) {
            val view = holder
            val product = items[position]

            view.name.text = product.productName
            ImageUtils.loadImageFromUrl(ctx, product.productImage as String, view.image)

            view.lytParent.setOnClickListener {
                mOnItemClickListener?.onItemClick(it, items[position], position)
            }

            view.btUndo.setOnClickListener {
                items[position].productSwiped = false
                itemsSwiped.remove(items[position])
                notifyItemChanged(position)
            }

            if (product.productSwiped == true) {
                view.lytParent.visibility = View.GONE
            } else {
                view.lytParent.visibility = View.VISIBLE
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                for (s in itemsSwiped) {
                    val indexRemoved = items.indexOf(s)
                    if (indexRemoved != -1) {
                        items.removeAt(indexRemoved)
                        notifyItemRemoved(indexRemoved)
                    }
                }
                itemsSwiped.clear()
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onItemDismiss(position: Int) {
        // handle when double swipe
        if (items[position].productSwiped == true) {
            itemsSwiped.remove(items[position])
            items.removeAt(position)
            notifyItemRemoved(position)
            return
        }

        items[position].productSwiped = true
        itemsSwiped.add(items[position])
        notifyItemChanged(position)
    }
}

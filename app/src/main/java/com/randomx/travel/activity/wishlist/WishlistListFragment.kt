package com.randomx.travel.activity.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.randomx.travel.R
import com.randomx.travel.RandomApp
import com.randomx.travel.activity.MainActivity
import com.randomx.travel.activity.category.CategoryProductActivity
import com.randomx.travel.activity.destination.DestinationProductActivity
import com.randomx.travel.data.local.Wishlist
import com.randomx.travel.databinding.FragmentWishlistListBinding
import com.randomx.travel.model.ProductCallerModel

class WishlistListFragment : Fragment(), WishlistItemListener {



    private lateinit var adapter: WishlistAdapter
    private lateinit var viewModel: WishlistListViewModel
    private lateinit var binding: FragmentWishlistListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = WishlistListViewModel((requireActivity().application as RandomApp).wishlistRepository)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateToDetails.observe(viewLifecycleOwner) { wish ->
            wish?.let {

                val productCaller = ProductCallerModel(
                    callerId = wish.caller_id.toString(),
                    callerType = wish.caller_type.toString()
                )

                val activityDestination = when {
                    productCaller.isCategory() -> CategoryProductActivity::class.java
                    productCaller.isDestination() -> DestinationProductActivity::class.java
                    else -> MainActivity::class.java
                }


                activityDestination.let {
                    val intent = Intent(requireContext(), it).apply {
                        putExtra("${productCaller.callerType}_id", productCaller.callerId)
                        putExtra("product_id", wish.product_id)
                    }
                    startActivity(intent)
                    viewModel.onNavigationDone()

                }

            }
        }

        // Resto del código...
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_wishlist_list,
            container,
            false
        )

        setupWishlist()

        return binding.root
    }

    override fun onEdit(wishlist: Wishlist) {


    }

    override fun onDelete(wishlist: Wishlist) {

    }

    private fun setupWishlist(){

        adapter = WishlistAdapter(viewModel)
        binding.list.adapter = adapter

        viewModel.wishlist.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }

            if (it.isEmpty())
            {
                binding.listEmptyMessage.visibility = View.VISIBLE
            }
            else
            {
                binding.listEmptyMessage.visibility = View.GONE
            }
        }

    }



}
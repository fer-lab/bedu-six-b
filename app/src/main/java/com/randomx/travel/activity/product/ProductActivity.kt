package com.randomx.travel.activity.product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.randomx.travel.R
import com.randomx.travel.RandomApp
import com.randomx.travel.activity.BaseActivity
import com.randomx.travel.data.local.Wishlist
import com.randomx.travel.data.local.WishlistRepository
import com.randomx.travel.model.ProductCaller
import com.randomx.travel.model.ProductModel
import com.randomx.travel.utils.DialogUtils
import com.randomx.travel.utils.ImageUtils
import com.randomx.travel.utils.RandomUtils
import com.randomx.travel.utils.ToolsUtils
import com.randomx.travel.utils.ViewAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

abstract class ProductActivity : BaseActivity() {


    protected lateinit var product: ProductModel
    protected lateinit var productCaller: ProductCaller
    private lateinit var parentView: View
    private lateinit var wishlistRepo: WishlistRepository

    private lateinit var nestedScrollView: NestedScrollView

    protected abstract fun initCaller()
    protected abstract fun initProduct()

    protected abstract fun initComponent()

    protected abstract fun getLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        initComponentRoot()

    }

    protected fun product404(routeClass: Class<*>, message: String? = null)
    {
        DialogUtils.toastThenActivity(this, message ?: "Product not found", routeClass)
    }


    protected fun currentProduct(): ProductModel?
    {
        var currentProduct: ProductModel? = null
        val productJson = intent.getStringExtra("product")
        val productId = intent.getStringExtra("product_id")

        when{
            productJson != null -> {
                currentProduct = ProductModel.fromJson(productJson)
            }
            productId != null ->
            {
                runBlocking {
                    val apiProduct = withContext(Dispatchers.IO) { apiProducts().get(productId).data }
                    if (apiProduct != null) {
                        currentProduct = apiProduct
                    }
                }
            }
        }

        return currentProduct
    }

    private fun initComponentRoot() {

        wishlistRepo = (this.applicationContext as RandomApp).wishlistRepository

        initProduct()
        initCaller()
        initToolbar()

        parentView = findViewById(R.id.parent_view)

        // nested scrollview
        nestedScrollView = findViewById(R.id.nested_scroll_view)


        val sections = listOf(
            Pair(R.id.bt_toggle_description, R.id.lyt_expand_description),
            Pair(R.id.bt_toggle_itinerary, R.id.lyt_expand_itinerary),
            Pair(R.id.bt_toggle_reviews, R.id.lyt_expand_reviews)
        )

        for ((index, section) in sections.withIndex()) {
            val btToggle = findViewById<ImageButton>(section.first)
            val lytExpand = findViewById<View>(section.second)

            setupToggleSectionButton(btToggle, lytExpand)

            if (index == 0) {
                toggleArrow(btToggle)
                lytExpand.visibility = View.VISIBLE
            }
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {


            CoroutineScope(Dispatchers.IO).launch {
                val productWishlist = wishlistRepo.getByProductId(product.productID.toString())


                Log.d("ProductActivity", "productWishlist: $productWishlist")
                if (productWishlist !== null) {
                    wishlistRepo.remove(productWishlist)
                    ToolsUtils.snack(parentView ,getString(R.string.wishlist_confirm_message_remove))
                }
                else
                {
                    wishlistRepo.insert(Wishlist(
                        id = 0,
                        product_id = product.productID,
                        product_name = product.productName,
                        caller_id = productCaller.callerId,
                        caller_type = productCaller.callerType,
                        image = product.productImage)
                    )

                    ToolsUtils.snack(parentView ,getString(R.string.wishlist_confirm_message))
                }



            }

        }

        findViewById<FloatingActionButton>(R.id.fab2).setOnClickListener {
            shareLink()
        }

        initComponent()
        productPopulate()

    }

    protected fun productPopulate()
    {
        ImageUtils.loadImageFromUrl(this, product.productImage as String, findViewById<ImageView>(R.id.product_image))
        findViewById<TextView>(R.id.product_name).text = product.productName
        //findViewById<TextView>(R.id.product_name_subame).text = product.productName
        findViewById<TextView>(R.id.product_description).text = ToolsUtils.removeHtmlTags(product.productContentShort as String)
        findViewById<TextView>(R.id.product_itinerary).text = ToolsUtils.removeHtmlTags(product.productContentFull as String)

        val lytExpandReviews: LinearLayout = findViewById(R.id.lyt_expand_reviews)

        val randomComments = RandomUtils.generateRandomComments(this, 5)
        var totalReviews = 0
        var totalReviewsScore = 0.0F

        for (comment in randomComments) {

            totalReviews++
            totalReviewsScore += comment.second

            val commentView = LayoutInflater.from(this).inflate(R.layout.content_product_reviews_item, lytExpandReviews, false)

            val ratingBar = commentView.findViewById<RatingBar>(R.id.rating_bar)
            ratingBar.rating = comment.second

            val authorTextView = commentView.findViewById<TextView>(R.id.rating_author)
            authorTextView.text = comment.first

            lytExpandReviews.addView(commentView)
        }

        totalReviewsScore = totalReviewsScore / totalReviews
        findViewById<RatingBar>(R.id.product_rating).rating = totalReviewsScore
        findViewById<TextView>(R.id.product_rating_count).text = "(${totalReviews.toString()})"
        findViewById<TextView>(R.id.product_price).text = RandomUtils.getRandomFormattedNumber()





    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Fashion"
    }

    private fun setupToggleSectionButton(btToggle: ImageButton, lytExpand: View) {
        btToggle.setOnClickListener {
            toggleSection(it, lytExpand)
        }
    }

    private fun toggleSection(bt: View, lyt: View) {
        val show = toggleArrow(bt)
        if (show) {
            ViewAnimation.expand(lyt, object : ViewAnimation.AnimListener {
                override fun onFinish() {
                    ToolsUtils.nestedScrollTo(nestedScrollView, lyt)
                }
            })
        } else {
            ViewAnimation.collapse(lyt)
        }
    }

    private fun toggleArrow(view: View): Boolean {
        val rotation = view.rotation
        val targetRotation = if (rotation == 0f) 180f else 0f
        view.animate().rotation(targetRotation)
        view.animate().rotation(targetRotation)
        return targetRotation == 180f
    }

    private fun shareLink() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Link de BeduTravel: https://travel.bedu.pruebas.dev/paquete/${product.productSlug}")

        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }

}
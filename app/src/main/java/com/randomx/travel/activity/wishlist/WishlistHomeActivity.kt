package com.randomx.travel.activity.wishlist

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.randomx.travel.R
import com.randomx.travel.activity.BaseActivity

class WishlistHomeActivity : BaseActivity() {
    private lateinit var parentView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist_home)
        parentView = findViewById(android.R.id.content)
        findViewById<TextView>(R.id.toolbar_title).text = getString(R.string.wishlist_title)
    }
}

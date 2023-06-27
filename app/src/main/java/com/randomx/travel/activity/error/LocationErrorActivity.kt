package com.randomx.travel.activity.error

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.randomx.travel.R
import com.randomx.travel.activity.tours.ToursHomeActivity
import com.randomx.travel.databinding.ActivityErrorLocationBinding
import com.randomx.travel.utils.ToolsUtils

class LocationErrorActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var lytNoConnection: LinearLayout
    private lateinit var btnRetry: Button
    private lateinit var binding: ActivityErrorLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initComponent()
    }

    private fun initViews() {
        progressBar = findViewById(R.id.progress_bar)
        lytNoConnection = findViewById(R.id.lyt_no_connection)
        btnRetry = findViewById(R.id.no_internet_retry)
    }

    private fun initComponent() {
        progressBar.visibility = View.GONE
        lytNoConnection.visibility = View.VISIBLE

        btnRetry.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            lytNoConnection.visibility = View.GONE

            Handler(Looper.getMainLooper()).postDelayed({
                if (ToolsUtils.isNetworkAvailable(this)) {
                    if (isTaskRoot) {
                        ToolsUtils.goToActivity(this, ToursHomeActivity::class.java)
                    } else {
                        finish()
                    }
                } else {
                    progressBar.visibility = View.GONE
                    lytNoConnection.visibility = View.VISIBLE
                }
            }, 1000)
        }
    }
}

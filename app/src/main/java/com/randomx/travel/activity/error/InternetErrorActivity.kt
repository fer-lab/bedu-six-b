package com.randomx.travel.activity.error

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.randomx.travel.R
import com.randomx.travel.activity.MainActivity
import com.randomx.travel.utils.ToolsUtils

class InternetErrorActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var lytNoConnection: LinearLayout
    private lateinit var btnRetry: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error_internet)
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
                        redirectToMainActivity()
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

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


}

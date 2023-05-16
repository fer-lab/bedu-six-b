package com.randomx.travel.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.randomx.travel.R

class ProgressFragment : Fragment() {

    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_progress, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        return view
    }

    // Método para actualizar el progreso del ProgressBar desde la actividad principal o cualquier otra clase
    fun setProgress(progress: Int) {
        progressBar.progress = progress
    }

    fun setProgressVisibility(visibility: Int) {
        progressBar.visibility = visibility
    }
}

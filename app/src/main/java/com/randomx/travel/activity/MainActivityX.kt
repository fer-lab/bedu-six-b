package com.randomx.travel.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.randomx.travel.R
import com.randomx.travel.activity.auth.LoginScreen
import com.randomx.travel.activity.home.HomeActivity
import com.randomx.travel.network.datasource.ManagerDataSource
import com.randomx.travel.utils.DialogUtils
import com.randomx.travel.utils.ToolsUtils
import com.randomx.travel.utils.UserSessionUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivityX : AppCompatActivity() {
    private lateinit var btnTestApi: Button
    private val dataManager = ManagerDataSource.getInstance()
    private lateinit var progressBarFrame: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UserSessionUtils.init(this)

        if (UserSessionUtils.isUserLogged())
        {
            ToolsUtils.goToActivity(this@MainActivityX, HomeActivity::class.java)
        } else
        {
            ToolsUtils.goToActivity(this@MainActivityX, LoginScreen::class.java)
        }


    }

    override fun onResume() {
        super.onResume()
       // DialogUtils.showSuccessDialog(this, "Resume")
    }

    private fun getDestination(id: String) {

            progressBarFrame.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val response = dataManager.destinations().get(id)

                    if (response.isSuccessful && response.isSuccessful && response.data != null) {

                        val destination = response.data
                        DialogUtils.showSuccessDialog(this@MainActivityX, "Destino: ${destination.destinationName}")

                    } else {

                        DialogUtils.showErrorDialog(this@MainActivityX, response.error?.message ?: "Error al obtener el destino")
                    }

                } catch (e: Exception) {
                    DialogUtils.showErrorDialog(this@MainActivityX, "Error al obtener el destino")
                } finally {
                    progressBarFrame.visibility = View.GONE
                }

        }
    }

    private fun getDestinations() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val destinations = dataManager.destinations().getAll()
                // Manejar el resultado
                destinations?.let {
                    // Hacer algo con la lista de DestinationModel
                    Log.d("MainActivity", "Destinations: $it")
                } ?: run {
                    // La respuesta fue nula
                    Log.d("MainActivity", "Error: Respuesta nula")
                }
            } catch (e: Exception) {
                // Manejar la excepción
                Log.e("MainActivity", "Error: ${e.message}")
            }
        }
    }

    fun goToLoginScreen() {
        val intent = Intent(this@MainActivityX, LoginScreen::class.java)
        startActivity(intent)
    }


}
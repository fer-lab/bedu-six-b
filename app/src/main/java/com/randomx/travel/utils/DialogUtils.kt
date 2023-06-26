package com.randomx.travel.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import com.randomx.travel.R

object DialogUtils {


    fun showDialog(context: Context, title: String, message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    fun showConfirmationDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonLabel: String,
        negativeButtonLabel: String,
        positiveButtonListener: DialogInterface.OnClickListener,
        negativeButtonListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonLabel, positiveButtonListener)
            .setNegativeButton(negativeButtonLabel, negativeButtonListener)
            .show()
    }



    fun showErrorDialog(context: Context, message: String, title: String? = null) {
        val alertDialogBuilder = AlertDialog.Builder(context, R.style.AlertDialogError)
            .setTitle(title?:"Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    fun showErrorNotImplemented(context: Context, message: String? = null) {

        showErrorDialog(context, message ?: "Not implemented yet");
    }
    fun toastNotImplemented(context: Context, message: String? = null) {

        toast(context, message ?: "Not implemented yet");
    }

    fun showSuccessDialog(context: Context, message: String, title: String? = null) {
        val alertDialogBuilder = AlertDialog.Builder(context, R.style.AlertDialogSuccess)
            .setTitle(title ?: "Success")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun toast(context: Context, message: String)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun toastThenActivity(context: Context, message: String, destinationClass: Class<*>)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()


        // Crear un HandlerThread
        val handlerThread = HandlerThread("MyHandlerThread")
        handlerThread.start()

        // Crear un Handler asociado al HandlerThread
        val handler = Handler(handlerThread.looper)

        // Ejecutar una acción después de cierto tiempo
        val delayMillis = 500L // 2000 milisegundos = 2 segundos

        handler.postDelayed({
            ToolsUtils.goToActivity(context, destinationClass)
        }, delayMillis)


    }
}

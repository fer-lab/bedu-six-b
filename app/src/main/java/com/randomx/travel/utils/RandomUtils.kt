package com.randomx.travel.utils

import android.content.Context
import com.randomx.travel.R
import java.text.DecimalFormat
import kotlin.random.Random

object RandomUtils {

    fun getRandomFormattedNumber(): String {
        val randomFloat = Random.nextFloat() * (10000 - 2000) + 2000
        val decimalFormat = DecimalFormat("$0,000.00")
        return decimalFormat.format(randomFloat)
    }

    fun generateRandomComments(context: Context, count: Int): List<Pair<String, Float>> {
        val random = java.util.Random()
        val names: List<String> = ToolsUtils.getListFromArray(context, R.array.persons)

        val randomComments = mutableListOf<Pair<String, Float>>()
        val selectedNames = mutableListOf<String>()

        repeat(count) {
            val availableNames = names - selectedNames
            if (availableNames.isEmpty()) {
                // Si ya se han seleccionado todas las personas, reinicia la selección
                selectedNames.clear()
            }

            val randomName = availableNames[random.nextInt(availableNames.size)]
            selectedNames.add(randomName)

            val randomRating = (random.nextInt(9) + 1) * 0.5f
            val comment = randomName to randomRating
            randomComments.add(comment)
        }
        return randomComments
    }


}
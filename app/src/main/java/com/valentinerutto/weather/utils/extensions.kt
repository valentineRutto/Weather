package com.valentinerutto.weather.utils

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.valentinerutto.weather.R
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

val Int?.orZero
    get() = this ?: 0

val Long?.orZero
    get() = this ?: 0L

val Float?.orZero: Float
    get() = this ?: 0f

val Double?.orZero: Double
    get() = this ?: 0.0

fun convertKelvinToCelsius(kelvin: Float): String {
    return (kelvin - 273.15f).roundToInt().toString() + "\u00B0"
}

fun setBackground(
    context: Context,
    description: String,
    recyclerView: RecyclerView,
    currentConstraintLayout: ConstraintLayout,
    weatherDescConstraintLayout: ConstraintLayout
) {
    if (description.contains("sun", true)) {
        recyclerView.setBackgroundColor(
            androidx.core.content.ContextCompat.getColor(
                context,
                R.color.sunny
            )
        )
        weatherDescConstraintLayout.setBackgroundColor(
            androidx.core.content.ContextCompat.getColor(
                context,
                R.color.sunny
            )
        )
        currentConstraintLayout.setBackgroundResource(R.drawable.forest_sunny)


    } else if (description.contains("cloud", true)) {
        currentConstraintLayout.setBackgroundResource(R.drawable.forest_cloudy)

        recyclerView.setBackgroundColor(
            androidx.core.content.ContextCompat.getColor(
                context,
                R.color.cloudy
            )
        )
        weatherDescConstraintLayout.setBackgroundColor(
            androidx.core.content.ContextCompat.getColor(
                context,
                R.color.cloudy
            )
        )
    } else if (description.contains("rain", true)) {
        currentConstraintLayout.setBackgroundResource(R.drawable.forest_rainy)

        recyclerView.setBackgroundColor(
            androidx.core.content.ContextCompat.getColor(
                context,
                R.color.rainy
            )
        )
        weatherDescConstraintLayout.setBackgroundColor(
            androidx.core.content.ContextCompat.getColor(
                context,
                R.color.rainy
            )
        )
    } else {
        currentConstraintLayout.setBackgroundColor(
            androidx.core.content.ContextCompat.getColor(
                context,
                R.color.sunny
            )
        )
        recyclerView.setBackgroundColor(
            androidx.core.content.ContextCompat.getColor(
                context,
                R.color.sunny
            )
        )
        weatherDescConstraintLayout.setBackgroundColor(
            androidx.core.content.ContextCompat.getColor(
                context,
                R.color.sunny
            )
        )
    }
}


fun getDayOfWeek(timestamp: Long): String {
    return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
}
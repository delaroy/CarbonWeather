package com.carbon.carbonweather.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.carbon.carbonweather.utils.convertToForecastDate
import com.carbon.domain.model.WeatherResponseData

@BindingAdapter("weatherDate")
fun bindWeatherDate(view: TextView, value: WeatherResponseData?) {
    value?.let {
        val weatherDate = convertToForecastDate(value.weatherDate)
        view.text = weatherDate
    }
}

@BindingAdapter("weatherIcon")
fun bindWeatherIcon(view: ImageView, value: WeatherResponseData?) {
    value?.let {
        val iconUrl = "http://openweathermap.org/img/w/${value.weatherIcon}.png";

        Glide.with(view)
            .load(iconUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("minMax")
fun minMax(view: TextView, value: WeatherResponseData?) {
    value?.let {
        val degree = 0x00B0.toChar()
        "${String.format("%.2f", value.temperatureMax)}/${
            String.format(
                "%.2f",
                value.temperatureMin
            )
        }${degree}C".also { view.text = it }
    }
}

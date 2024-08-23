package com.carbon.carbonweather.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.carbon.carbonweather.databinding.FragmentWeatherBinding
import com.carbon.carbonweather.utils.CustomProgressDialog
import com.carbon.carbonweather.utils.convertLongToTime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null

    private val binding get() = _binding!!
    private val progressDialog by lazy { CustomProgressDialog(requireActivity()) }
    private val viewModel: WeatherViewModel by viewModels()
    private val weatherAdapter: WeatherAdapter by lazy { WeatherAdapter() }
    private var query = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.forecastRecyclerview.apply {
            adapter = weatherAdapter
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryText: String?): Boolean {
                query = queryText ?: ""
                viewModel.getCoordinates(query.trim())

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                Log.d("onQueryTextChange", "query: $query")
                return true
            }
        })

        viewModel.fetchAllForecasts()
        viewModel.fetchCurrentWeather()

        observeCoordinates()
        observeCurrentWeather()
        observeForecasts()
    }

    private fun observeCurrentWeather() {
        viewModel.currentWeatherDetails.observe(requireActivity()) { result ->
            result?.getContentIfNotHandled()?.let { value ->
                binding.noData.visibility = GONE
                binding.weatherDetail.visibility = VISIBLE
                binding.weatherDate.text = convertLongToTime(value.weatherDate)
                "${value.cityName}, ${value.countryCode}".also { binding.location.text = it }

                val iconUrl = "http://openweathermap.org/img/w/${value.weatherIcon}.png";

                Glide.with(binding.imageView)
                    .load(iconUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imageView)

                val degree = 0x00B0.toChar()
                "${String.format("%.2f", value.weatherMainTemperature)}${degree}C".also { binding.weatherCelsius.text = it }

               "Feels like ${
                    String.format(
                        "%.2f",
                        value.feelsLike
                    )
                }${degree}C. ${value.weatherMain}".also { binding.feelsLike.text = it }

                (value.humidity.toString() + "%").also { binding.humidityFigure.text = it }

                (value.windSpeed.toString() + "m/s").also { binding.windFigure.text = it }

                (value.pressure.toString() + "hPa").also { binding.pressureFigure.text = it }
            }
        }
    }

    private fun observeForecasts() {
        viewModel.weatherForecasts.observe(requireActivity()) { result ->
            result?.getContentIfNotHandled()?.let { value ->
                binding.noData.visibility = GONE
                weatherAdapter.submitList(value)
            }
        }
    }

    private fun observeCoordinates() {
        viewModel.getCoordinates.observe(requireActivity()) { result ->
            result?.getContentIfNotHandled()?.let { value ->
                when {
                    value.isSuccess() -> {
                        value.data?.let { data ->
                            viewModel.fetchAllForecasts()
                            viewModel.fetchCurrentWeather()
                        }
                        progressDialog.stop()
                    }

                    value.isLoading() -> {
                        progressDialog.start(title = "fetching data ...")
                    }

                    value.isError() -> {
                        progressDialog.stop()
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

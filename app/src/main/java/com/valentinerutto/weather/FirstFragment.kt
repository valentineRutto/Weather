package com.valentinerutto.weather

import android.content.BroadcastReceiver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.valentinerutto.weather.data.local.entities.DailyWeatherEntity
import com.valentinerutto.weather.databinding.FragmentFirstBinding
import com.valentinerutto.weather.ui.ForecastAdapter
import com.valentinerutto.weather.ui.OnWeatherClicked
import com.valentinerutto.weather.ui.WeatherViewmodel
import com.valentinerutto.weather.utils.WeatherForecast
import com.valentinerutto.weather.utils.getDayOfWeek
import com.valentinerutto.weather.utils.setBackground
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private lateinit var weatherAdapter: ForecastAdapter

    private var _binding: FragmentFirstBinding? = null
    private val weatherViewModel by sharedViewModel<WeatherViewmodel>()
    private lateinit var receiver: BroadcastReceiver

    //Düsseldorf
    private var DEFAULT_LONGITUDE = "6.773456"
    private var DEFAULT_LATITUDE = "51.227741"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Weather"

        setupObservers()

        weatherAdapter = ForecastAdapter(object : OnWeatherClicked {})


        binding.favoritesFloatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }


    }

    private fun setupObservers() {
        weatherViewModel.location.observe(viewLifecycleOwner) {
            DEFAULT_LATITUDE = it.latitude

            DEFAULT_LONGITUDE = it.longitude

            lifecycleScope.launch {
                weatherViewModel.getWeatherAndForecast(
                    DEFAULT_LATITUDE, DEFAULT_LONGITUDE, BuildConfig.OPEN_WEATHER_API_KEY
                )
            }
        }

        weatherViewModel.errorResponse.observe(viewLifecycleOwner) { errorMsg ->
            binding.temperatureTextview.text = errorMsg
        }

        weatherViewModel.successfulResponse.observe(viewLifecycleOwner) {
            it?.let { it1 -> setUpViews(it1) }
        }

        weatherViewModel.isLoading.observe(viewLifecycleOwner) { showLoading ->
            binding.weatherProgressBar.isVisible = showLoading
        }

    }

    private fun setUpViews(weatherForecast: WeatherForecast) {
        binding.weatherDescriptionTextview.text = weatherForecast.weather.main
        binding.temperatureTextview.text = weatherForecast.weather.temperature.toString()

        binding.maximumTemperatureTitle.text = weatherForecast.weather.temperatureMax.toString()

        binding.minimumTemperatureTexview.text = weatherForecast.weather.temperatureMin.toString()

        binding.currentTemperatureTextview.text = weatherForecast.weather.temperature.toString()

        val daily = weatherForecast.forecasts.map {
            DailyWeatherEntity(
                id = 0,
                weather = it.description,

                temperature = it.temperature.toString(),
                day = getDayOfWeek(it.timeStampL),
                weatherDesc = weatherForecast.weather.main
            )

        }
        setBackground(
            requireActivity(),
            weatherForecast.weather.main,
            binding.weeklyForecastRecyclerview,
            binding.currentWeatherLayout,
            binding.weatherTextLayout
        )

        binding.weeklyForecastRecyclerview.adapter = weatherAdapter.apply {
            submitList(daily)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
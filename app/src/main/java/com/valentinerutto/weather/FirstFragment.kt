package com.valentinerutto.weather

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.valentinerutto.weather.data.local.entities.DailyWeatherEntity
import com.valentinerutto.weather.databinding.FragmentFirstBinding
import com.valentinerutto.weather.ui.ForecastAdapter
import com.valentinerutto.weather.ui.OnWeatherClicked
import com.valentinerutto.weather.ui.WeatherViewmodel
import com.valentinerutto.weather.utils.WeatherForecast
import com.valentinerutto.weather.utils.convertKelvinToCelsius
import com.valentinerutto.weather.utils.getDayOfWeek
import com.valentinerutto.weather.utils.setRecyclerViewBackground
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
        weatherAdapter = ForecastAdapter(object : OnWeatherClicked {

        })

        binding.favouriteIcon.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        (activity as AppCompatActivity).supportActionBar?.title = "Weather"
//        lifecycleScope.launch {
//            weatherViewModel.getWeatherAndForecast(
//                "51.227741", "6.773456", BuildConfig.OPEN_WEATHER_API_KEY
//            )
//        }

        weatherViewModel.errorResponse.observe(viewLifecycleOwner) { errorMsg ->
            binding.temperatureTextview.text = errorMsg
        }

        weatherViewModel.successfulResponse.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it?.weather.toString(), Toast.LENGTH_LONG).show()
            it?.let { it1 -> setUpViews(it1) }
        }
    }

    private fun setUpViews(weatherForecast: WeatherForecast) {
        binding.weatherDescriptionTextview.text = weatherForecast.weather.main
        binding.temperatureTextview.text =
           weatherForecast.weather.temperature.toString()

        binding.maximumTemperatureTextview.text =
            weatherForecast.weather.temperatureMax.toString()

        binding.minimumTemperatureTexview.text =
            weatherForecast.weather.temperatureMin.toString()

        binding.currentTemperatureTextview.text =
            weatherForecast.weather.temperature.toString()

        val daily = weatherForecast.forecasts.map {
            DailyWeatherEntity(
                id = 0, weather = it.description,

                temperature = it.temperature.toString(),day = getDayOfWeek( it.timeStampL), weatherDesc = weatherForecast.weather.main
            )

        }

        setRecyclerViewBackground(requireActivity(), weatherForecast.weather.main,binding!!.weeklyForecastRecyclerview)

        binding.weeklyForecastRecyclerview.adapter = weatherAdapter.apply {
            submitList(daily)
        }
    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == activity?.packageName.toString() + "MY_LOCATION") {

                weatherViewModel._latitude.value =
                    intent.extras?.getDouble(LocationService.LAT_KEY, 0.0).toString()

                weatherViewModel.mlatitude =
                    intent.extras?.getDouble(LocationService.LAT_KEY, 0.0).toString()

                weatherViewModel._longitude.value =
                    intent.extras?.getDouble(LocationService.LNG_KEY, 0.0).toString()
                weatherViewModel.mlongitude
                intent.extras?.getDouble(LocationService.LNG_KEY, 0.0).toString()
            }

            lifecycleScope.launch {

                weatherViewModel.getWeatherAndForecast(
                    weatherViewModel.mlatitude,
                    weatherViewModel.mlongitude,
                    BuildConfig.OPEN_WEATHER_API_KEY
                )
            }

          //  weatherViewModel.fetchResponse()

        }
    }

    override fun onPause() {
        super.onPause()
        context?.unregisterReceiver(receiver)

    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(activity?.packageName.toString() + "MY_LOCATION")
        receiver = MyBroadcastReceiver()
        activity?.registerReceiver(receiver, filter)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
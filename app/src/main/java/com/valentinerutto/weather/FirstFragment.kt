package com.valentinerutto.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.valentinerutto.weather.data.local.entities.DailyWeatherEntity
import com.valentinerutto.weather.databinding.FragmentFirstBinding
import com.valentinerutto.weather.ui.ForecastAdapter
import com.valentinerutto.weather.ui.OnWeatherClicked
import com.valentinerutto.weather.ui.WeatherViewmodel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private lateinit var weatherAdapter: ForecastAdapter

    private var _binding: FragmentFirstBinding? = null
    private val weatherViewModel by sharedViewModel<WeatherViewmodel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
weatherAdapter = ForecastAdapter(object : OnWeatherClicked{


})



        binding.favouriteIcon.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        weatherViewModel.errorResponse.observe(viewLifecycleOwner) { errorMsg ->
            binding.temperatureTextview.text = errorMsg
        }

        weatherViewModel.successfulResponse.observe(viewLifecycleOwner) {
           binding.weatherDescriptionTextview.text = it?.daily?.flatMap { it.weather.map { it.main } }.toString()
          val wweather = it?.daily?.flatMap {
              it.weather.map { DailyWeatherEntity(id = it.id, weather = it.main, temperature = it.icon) }
          }
            binding.weeklyForecastRecyclerview.adapter = weatherAdapter.apply {
                submitList(wweather)
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
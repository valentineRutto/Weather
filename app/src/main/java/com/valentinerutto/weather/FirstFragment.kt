package com.valentinerutto.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.valentinerutto.weather.databinding.FragmentFirstBinding
import com.valentinerutto.weather.ui.WeatherViewmodel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel: WeatherViewmodel by sharedViewModel()

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

        binding.favouriteIcon.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        viewModel.errorResponse.observe(viewLifecycleOwner) { errorMsg ->
            binding.temperatureTextview.text = errorMsg
        }

        viewModel.successfulResponse.observe(viewLifecycleOwner) {
            binding.weatherDescriptionTextview.text = it?.current?.weather.toString()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
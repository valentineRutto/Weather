package com.valentinerutto.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.valentinerutto.weather.databinding.FragmentSecondBinding
import com.valentinerutto.weather.ui.FavoriteAdapter
import com.valentinerutto.weather.ui.WeatherViewmodel
import com.valentinerutto.weather.utils.DefaultLocation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val weatherViewModel by sharedViewModel<WeatherViewmodel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherViewModel._isLoading.value = true

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        binding.favRecyclerview.layoutManager = layoutManager
        favoriteAdapter = FavoriteAdapter()
        binding.favRecyclerview.addItemDecoration(
            DividerItemDecoration(
                requireActivity(), layoutManager.orientation
            )
        )
        weatherViewModel.location.observe(viewLifecycleOwner) {
            weatherViewModel._isLoading.value = false

            setupViews(it)
        }
        weatherViewModel.isLoading.observe(viewLifecycleOwner) { showLoading ->
            binding.favProgressBar.isVisible = showLoading
        }

    }

  private  fun setupViews(defaultLocation: DefaultLocation) {

        if (defaultLocation.latitude.isNullOrEmpty()) {
            binding.errorText.isVisible = true

        } else {
            binding.errorText.isVisible = false

            binding.favRecyclerview.adapter = favoriteAdapter.apply {
                submitList(listOf(defaultLocation))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
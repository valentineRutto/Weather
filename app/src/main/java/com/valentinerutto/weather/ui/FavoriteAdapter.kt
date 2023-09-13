package com.valentinerutto.weather.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.valentinerutto.weather.databinding.RowFavouriteBinding
import com.valentinerutto.weather.utils.DefaultLocation


class FavoriteAdapter :
    ListAdapter<DefaultLocation, FavoriteAdapter.LocationViewHolder>(
        diff
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.bind(location)
    }

    class LocationViewHolder(private val binding: RowFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(location: DefaultLocation) {
            binding.longTxt.text = "Longitude: " + location.longitude
            binding.latTxt.text = "Latitude: " + location.latitude
        }
    }

    companion object {

        fun from(parent: ViewGroup): LocationViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RowFavouriteBinding.inflate(
                layoutInflater,
                parent, false
            )
            return LocationViewHolder(binding)
        }

        val diff = object : DiffUtil.ItemCallback<DefaultLocation>() {
            override fun areItemsTheSame(
                oldItem: DefaultLocation,
                newItem: DefaultLocation
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: DefaultLocation,
                newItem: DefaultLocation
            ): Boolean = oldItem.latitude == newItem.latitude
        }

    }

}
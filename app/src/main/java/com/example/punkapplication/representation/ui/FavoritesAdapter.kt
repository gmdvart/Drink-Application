package com.example.punkapplication.representation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.punkapplication.R
import com.example.punkapplication.databinding.ListItemBinding
import com.example.punkapplication.domain.model.DrinkModel

class FavoritesAdapter(
    private val onFavoriteClick: (DrinkModel) -> Unit = {}
) : ListAdapter<DrinkModel, FavoritesAdapter.FavoriteDrinkViewHolder>(AdapterComponents.drinkModelDiffCallback) {
    inner class FavoriteDrinkViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(drinkModel: DrinkModel) {
            with(binding) {
                favoriteImageView.setOnClickListener { onFavoriteClick(drinkModel) }
                imageView.load(drinkModel.imageUrl) { crossfade(true) }
                textView.text = drinkModel.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteDrinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val binding = ListItemBinding.bind(view)
        return FavoriteDrinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteDrinkViewHolder, position: Int) {
        val drinkModel = getItem(position)
        drinkModel?.let { holder.bind(drinkModel) }
    }
}
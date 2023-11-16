package com.example.punkapplication.representation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.punkapplication.R
import com.example.punkapplication.databinding.ListItemBinding
import com.example.punkapplication.domain.model.DrinkModel

class DrinkListPagingDataAdapter(
    private val onDrinkClick: (Int) -> Unit = {}
) : PagingDataAdapter<DrinkModel, DrinkListPagingDataAdapter.DrinkViewHolder>(differCallback) {
    inner class DrinkViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(drinkModel: DrinkModel) {
            itemView.setOnClickListener { onDrinkClick(drinkModel.id) }
            with(binding) {
                favoriteImageView.isVisible = false
                imageView.load(drinkModel.imageUrl) { crossfade(true) }
                textView.text = drinkModel.name
            }
        }
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drinkModel = getItem(position)
        drinkModel?.let { holder.bind(drinkModel) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val binding = ListItemBinding.bind(view)
        return DrinkViewHolder(binding)
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<DrinkModel>() {
            override fun areItemsTheSame(oldItem: DrinkModel, newItem: DrinkModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DrinkModel, newItem: DrinkModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
package com.example.punkapplication.representation.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.punkapplication.domain.model.DrinkModel

object AdapterComponents {
    val drinkModelDiffCallback = object : DiffUtil.ItemCallback<DrinkModel>() {
        override fun areItemsTheSame(oldItem: DrinkModel, newItem: DrinkModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DrinkModel, newItem: DrinkModel): Boolean {
            return oldItem == newItem
        }
    }
}
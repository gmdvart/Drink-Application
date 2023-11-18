package com.example.punkapplication.representation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.punkapplication.R
import com.example.punkapplication.databinding.HistoryListItemBinding
import com.example.punkapplication.domain.model.DrinkModel

class HistoryAdapter: ListAdapter<DrinkModel, HistoryAdapter.HistoryItemViewHolder>(AdapterComponents.drinkModelDiffCallback) {
    inner class HistoryItemViewHolder(private val binding: HistoryListItemBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var _drinkModel: DrinkModel
        val drinkModel = _drinkModel

        fun bind(drinkModel: DrinkModel) {
            _drinkModel = drinkModel
            binding.drinkNameTextView.text = drinkModel.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_list_item, parent, false)
        val binding = HistoryListItemBinding.bind(view)
        return HistoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        val drinkModel = getItem(position)
        drinkModel?.let { holder.bind(it) }
    }
}
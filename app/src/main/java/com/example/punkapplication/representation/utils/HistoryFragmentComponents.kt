package com.example.punkapplication.representation.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.punkapplication.databinding.FragmentHistoryBinding
import com.example.punkapplication.domain.model.DrinkModel
import com.example.punkapplication.representation.ui.HistoryAdapter

fun FragmentHistoryBinding.setUpRecyclerViewWithSwipeToRemove(
    adapter: HistoryAdapter,
    swipeToRemoveCallback: (DrinkModel) -> Unit = {}
) {
    recyclerView.adapter = adapter

    val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return 0
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val drinkModel = (viewHolder as HistoryAdapter.HistoryItemViewHolder).drinkModel
            swipeToRemoveCallback(drinkModel)
        }
    })

    itemTouchHelper.attachToRecyclerView(recyclerView)
}
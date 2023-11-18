package com.example.punkapplication.representation.utils

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.punkapplication.databinding.FragmentHistoryBinding
import com.example.punkapplication.domain.model.DrinkModel
import com.example.punkapplication.representation.ui.HistoryAdapter

fun FragmentHistoryBinding.setUpRecyclerViewWithSwipeToRemove(
    context: Context,
    adapter: HistoryAdapter,
    swipeToRemoveCallback: (DrinkModel) -> Unit = {}
) {
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(context)

    val itemTouchHelper = ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {

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
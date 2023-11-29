package com.example.punkapplication.representation.utils

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
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
    recyclerView.setPadding(16, 0, 16, 16)
    recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration(){
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.set(16, 16, 16, 16)
        }
    })

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
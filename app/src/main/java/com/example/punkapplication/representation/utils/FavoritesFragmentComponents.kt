package com.example.punkapplication.representation.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.punkapplication.databinding.FragmentFavoritesBinding
import com.example.punkapplication.representation.ui.FavoritesAdapter

fun FragmentFavoritesBinding.setUpRecyclerView(context: Context, adapter: FavoritesAdapter) {
    recyclerView.adapter = adapter
    recyclerView.layoutManager = GridLayoutManager(context, 2)
    recyclerView.setPadding(16, 0, 16, 16)
    recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration(){
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.set(16, 16, 16, 16)
        }
    })
}
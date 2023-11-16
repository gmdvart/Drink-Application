package com.example.punkapplication.representation.utils

import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.punkapplication.databinding.FavoriteFabLayoutBinding
import com.example.punkapplication.representation.details.DetailsFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun DetailsFragment.getAndInitFavoriteFab(): FloatingActionButton {
    val dialog = dialog as BottomSheetDialog
    val containerLayout = dialog.findViewById(com.google.android.material.R.id.container) as? FrameLayout

    val favoriteFabLayoutBinding = FavoriteFabLayoutBinding.inflate(LayoutInflater.from(requireContext()))

    val layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.WRAP_CONTENT,
        Gravity.BOTTOM
    )

    dialog.setOnShowListener {
        favoriteFabLayoutBinding.favoriteFab.hide()

        containerLayout?.addView(
            favoriteFabLayoutBinding.root,
            layoutParams
        )
    }

    return favoriteFabLayoutBinding.favoriteFab
}
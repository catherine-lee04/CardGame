package com.appknot.cardgame.ui.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author Catherine on 2021-07-14
 */
@BindingAdapter("isSelected")
fun isSelected(view: View, isSelected: Boolean?) {
    isSelected?.let {
        view.isSelected = it
    }
}

@BindingAdapter(
    "setCardAdapter_items",
    "setCardAdapter_tryAction",
    "setCardAdapter_matchedAction")
fun setCardAdapter(
    view: RecyclerView,
    items: ArrayList<Int>?,
    tryAction: (() -> Unit)?,
    matchedAction: (() -> Unit)?) {
    if (items != null
        && tryAction != null
        && matchedAction != null) {
        view.apply {
            adapter = CardAdapter(items, tryAction, matchedAction)
            itemAnimator = null
        }
    }
}
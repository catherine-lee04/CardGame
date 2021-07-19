package com.appknot.bentleymembership.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author Catherine on 2021-06-04
 */
abstract class BaseRecyclerViewAdapter<VB: ViewDataBinding>
    : RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder<VB>>() {
    abstract val layoutRes: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB> =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(layoutRes, parent, false))

    class ViewHolder<T: ViewDataBinding>(view: View) : RecyclerView.ViewHolder(view) {
        val bind = DataBindingUtil.bind<ViewDataBinding>(view) as T
    }
}
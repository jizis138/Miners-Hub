package ru.vsibi.btc_mathematic.uikit.animation

import androidx.recyclerview.widget.RecyclerView

interface CommonItemAnimator {
    fun animateRemove(holder: RecyclerView.ViewHolder)
    fun animateAdd(holder: RecyclerView.ViewHolder)

    fun preAnimateRemove(holder: RecyclerView.ViewHolder) {}
    fun preAnimateAdd(holder: RecyclerView.ViewHolder){}
}


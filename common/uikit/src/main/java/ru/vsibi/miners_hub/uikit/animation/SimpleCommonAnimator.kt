package ru.vsibi.miners_hub.uikit.animation

import androidx.recyclerview.widget.RecyclerView
import ru.vsibi.miners_hub.uikit.animation.CommonItemAnimator

class SimpleCommonAnimator : CommonItemAnimator {

    override fun animateRemove(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().alpha(0f)
    }

    override fun preAnimateRemove(holder: RecyclerView.ViewHolder) {
        holder.itemView.alpha = 1f
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().alpha(1f)
    }

    override fun preAnimateAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.alpha = 0f
    }

}
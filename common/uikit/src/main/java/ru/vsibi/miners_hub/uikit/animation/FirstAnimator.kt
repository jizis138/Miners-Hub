package ru.vsibi.miners_hub.uikit.animation

import androidx.recyclerview.widget.RecyclerView

class FirstAnimator : CommonItemAnimator {

    override fun animateRemove(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().apply {
            translationX(-holder.itemView.width.toFloat())
            alpha(0f)
        }.start()
    }

    override fun preAnimateAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.scaleX = 1.075f
        holder.itemView.scaleY = 1.075f
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate()
            .scaleX(1f)
            .scaleY(1f)
            .start()
    }
}

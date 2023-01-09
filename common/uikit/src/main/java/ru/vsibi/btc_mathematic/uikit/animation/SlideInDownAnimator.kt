package ru.vsibi.btc_mathematic.uikit.animation

import androidx.recyclerview.widget.RecyclerView

class SlideInDownAnimator(private val onAnimationFinished: () -> Unit) : CommonItemAnimator {

    override fun animateRemove(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().apply {
            translationX(-holder.itemView.width.toFloat())
            alpha(0f)
        }.withEndAction {
            onAnimationFinished()
        }.start()
    }

    override fun preAnimateAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.translationY = -holder.itemView.height.toFloat()
        holder.itemView.alpha = 0f
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate()
            .translationY(0f)
            .alpha(1f)
            .withEndAction {
                onAnimationFinished()
            }
            .start()
    }
}

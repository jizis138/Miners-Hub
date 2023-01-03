package ru.vsibi.miners_hub.util

import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow

object PopupMenu {
    fun create(view: View): PopupWindow {
        return PopupWindow(
            view,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )
    }
}
/**
 * Created by Dmitry Popov on 15.08.2022.
 */
package ru.vsibi.momento.uikit

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.annotation.StyleableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import ru.vsibi.momento.uikit.databinding.ToolbarCommonBinding
import ru.vsibi.momento.util.android.drawable
import ru.vsibi.momento.util.onClick

class MomentoToolbar : ConstraintLayout {

    private val binding: ToolbarCommonBinding

    var title: CharSequence?
        get() = binding.tb.title
        set(value) {
            binding.tb.title = value
        }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        binding = ToolbarCommonBinding.inflate(LayoutInflater.from(context), this, true)

        setupViews(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        binding = ToolbarCommonBinding.inflate(LayoutInflater.from(context), this, true)

        setupViews(attrs)
    }

    fun setOnButtonClickListener(onClick: () -> Unit) {
        binding.tb.navigationIcon = context.drawable(R.drawable.ic_cancel)
        binding.tb.setNavigationOnClickListener {
            onClick()
        }
    }
//
//    fun setButtonVisibility(visible: Boolean) {
//        binding.ibCancel.isInvisible = !visible
//    }

    private fun setupViews(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MomentoToolbar)

        title = a.getText(R.styleable.MomentoToolbar_title)

        a.recycle()
    }
}
package ru.vsibi.btc_mathematic.util.android

import android.os.Bundle

fun bundleOf(block: Bundle.() -> Unit): Bundle = Bundle().apply(block)
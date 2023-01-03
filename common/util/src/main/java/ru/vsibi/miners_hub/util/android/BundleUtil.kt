package ru.vsibi.miners_hub.util.android

import android.os.Bundle

fun bundleOf(block: Bundle.() -> Unit): Bundle = Bundle().apply(block)
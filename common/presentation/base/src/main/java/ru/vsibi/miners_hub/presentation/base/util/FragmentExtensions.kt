package ru.vsibi.miners_hub.presentation.base.util

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import ru.vsibi.miners_hub.mvi.Message
import ru.vsibi.miners_hub.navigation.BackPressConsumer
import ru.vsibi.miners_hub.navigation.model.requestParams
import ru.vsibi.miners_hub.presentation.base.ui.BaseFragment
import ru.vsibi.miners_hub.presentation.base.ui.MessageDialogFragment
import ru.vsibi.miners_hub.util.doOnDestroy
import ru.vsibi.miners_hub.util.getPrintableText
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("unused")
fun BaseFragment<*, *>.nothingToUpdate() = Unit

@Suppress("unused")
fun BaseFragment<*, *>.noEventsExpected(): Nothing =
    error("No events expected! Add implementation if needed.")

typealias ViewBindingInflate<B> = (LayoutInflater, ViewGroup?, Boolean) -> B

private val mainHandler = Handler(Looper.getMainLooper())

fun <B : ViewBinding> Fragment.fragmentViewBinding(
    bindingCreator: (View) -> B,
) = object : ReadOnlyProperty<Fragment, B> {
    private var binding: B? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): B {
        //view может быть null, если обращение к binding в onDestroyView
        this.binding
            ?.takeIf { view == null || it.root == view }
            ?.let { return it }

        val newBinding = bindingCreator(requireView())
        binding = newBinding
        viewLifecycleOwner.lifecycle.doOnDestroy {
            mainHandler.post {
                if (binding === newBinding) {
                    binding = null
                }
            }
        }

        return newBinding
    }
}

fun <B : ViewBinding> Fragment.nestedViewBinding(
    nestedContentRoot: () -> ViewGroup,
    inflate: ViewBindingInflate<B>,
) = object : ReadOnlyProperty<Fragment, B> {
    private var binding: B? = null

    @SuppressLint("StaticFieldLeak")
    private var nestedRoot: ViewGroup? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): B {
        //view может быть null, если обращение к binding в onDestroyView
        this.binding
            ?.takeIf { view == null || nestedRoot == nestedContentRoot() }
            ?.let { return it }

        val nestedRoot = nestedContentRoot()
        val newBinding = inflate(layoutInflater, nestedRoot, true)
        this.nestedRoot = nestedRoot
        binding = newBinding
        viewLifecycleOwner.lifecycle.doOnDestroy {
            mainHandler.post {
                if (binding === newBinding) {
                    binding = null
                    this.nestedRoot = null
                }
            }
        }

        return newBinding
    }
}

internal fun FragmentManager.onBackPressed(): Boolean =
    fragments.any { it is BackPressConsumer && it.onBackPressed() }

fun Fragment.showMessage(message: Message): Unit = when (message) {
    is Message.Alert -> {
        val tag = message.id
        (childFragmentManager.findFragmentByTag(tag) as MessageDialogFragment?)
            ?.dismissAllowingStateLoss()
        MessageDialogFragment
            .create(requestParams.requestKey, message)
            .show(childFragmentManager, tag)
    }

    is Message.PopUp -> Toast
        .makeText(requireContext(), getPrintableText(message.messageText), Toast.LENGTH_LONG)
        .show()
    is Message.Dialog -> Unit
//        ErrorDialog
//            .create(this, message.titleText, message.messageText)
//            .show()

}


fun Fragment.getOrCreateArguments(): Bundle {
    if (arguments == null) arguments = Bundle()
    return requireArguments()
}

fun booleanArg(key: String, defaultValue: Boolean) = object : ReadWriteProperty<Fragment, Boolean> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Boolean =
        thisRef.getOrCreateArguments().getBoolean(key, defaultValue)

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Boolean) {
        thisRef.getOrCreateArguments().putBoolean(key, value)
    }
}
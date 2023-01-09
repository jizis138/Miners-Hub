package ru.vsibi.btc_mathematic.navigation.model

import android.os.Bundle
import android.os.Parcelable
import ru.vsibi.btc_mathematic.navigation.contract.GetResultInterface

interface ResultProvider {
    fun <R : Parcelable> getResult(getResult: GetResultInterface<R>): R?

    fun <R : Parcelable> consumeResult(
        getResultInterface: GetResultInterface<R>,
        consume: (R) -> Unit,
    ) {
        this.getResult(getResultInterface)
            ?.also(consume)
    }
}

class BundleResultProvider(private val bundle: Bundle) : ResultProvider {
    override fun <R : Parcelable> getResult(getResult: GetResultInterface<R>): R? =
        getResult.parseResult(bundle)
}

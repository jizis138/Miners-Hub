/**
 * Created by Dmitry Popov on 10.01.2023.
 */
package ru.vsibi.btc_mathematic

sealed interface MainActivityEvent {

    class OnLocaleChanged(
        val locale : String
    ) : MainActivityEvent

}
package ru.vsibi.btc_mathematic.mining_impl.presentation.main

import ru.vsibi.btc_mathematic.mining_impl.presentation.main.model.FarmViewItem

sealed interface MiningEvent {

    class ShowMenuDialog(val farmViewItem: FarmViewItem) : MiningEvent

    class ShowAcceptDialog(val farmViewItem: FarmViewItem) : MiningEvent

}
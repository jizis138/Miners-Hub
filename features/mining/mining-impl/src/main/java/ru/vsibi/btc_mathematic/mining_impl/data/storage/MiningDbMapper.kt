package ru.vsibi.btc_mathematic.mining_impl.data.storage

import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.knowledge_api.model.Price
import ru.vsibi.btc_mathematic.knowledge_api.model.Schema
import ru.vsibi.btc_mathematic.mining_impl.data.storage.model.FarmTable
import ru.vsibi.btc_mathematic.mining_impl.data.storage.model.MinerDB
import ru.vsibi.btc_mathematic.mining_impl.data.storage.model.PriceDB
import ru.vsibi.btc_mathematic.knowledge_api.model.Farm

object MiningDbMapper {

    fun mapFarms(farms: List<FarmTable>) = farms.map { farmTable ->
        Farm(
            id = farmTable.id,
            title = farmTable.title,
            miners = farmTable.miners.map {
                Miner(
                    id = it.id,
                    name = it.name,
                    schemas = listOf(
                        Schema(
                            algorithmName = it.algorithmName ?: "",
                            hashrate = it.hashrate,
                            power = it.power,
                        )
                    ),
                    count = it.count,
                    tag = it.tag
                )
            },
            electricityPrice = Price(
                value = farmTable.electricityPrice.value,
                currency = farmTable.electricityPrice.currency
            )
        )
    }

    fun mapTableFarm(farm: Farm) =
        FarmTable(
            id = farm.id,
            title = farm.title,
            miners = farm.miners.map {
                MinerDB(
                    id = it.id,
                    name = it.name,
                    algorithmName = it.schemas.last().algorithmName,
                    power = it.schemas.last().power,
                    hashrate = it.schemas.last().hashrate,
                    count = it.count,
                    tag = it.tag
                )
            },
            electricityPrice = PriceDB(
                value = farm.electricityPrice.value,
                currency = farm.electricityPrice.currency
            )
        )
}
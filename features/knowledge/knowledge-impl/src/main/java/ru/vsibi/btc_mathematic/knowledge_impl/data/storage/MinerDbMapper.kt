package ru.vsibi.btc_mathematic.knowledge_impl.data.storage

import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.MinerTable
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.SchemaTable
import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.knowledge_api.model.Schema


object MinerDbMapper {

    fun mapMiners(miners: List<MinerTable>) = miners.map { minerTable ->
        Miner(
            id = minerTable.id,
            name = minerTable.name,
            schemas = minerTable.schemas.map {
                Schema(
                    algorithmName = it.algorithmName,
                    hashrate = it.hashrate,
                    power = it.power,
                )
            },
            count = minerTable.count,
            tag = minerTable.tag
        )
    }

    fun mapTableMiners(miners: List<Miner>) = miners.map { miner ->
        MinerTable(
            id = miner.id,
            name = miner.name,
            schemas = miner.schemas.map {
                SchemaTable(
                    algorithmName = it.algorithmName,
                    hashrate = it.hashrate,
                    power = it.power,
                )
            },
            count = miner.count,
            tag = miner.tag
        )
    }
}
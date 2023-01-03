package ru.vsibi.miners_hub.knowledge_impl.data.storage

import ru.vsibi.miners_hub.knowledge_impl.data.storage.model.MinerTable
import ru.vsibi.miners_hub.knowledge_impl.data.storage.model.SchemaTable
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Miner
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Schema


object MinerDbMapper {

    fun mapMiners(miners: List<MinerTable>) = miners.map { minerTable ->
        Miner(
            id = minerTable.id,
            name = minerTable.name,
            schemas = minerTable.schemas.map {
                Schema(
                    algorithmName = it.algorithmName,
                    hashrate = it.hashrate,
                    power = it.power
                )
            }
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
                    power = it.power
                )
            }
        )
    }
}
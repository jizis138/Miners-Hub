package ru.vsibi.btc_mathematic.knowledge_impl.data.storage

import ru.vsibi.btc_mathematic.knowledge_api.model.CalculationState
import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.knowledge_api.model.Schema
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.HistoryTable
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.MinerTable
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.SchemaTable
import java.time.LocalDate


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

    fun mapTableToCalcResult(historyTable: List<HistoryTable>) = historyTable.map {
        CalculationState.ReadyResult(
            id = it.id,
            hashrate = it.hashrate,
            power = it.power,
            electricityPrice = it.electricityPrice,
            miners = it.miners,
            perDay = it.perDay,
            perMonth = it.perMonth,
            exchangeRate = it.exchangeRate,
            difficulty = it.difficulty,
            blockIncome = it.blockIncome,
            powerPerMonth = it.powerPerMonth,
            btcIncomePerDay = it.btcIncomePerDay,
            btcIncomePerMonth = it.btcIncomePerMonth,
            incomePerMonth = it.incomePerMonth,
            pricePowerPerMonth = it.pricePowerPerMonth,
            fromDate = it.date,
            usingViaBtc = it.usingViaBtc
        )
    }

    fun mapResultToHistoryTable(calculationResult: CalculationState.ReadyResult) =
        HistoryTable(
            id = 0,
            date = calculationResult.fromDate,
            hashrate = calculationResult.hashrate,
            power = calculationResult.power,
            electricityPrice = calculationResult.electricityPrice,
            miners = calculationResult.miners,
            perDay = calculationResult.perDay,
            perMonth = calculationResult.perMonth,
            exchangeRate = calculationResult.exchangeRate,
            difficulty = calculationResult.difficulty,
            blockIncome = calculationResult.blockIncome,
            powerPerMonth = calculationResult.powerPerMonth,
            btcIncomePerDay = calculationResult.btcIncomePerDay,
            btcIncomePerMonth = calculationResult.btcIncomePerMonth,
            incomePerMonth = calculationResult.incomePerMonth,
            pricePowerPerMonth = calculationResult.pricePowerPerMonth,
            usingViaBtc = calculationResult.usingViaBtc
        )
}
package ru.vsibi.btc_mathematic.knowledge_impl.data.storage

import ru.vsibi.btc_mathematic.data.db.DatabaseFactory
import ru.vsibi.btc_mathematic.knowledge_api.model.CalculationState

class HistoryStorage(
    databaseFactory: DatabaseFactory,
) {

    private val db by databaseFactory.create(MinerDatabase::class, "MinerDatabase")

    suspend fun getHistoryItems(): List<CalculationState.ReadyResult> = db.historyDao.getHistoryItems()?.let {
        MinerDbMapper.mapTableToCalcResult(it)
    } ?: listOf()

    suspend fun saveHistory(calculationResult: CalculationState.ReadyResult) {
        db.historyDao.saveHistory(MinerDbMapper.mapResultToHistoryTable(calculationResult))
    }

    suspend fun removeItem(id: Long) {
        db.historyDao.removeHistory(id)
    }

    suspend fun removeAllItems() {
        db.historyDao.removeHistoryTable()
    }
}
package ru.vsibi.btc_mathematic.knowledge_impl.domain.repo

import ru.vsibi.btc_mathematic.knowledge_api.model.Difficulty
import ru.vsibi.btc_mathematic.util.CallResult

interface DifficultyRepository {

    suspend fun fetchDifficulties() : CallResult<List<Difficulty>>

}
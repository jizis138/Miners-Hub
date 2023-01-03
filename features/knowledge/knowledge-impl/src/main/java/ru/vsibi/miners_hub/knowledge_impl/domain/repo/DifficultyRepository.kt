package ru.vsibi.miners_hub.knowledge_impl.domain.repo

import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Difficulty
import ru.vsibi.miners_hub.util.CallResult

interface DifficultyRepository {

    suspend fun fetchDifficulties() : CallResult<List<Difficulty>>

}
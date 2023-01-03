package ru.vsibi.miners_hub.knowledge_impl.data.repo_impl

import ru.vsibi.miners_hub.knowledge_impl.data.service.DifficultyService
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Difficulty
import ru.vsibi.miners_hub.knowledge_impl.domain.repo.DifficultyRepository
import ru.vsibi.miners_hub.util.CallResult
import ru.vsibi.miners_hub.util.callForResult

class DifficultyRepoImpl(
    private val difficultyService: DifficultyService
) : DifficultyRepository {

    override suspend fun fetchDifficulties(): CallResult<List<Difficulty>> = callForResult {
        difficultyService.getCoinsDifficulty()
    }
}
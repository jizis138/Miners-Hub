package ru.vsibi.btc_mathematic.knowledge_impl.data.repo_impl

import ru.vsibi.btc_mathematic.knowledge_impl.data.service.DifficultyService
import ru.vsibi.btc_mathematic.knowledge_api.model.Difficulty
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.DifficultyRepository
import ru.vsibi.btc_mathematic.util.CallResult
import ru.vsibi.btc_mathematic.util.callForResult

class DifficultyRepoImpl(
    private val difficultyService: DifficultyService
) : DifficultyRepository {

    override suspend fun fetchDifficulties(): CallResult<List<Difficulty>> = callForResult {
        difficultyService.getCoinsDifficulty()
    }
}
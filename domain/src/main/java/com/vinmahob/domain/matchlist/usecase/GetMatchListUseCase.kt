package com.vinmahob.domain.matchlist.usecase

import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
import com.vinmahob.domain.architecture.model.UseCaseResult
import com.vinmahob.domain.architecture.usecase.BackgroundExecutingUseCase
import com.vinmahob.domain.matchlist.model.MatchListDomainModel
import com.vinmahob.domain.matchlist.repository.MatchListRepository

class GetMatchListUseCase(
    private val matchListRepository: MatchListRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<Unit, MatchListDomainModel>(coroutineContextProvider) {
    override suspend fun executeInBackground(request: Unit): UseCaseResult<MatchListDomainModel> {
        return matchListRepository.getMatchList()
    }
}
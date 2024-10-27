package com.vinmahob.domain.matchlist.repository

import com.vinmahob.domain.architecture.model.UseCaseResult
import com.vinmahob.domain.matchlist.model.MatchListDomainModel


interface MatchListRepository {
    suspend fun getMatchList(): UseCaseResult<MatchListDomainModel>
}
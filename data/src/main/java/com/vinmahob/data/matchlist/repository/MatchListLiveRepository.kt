package com.vinmahob.data.matchlist.repository

import com.vinmahob.data.matchlist.datasource.MatchListDataSource
import com.vinmahob.data.matchlist.mapper.MatchListDataToDomainMapper
import com.vinmahob.domain.architecture.model.UseCaseResult
import com.vinmahob.domain.matchlist.model.MatchListDomainModel
import com.vinmahob.domain.matchlist.repository.MatchListRepository

class MatchListLiveRepository(
    private val matchListDataSource: MatchListDataSource,
    private val matchListDataToDomainMapper: MatchListDataToDomainMapper
) : MatchListRepository {
    override suspend fun getMatchList(): UseCaseResult<MatchListDomainModel> {
        return try {
            UseCaseResult.OnSuccess(matchListDataToDomainMapper.toDomain(matchListDataSource.getMatchList()))
        } catch (throwable: Throwable) {
            UseCaseResult.OnError(throwable)
        }
    }
}
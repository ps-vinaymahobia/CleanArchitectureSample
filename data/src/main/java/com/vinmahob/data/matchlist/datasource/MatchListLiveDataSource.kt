package com.vinmahob.data.matchlist.datasource

import com.vinmahob.data.matchlist.model.MatchListDataModel
import com.vinmahob.data.matchlist.network.MatchListApiService


class MatchListLiveDataSource(
    private val matchListApiService: MatchListApiService,
) : MatchListDataSource {
    override suspend fun getMatchList(): MatchListDataModel {
        return matchListApiService.getMatchList()
    }
}
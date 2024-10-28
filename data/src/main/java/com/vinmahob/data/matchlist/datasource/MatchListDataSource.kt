package com.vinmahob.data.matchlist.datasource

import com.vinmahob.data.matchlist.model.MatchListDataModel


interface MatchListDataSource {
    suspend fun getMatchList() : MatchListDataModel
}
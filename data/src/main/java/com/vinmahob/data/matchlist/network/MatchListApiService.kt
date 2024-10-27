package com.vinmahob.data.matchlist.network

import com.vinmahob.data.matchlist.model.MatchListDataModel
import retrofit2.http.GET

interface MatchListApiService {
    @GET("/v1/currentMatches?apikey=7f24c045-5095-4865-8183-363561e64858&offset=0")
    suspend fun getMatchList(): MatchListDataModel

}
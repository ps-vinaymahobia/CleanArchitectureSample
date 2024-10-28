package com.vinmahob.cleanarchitecturesample.di

import com.vinmahob.data.matchlist.datasource.MatchListDataSource
import com.vinmahob.data.matchlist.datasource.MatchListLiveDataSource
import com.vinmahob.data.matchlist.mapper.MatchListDataToDomainMapper
import com.vinmahob.data.matchlist.mapper.MatchListItemDataToDomainMapper
import com.vinmahob.data.matchlist.network.MatchListApiService
import com.vinmahob.data.matchlist.repository.MatchListLiveRepository
import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
import com.vinmahob.domain.matchlist.repository.MatchListRepository
import com.vinmahob.domain.matchlist.usecase.GetMatchListUseCase
import com.vinmahob.presentation.matchlist.mapper.MatchListDomainToPresentationMapper
import com.vinmahob.presentation.matchlist.mapper.MatchListItemDomainToPresentationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object MatchListModule {
    @Provides
    fun provideGetMatchListUseCase(
        matchListRepository: MatchListRepository,
        contextProvider: CoroutineContextProvider
    ) = GetMatchListUseCase(matchListRepository, contextProvider)

    @Provides
    fun provideMatchListRepository(
        matchDataSource: MatchListDataSource,
        matchDataToDomainMapper: MatchListDataToDomainMapper
    ): MatchListRepository =
        MatchListLiveRepository(matchDataSource, matchDataToDomainMapper)

    @Provides
    fun provideMatchDataSource(
        apiService: MatchListApiService,
    ): MatchListDataSource =
        MatchListLiveDataSource(apiService)

    @Provides
    fun provideMatchDataToDomainMapper(
        matchListItemDataToDomainMapper: MatchListItemDataToDomainMapper
    ) = MatchListDataToDomainMapper(matchListItemDataToDomainMapper)

    @Provides
    fun provideMatchListItemDataToDomainMapper() = MatchListItemDataToDomainMapper()

    @Provides
    fun provideMatchListDomainToPresentationMapper(
        matchListItemDomainToPresentationMapper: MatchListItemDomainToPresentationMapper
    ) = MatchListDomainToPresentationMapper(matchListItemDomainToPresentationMapper)

    @Provides
    fun provideMatchListItemDomainToPresentationMapper() =
        MatchListItemDomainToPresentationMapper()

    @Provides
    fun provideMatchDetailsApiService(retrofit: Retrofit): MatchListApiService {
        return retrofit.create(MatchListApiService::class.java)
    }

}
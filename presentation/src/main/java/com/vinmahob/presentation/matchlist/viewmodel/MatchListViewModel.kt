package com.vinmahob.presentation.matchlist.viewmodel

import androidx.lifecycle.viewModelScope
import com.vinmahob.domain.matchlist.model.MatchListDomainModel
import com.vinmahob.domain.matchlist.usecase.GetMatchListUseCase
import com.vinmahob.presentation.architecture.viewmodel.base.BaseViewModel
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.matchlist.mapper.MatchListDomainToPresentationMapper
import com.vinmahob.presentation.matchlist.model.MatchListViewState
import com.vinmahob.presentation.matchlist.model.MatchListSideEffect
import com.vinmahob.presentation.matchlist.model.MatchListViewIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val getMatchListUseCase: GetMatchListUseCase,
    private val matchListDomainToPresentationMapper: MatchListDomainToPresentationMapper,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<MatchListViewState, MatchListViewIntent, MatchListSideEffect>(
    useCaseExecutorProvider = useCaseExecutorProvider
) {
    init {
        handleViewIntent()
    }

    override fun initialState() = MatchListViewState.Idle

    private fun fetchProductList() {
        updateViewState(MatchListViewState.Loading)
        useCaseExecutor.execute(
            getMatchListUseCase, Unit, ::currentProductList, ::onError
        )
    }

    private fun currentProductList(matchList: MatchListDomainModel) {
        val matchDetails = matchListDomainToPresentationMapper.toPresentation(matchList)
        updateViewState(MatchListViewState.MatchListLoaded(matchList = matchDetails))
    }

    private fun onError(throwable: Throwable) {
        updateViewState(MatchListViewState.Error(error = throwable.message))
    }

    private fun onMatchItemClick(matchId: String) {
        emitSideEffect(MatchListSideEffect.NavigateToMatchDetails(matchId))
    }

    override fun handleViewIntent() {
        viewModelScope.launch {
            viewIntent.consumeAsFlow().collect {
                when (it) {
                    MatchListViewIntent.LoadMatchList -> fetchProductList()
                    is MatchListViewIntent.OnMatchItemClicked -> onMatchItemClick(it.matchId)
                }
            }
        }
    }
}
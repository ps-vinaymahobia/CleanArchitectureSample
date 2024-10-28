package com.vinmahob.presentation.matchlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinmahob.domain.architecture.usecase.UseCaseExecutor
import com.vinmahob.domain.matchlist.model.MatchListDomainModel
import com.vinmahob.domain.matchlist.usecase.GetMatchListUseCase
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.matchlist.mapper.MatchListDomainToPresentationMapper
import com.vinmahob.presentation.matchlist.model.MatchListSideEffect
import com.vinmahob.presentation.matchlist.model.MatchListViewIntent
import com.vinmahob.presentation.matchlist.model.MatchListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val getMatchListUseCase: GetMatchListUseCase,
    private val matchListDomainToPresentationMapper: MatchListDomainToPresentationMapper,
    private val useCaseExecutorProvider: UseCaseExecutorProvider
) : ViewModel() {

    private val _viewState = MutableStateFlow<MatchListViewState>(value = MatchListViewState.Idle)
    val viewState = _viewState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MatchListSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    val viewIntent = Channel<MatchListViewIntent>(Channel.UNLIMITED)

    private val useCaseExecutor by lazy {
        useCaseExecutorProvider(viewModelScope)
    }
    private val currentViewState: MatchListViewState
        get() = viewState.value

    init {
        handleViewIntent()
    }

    private fun updateViewState(newViewState: MatchListViewState) {
        _viewState.value = newViewState
    }

    private fun emitSideEffect(sideEffect: MatchListSideEffect) {
        viewModelScope.launch {
            _sideEffect.emit(sideEffect)
        }
    }

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

    private fun handleViewIntent() {
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
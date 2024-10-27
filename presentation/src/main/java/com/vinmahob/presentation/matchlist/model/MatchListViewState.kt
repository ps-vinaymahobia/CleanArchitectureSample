package com.vinmahob.presentation.matchlist.model

import com.vinmahob.presentation.architecture.viewmodel.base.ViewState

sealed interface MatchListViewState : ViewState {
    data object Loading : MatchListViewState
    data object Idle : MatchListViewState

    data class MatchListLoaded(
        val matchList: MatchListPresentationModel
    ) : MatchListViewState

    data class Error(val error: String?) : MatchListViewState
}
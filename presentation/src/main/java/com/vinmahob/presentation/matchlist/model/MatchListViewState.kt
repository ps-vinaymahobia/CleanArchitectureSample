package com.vinmahob.presentation.matchlist.model

sealed interface MatchListViewState {
    data object Loading : MatchListViewState
    data object Idle : MatchListViewState

    data class MatchListLoaded(
        val matchList: MatchListPresentationModel
    ) : MatchListViewState

    data class Error(val error: String?) : MatchListViewState
}
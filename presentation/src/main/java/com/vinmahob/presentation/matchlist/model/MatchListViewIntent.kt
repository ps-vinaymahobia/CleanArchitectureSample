package com.vinmahob.presentation.matchlist.model

sealed interface MatchListViewIntent {
    data object LoadMatchList : MatchListViewIntent
    data class OnMatchItemClicked(val matchId: String) : MatchListViewIntent
}
package com.vinmahob.presentation.matchlist.model

import com.vinmahob.presentation.architecture.viewmodel.base.ViewIntent

sealed interface MatchListViewIntent : ViewIntent {
    data object LoadMatchList : MatchListViewIntent
    data class OnMatchItemClicked(val matchId: String) : MatchListViewIntent
}
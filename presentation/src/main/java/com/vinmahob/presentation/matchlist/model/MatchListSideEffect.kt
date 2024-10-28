package com.vinmahob.presentation.matchlist.model

sealed interface MatchListSideEffect {
    data class NavigateToMatchDetails(val id: String) : MatchListSideEffect
}

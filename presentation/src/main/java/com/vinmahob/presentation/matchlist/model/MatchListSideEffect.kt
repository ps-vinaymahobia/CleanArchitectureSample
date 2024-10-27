package com.vinmahob.presentation.matchlist.model

import com.vinmahob.presentation.architecture.viewmodel.base.SideEffect

sealed interface MatchListSideEffect : SideEffect{
    data class NavigateToMatchDetails(val id: String) : MatchListSideEffect
}

package com.vinmahob.presentation.architecture.navigation

@JvmInline
value class NavigationScreen(val screenName: String)

val MATCH_SCREEN = NavigationScreen("matchList")
val DETAILS_SCREEN = NavigationScreen("details")
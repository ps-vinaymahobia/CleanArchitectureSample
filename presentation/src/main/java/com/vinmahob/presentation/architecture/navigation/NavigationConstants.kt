package com.vinmahob.presentation.architecture.navigation

@JvmInline
value class NavigationScreen(val screenName: String)

val MATCH_SCREEN = NavigationScreen("matchList")
val HOME_SCREEN = NavigationScreen("home")
val DETAILS_SCREEN = NavigationScreen("details")
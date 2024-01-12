package com.vinmahob.ui.architecture.navigation

@JvmInline
value class NavigationScreen(val screenName: String)

val HOME_SCREEN = NavigationScreen("home")
val DETAILS_SCREEN = NavigationScreen("details")
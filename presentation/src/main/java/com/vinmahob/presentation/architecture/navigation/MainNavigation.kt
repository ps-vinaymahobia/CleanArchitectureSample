package com.vinmahob.presentation.architecture.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vinmahob.presentation.matchlist.MatchListRoute

@Composable
fun MainNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    val activity = LocalContext.current as Activity
    NavHost(navController = navHostController, startDestination = MATCH_SCREEN.screenName) {
        composable(route = MATCH_SCREEN.screenName) {
            MatchListRoute(
                onGoToItem = { id ->
                    navHostController.navigate("${DETAILS_SCREEN.screenName}/$id")
                },
                onCloseIconPressed = { activity.finish() }
            )
        }
    }
}
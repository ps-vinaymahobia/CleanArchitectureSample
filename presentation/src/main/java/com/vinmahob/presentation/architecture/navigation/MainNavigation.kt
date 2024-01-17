package com.vinmahob.presentation.architecture.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vinmahob.presentation.productdetails.ProductDetailsRoute
import com.vinmahob.presentation.productdetails.viewmodel.KEY_ID
import com.vinmahob.presentation.productlist.ProductListRoute

@Composable
fun MainNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    val activity = LocalContext.current as Activity
    NavHost(navController = navHostController, startDestination = HOME_SCREEN.screenName) {
        composable(route = HOME_SCREEN.screenName) {
            ProductListRoute(
                onGoToItem = { id ->
                    navHostController.navigate("${DETAILS_SCREEN.screenName}/$id")
                },
                onCloseIconPressed = { activity.finish() }
            )
        }

        composable(
            route = "${DETAILS_SCREEN.screenName}/{id}",
            arguments = listOf(element = navArgument(KEY_ID) { type = NavType.IntType })
        ) {
            ProductDetailsRoute(
                onGoBack = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}
package com.vinmahob.ui.architecture.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vinmahob.presentation.productdetails.viewmodel.KEY_ID
import com.vinmahob.ui.productdetails.ProductDetailsRoute
import com.vinmahob.ui.productlist.ProductListRoute

@Composable
fun MainNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(navController = navHostController, startDestination = HOME_SCREEN.screenName) {
        composable(route = HOME_SCREEN.screenName) {
            ProductListRoute(
                onGoToItem = { id ->
                    navHostController.navigate("${DETAILS_SCREEN.screenName}/$id")
                }
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
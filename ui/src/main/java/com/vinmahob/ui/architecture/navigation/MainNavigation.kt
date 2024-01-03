package com.vinmahob.ui.architecture.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vinmahob.ui.productlist.ProductListRoute

@Composable
fun MainNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(navController = navHostController, startDestination = "home") {
        composable(route = "home") {
            ProductListRoute(
                onGoToItem = { id ->
                    navHostController.navigate("details/$id")
                }
            )
        }

        composable(
            route = "details/{id}",
            arguments = listOf(element = navArgument("id") { type = NavType.IntType })
        ) {
//            ProductDetailsRoute(
//                onGoBack = {
//                    navHostController.popBackStack()
//                }
//            )
        }
    }
}
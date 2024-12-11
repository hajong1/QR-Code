package hajong.qrcode.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hajong.qrcode.presentation.history.HistoryScreen
import hajong.qrcode.presentation.main.MainScreen
import hajong.qrcode.presentation.result.ResultScreen

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object History : Screen("history")
    data object Result : Screen("result/{url}") {
        fun createRoute(url: String) = "result/${url}"
    }
}

@Composable
fun QRScannerNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                onHistoryClick = {
                    navController.navigate(Screen.History.route)
                },
                onScanResult = { url ->
                    navController.navigate(Screen.Result.createRoute(url))
                }
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onItemClick = { url ->
                    navController.navigate(Screen.Result.createRoute(url))
                }
            )
        }

        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url")
            ResultScreen(
                url = url ?: "",
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
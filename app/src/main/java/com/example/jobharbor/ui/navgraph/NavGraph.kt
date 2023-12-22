package com.example.jobharbor.ui.navgraph


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.jobharbor.JobHarborApp
import com.example.jobharbor.ui.login.company.LoginCompanyScreen
import com.example.jobharbor.ui.screen.search.SearchScreen

@Composable
fun NavGraph(
    startDestination: String,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.AppStartNavaigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {}

        navigation(
            route = Route.JobsNavigation.route,
            startDestination = Route.JobsNavigatorScreen.route
        ) {
            composable(route = Route.JobsNavigatorScreen.route) {
                JobHarborApp()
            }
            composable(route = Route.SearchScreen.route) {
                SearchScreen(
                    navController = navController,
                    goBack = { navController.popBackStack() },
                    navigateToDetail = { jobId ->
                        navController.navigate(Route.DetailScreen.createRoute(jobId))
                    }
                )
            }
        }



        composable(route = Route.LoginCompanyScreen.route) {
            LoginCompanyScreen()
        }
    }

}
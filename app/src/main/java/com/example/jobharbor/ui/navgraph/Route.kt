package com.example.jobharbor.ui.navgraph

sealed class Route(
    val route: String,
) {
    // Beginning
    object OnBoardingScreen: Route(route = "onBoardingScreen")
    object FinalBoardingScreen: Route(route = "FinalBoardingScreen")
    object LoginJobScreen: Route(route = "loginJobScreen")
    object LoginCompanyScreen: Route(route = "loginCompanyScreen")

    //authentication
    object LoginScreen: Route(route = "loginScreen")
    object RegisterScreen: Route(route = "registerScreen")

    // MainContent
    object HomeScreen: Route(route = "homeScreen")
    object SearchScreen: Route(route = "searchScreen")
    object ProfileScreen: Route(route = "profileScreen")
    object DetailScreen: Route(route = "detailScreen/{jobId}") {
        fun createRoute(jobId: Long) = "detailScreen/${jobId}"
    }
    object BookmarkScreen: Route(route = "bookmarkScreen")


    // Core
    object AppStartNavaigation: Route(route = "appStartNavigation")
    object JobsNavigation: Route(route = "jobsNavigation")
    object JobsNavigatorScreen: Route(route = "jobsNavigator")
}


package com.example.jobharbor

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jobharbor.di.Injection
import com.example.jobharbor.ui.common.NavigationItem
import com.example.jobharbor.ui.common.UiState
import com.example.jobharbor.ui.navgraph.Route
import com.example.jobharbor.ui.screen.bookmark.BookmarkScreen
import com.example.jobharbor.ui.screen.detail.DetailJobScreen
import com.example.jobharbor.ui.screen.home.HomeScreen
import com.example.jobharbor.ui.screen.home.HomeViewModel
import com.example.jobharbor.ui.screen.profile.ProfileScreen
import com.example.jobharbor.ui.screen.search.SearchScreen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JobsApplication : Application()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobHarborApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val  viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )

    val uiState by viewModel.uiState.collectAsState(initial = UiState.Loading)

    val jobs = when (val currentState = uiState) {
        is UiState.Success -> currentState.data
        else -> emptyList()
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != Route.DetailScreen.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier,

        ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.HomeScreen.route) {
                HomeScreen(
                    navController,
                    navigateToDetail = { jobId ->
                        navController.navigate(Route.DetailScreen.createRoute(jobId))
                    }
                )
            }
            composable(
                route = Route.DetailScreen.route,
                arguments = listOf(navArgument("jobId") { type = NavType.LongType })
            ) {
                val id = it.arguments?.getLong("jobId") ?: -1L
                val context = LocalContext.current
                DetailJobScreen(
                    jobId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onApplyButtonClicked = { message ->
                        shareOrder(context, message)
                    }
                )
            }
            composable(Route.SearchScreen.route) {
                    SearchScreen(
                        navController = navController,
                        goBack = { navController.popBackStack() },
                        navigateToDetail = { jobId ->
                            navController.navigate(Route.DetailScreen.createRoute(jobId))
                        }
                    )
            }
            composable(Route.ProfileScreen.route) {
                ProfileScreen()
            }

            composable(Route.BookmarkScreen.route) {
                BookmarkScreen()
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = "Home",
                icon = Icons.Default.Home,
                screen = Route.HomeScreen
            ),
            NavigationItem(
                title = "Search",
                icon = Icons.Default.Search,
                screen = Route.SearchScreen
            ),
            NavigationItem(
                title = "Profile",
                icon = Icons.Default.AccountCircle,
                screen = Route.ProfileScreen
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = false,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}


private fun shareOrder(context: Context, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.apply_job))
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.apply_job)
        )
    )
}

@Preview
@Composable
fun ButtomBarPrev() {
    JobHarborApp()
}
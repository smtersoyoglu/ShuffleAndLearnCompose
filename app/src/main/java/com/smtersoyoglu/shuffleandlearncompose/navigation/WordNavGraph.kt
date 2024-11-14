package com.smtersoyoglu.shuffleandlearncompose.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.smtersoyoglu.shuffleandlearncompose.screens.word_detail.WordDetailScreen
import com.smtersoyoglu.shuffleandlearncompose.screens.word_main.WordMainScreen

@Composable
fun WordNavGraph(modifier: Modifier) {

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Screen.WordMainScreen.route
            ) {
                composable(
                    route = Screen.WordMainScreen.route,
                    enterTransition = ::slideInToRight,
                    exitTransition = ::slideOutToLeft
                ) {
                    WordMainScreen(navController = navController)
                }

                composable(
                    route = Screen.WordDetailScreen.route,
                    arguments = listOf(navArgument("wordId") { type = NavType.IntType }),
                    enterTransition = ::slideInToLeft,
                    exitTransition = ::slideOutToLeft,
                    popEnterTransition = ::slideInToRight,
                    popExitTransition = ::slideOutToRight
                ) { backStackEntry ->
                    val wordId = backStackEntry.arguments?.getInt("wordId") ?: 0
                    WordDetailScreen(
                        navController = navController,
                        wordId = wordId,
                        viewModel = hiltViewModel()
                    )
                }
            }
        }
    }
}

fun slideInToLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(300)
    )
}

fun slideInToRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(300)
    )
}

fun slideOutToLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(300)
    )
}

fun slideOutToRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(300)
    )
}



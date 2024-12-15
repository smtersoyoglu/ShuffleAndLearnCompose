package com.smtersoyoglu.shuffleandlearncompose.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.smtersoyoglu.shuffleandlearncompose.navigation.bottomnav.BottomNavBar
import com.smtersoyoglu.shuffleandlearncompose.navigation.bottomnav.BottomNavItem
import com.smtersoyoglu.shuffleandlearncompose.screens.learned_words.LearnedWordsScreen
import com.smtersoyoglu.shuffleandlearncompose.screens.word_detail.WordDetailScreen
import com.smtersoyoglu.shuffleandlearncompose.screens.word_game.WordGameScreen
import com.smtersoyoglu.shuffleandlearncompose.screens.word_main.WordMainScreen

@Composable
fun WordNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(
            route = Screen.WordMainScreen.route,
            title = "Home",
            icon = Icons.Default.Home
        ),
        BottomNavItem(
            route = Screen.LearnedWordsScreen.route,
            title = "Learned",
            icon = Icons.Default.Star
        ),
        BottomNavItem(
            route = Screen.WordGameScreen.route,
            title = "Game",
            icon = Icons.Default.PlayArrow
        )
    )

    Scaffold(
        bottomBar = {
            // Mevcut rota bilgisini kontrol et
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            // Sadece ana ekranlar için BottomNavBar göster
            if (currentRoute in bottomNavItems.map { it.route }) {
                BottomNavBar(navController = navController, items = bottomNavItems)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.WordMainScreen.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(
                route = Screen.WordMainScreen.route,
                enterTransition = ::slideInToRight,
                exitTransition = ::slideOutToLeft
            ) {
                WordMainScreen(navController = navController)
            }

            composable(
                route = Screen.LearnedWordsScreen.route,
                enterTransition = ::slideInToRight,
                exitTransition = ::slideOutToLeft
            ) {
                LearnedWordsScreen(navController = navController)
            }

            composable(
                route = Screen.WordGameScreen.route,
                enterTransition = ::slideInToRight,
                exitTransition = ::slideOutToLeft
            ) {
                WordGameScreen(navController = navController)
            }

            composable(
                route = Screen.WordDetailScreen.route,
                arguments = listOf(
                    navArgument("wordId") { type = NavType.IntType }),
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) { backStackEntry ->
                val wordId = backStackEntry.arguments?.getInt("wordId") ?: 0
                WordDetailScreen(
                    navController = navController,
                    wordId = wordId,
                    viewModel = hiltViewModel(),
                )
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




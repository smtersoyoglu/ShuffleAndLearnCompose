package com.smtersoyoglu.shuffleandlearncompose.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.smtersoyoglu.shuffleandlearncompose.R
import com.smtersoyoglu.shuffleandlearncompose.navigation.bottomnav.BottomNavBar
import com.smtersoyoglu.shuffleandlearncompose.navigation.bottomnav.BottomNavItem
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.aichat.ChatPage
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words.LearnedWordsScreen
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.splash.SplashScreen
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.WordDetailScreen
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.WordGameScreen
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main.WordMainScreen
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.BackgroundColor

@Composable
fun WordNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(
            route = Screen.WordMainScreen.route,
            title = "Home",
            icon = R.drawable.ic_home
        ),
        BottomNavItem(
            route = Screen.LearnedWordsScreen.route,
            title = "Learned",
            icon = R.drawable.ic_learned
        ),
        BottomNavItem(
            route = Screen.WordGameScreen.route,
            title = "Game",
            icon = R.drawable.ic_matchword
        ),
        BottomNavItem(
            route = Screen.ChatPage.route,
            title = "ChatBot",
            icon = R.drawable.ic_chatbot
        )
    )

    Scaffold(
        bottomBar = {
            // Mevcut rota bilgisini kontrol et
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            // Sadece ana ekranlar için BottomNavBar göster
            if (currentRoute in bottomNavItems.map { it.route }) {
                BottomNavBar(navController = navController, items = bottomNavItems)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route,
            modifier = modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(innerPadding)
        ) {
            composable(route = Screen.SplashScreen.route) {
                SplashScreen(navController = navController)
            }

            composable(
                route = Screen.WordMainScreen.route,
            ) {
                WordMainScreen(navController = navController)
            }

            composable(
                route = Screen.LearnedWordsScreen.route,
            ) {
                LearnedWordsScreen(navController = navController)
            }

            composable(
                route = Screen.WordGameScreen.route,
            ) {
                WordGameScreen(navController = navController)
            }

            composable(
                route = Screen.WordDetailScreen.route,
                arguments = listOf(
                    navArgument("wordId") { type = NavType.IntType }),
            ) { backStackEntry ->
                val wordId = backStackEntry.arguments?.getInt("wordId") ?: 0
                WordDetailScreen(
                    navController = navController,
                    wordId = wordId,
                    viewModel = hiltViewModel(),
                )
            }

            composable(
                route = Screen.ChatPage.route,
            ) {
                ChatPage(navController = navController)
            }
        }
    }
}
package com.smtersoyoglu.shuffleandlearncompose.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smtersoyoglu.shuffleandlearncompose.R
import com.smtersoyoglu.shuffleandlearncompose.navigation.bottomnav.BottomNavBar
import com.smtersoyoglu.shuffleandlearncompose.navigation.bottomnav.BottomNavItem
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.aichat.ChatPage
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words.LearnedWordsScreen
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words.LearnedWordsViewModel
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.splash.SplashScreen
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.WordDetailScreen
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.WordDetailViewModel
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.WordGameScreen
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.WordGameViewModel
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main.WordMainScreen
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main.WordViewModel
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.BackgroundColor

@Composable
fun WordNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(
            route = Screens.WordMainScreen,
            title = "Home",
            icon = R.drawable.ic_home
        ),
        BottomNavItem(
            route = Screens.LearnedWordsScreen,
            title = "Learned",
            icon = R.drawable.ic_learned
        ),
        BottomNavItem(
            route = Screens.WordGameScreen,
            title = "Game",
            icon = R.drawable.ic_matchword
        ),
        BottomNavItem(
            route = Screens.ChatPage,
            title = "ChatBot",
            icon = R.drawable.ic_aichat
        )
    )


    Scaffold(
        bottomBar = {
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            if (Screens.shouldShowBottomBar(currentRoute)) {
                BottomNavBar(navController = navController, items = bottomNavItems)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.SplashScreen,
            modifier = modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(innerPadding)
        ) {
            composable<Screens.SplashScreen> {
                SplashScreen(navController = navController)
            }

            composable<Screens.WordMainScreen> {
                val viewModel: WordViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val uiEffect = viewModel.uiEffect
                WordMainScreen(
                    uiState = uiState,
                    uiAction = viewModel::onAction,
                    uiEffect = uiEffect,
                    onNavigateDetail = { wordId ->
                        navController.navigate(Screens.WordDetailScreen(wordId))
                    }
                )
            }

            composable<Screens.LearnedWordsScreen> {
                val viewModel: LearnedWordsViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val uiEffect = viewModel.uiEffect
                LearnedWordsScreen(
                    uiState = uiState,
                    uiAction = viewModel::onAction,
                    uiEffect = uiEffect,
                    onNavigateToLearnedWordDetail = { wordId ->
                        navController.navigate(Screens.WordDetailScreen(wordId))
                    }
                )
            }

            composable<Screens.WordGameScreen> {
                val viewModel: WordGameViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val uiEffect = viewModel.uiEffect
                WordGameScreen(
                    uiState = uiState,
                    uiAction = viewModel::onAction,
                    uiEffect = uiEffect,
                    onNavigateMain = { navController.navigate(Screens.WordMainScreen) }
                )
            }

            composable<Screens.WordDetailScreen> {
                val viewModel: WordDetailViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val uiEffect = viewModel.uiEffect
                WordDetailScreen(
                    uiState = uiState,
                    uiAction = viewModel::onAction,
                    uiEffect = uiEffect,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable<Screens.ChatPage> {
                ChatPage(navController = navController)
            }
        }
    }
}
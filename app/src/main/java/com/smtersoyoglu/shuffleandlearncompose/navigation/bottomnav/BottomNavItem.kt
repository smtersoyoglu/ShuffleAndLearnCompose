package com.smtersoyoglu.shuffleandlearncompose.navigation.bottomnav

import com.smtersoyoglu.shuffleandlearncompose.navigation.Screens

data class BottomNavItem(
    val route: Screens,
    val title: String,
    val icon: Int,
)
package com.smtersoyoglu.shuffleandlearncompose.navigation.bottomnav

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,       // Ekran rotası
    val title: String,       // Sekmenin başlığı
    val icon: ImageVector    // Sekmenin ikonu
)

package com.smtersoyoglu.shuffleandlearncompose.navigation.bottomnav

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaRegular

@Composable
fun BottomNavBar(navController: NavController, items: List<BottomNavItem>) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.White,
        tonalElevation = 5.dp,
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(24.dp))
            .fillMaxWidth()

    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 14.sp,
                        fontFamily = FredokaRegular
                    )
                },
                selected = isSelected,
                alwaysShowLabel = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(item.route) {
                                inclusive = true
                            } // Hedef ekranı tamamen yeniden yükle
                            launchSingleTop = true // Aynı ekran tekrar yığın içinde alınmaz
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.Black.copy(alpha = 0.4f),
                    unselectedTextColor = Color.Black.copy(alpha = 0.4f),
                    indicatorColor = Color.Transparent
                ),
                modifier = Modifier.padding(0.dp).align(Alignment.CenterVertically) // Yeni hizalama
            )
        }
    }
}

@Preview
@Composable
fun BottomNavBarPreview() {
    val navController = NavController(LocalContext.current)
    BottomNavBar(
        navController = navController, items = listOf(
            BottomNavItem(
                title = "Home",
                route = "home",
                icon = Icons.Default.Home
            ),
            BottomNavItem(
                title = "Search",
                route = "search",
                icon = Icons.Default.Home
            ),
            BottomNavItem(
                title = "Settings",
                route = "settings",
                icon = Icons.Default.Home
            )
        )
    )
}
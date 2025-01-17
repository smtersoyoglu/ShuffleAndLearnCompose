package com.smtersoyoglu.shuffleandlearncompose.navigation.bottomnav

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.smtersoyoglu.shuffleandlearncompose.R
import com.smtersoyoglu.shuffleandlearncompose.navigation.Screens
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.BottomNavBackgroundColor
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.ButtonColor
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.FredokaRegular
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.UnSelectedIconColor

@Composable
fun BottomNavBar(navController: NavController, items: List<BottomNavItem>) {
    NavigationBar(
        containerColor = BottomNavBackgroundColor,
        contentColor = Color.White,
        modifier = Modifier.height(70.dp),

        ) {
        val currentDestination by navController.currentBackStackEntryAsState()

        items.forEach { destination ->
            val selected =
                currentDestination?.destination?.route == Screens.getRoute(destination.route)
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = destination.icon),
                        contentDescription = destination.title,
                    )
                },
                label = {
                    Text(
                        text = destination.title,
                        fontFamily = FredokaRegular,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                selected = selected,
                alwaysShowLabel = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(destination.route) {
                            popUpTo(destination.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.White,
                    unselectedIconColor = UnSelectedIconColor.copy(alpha = 0.4f),
                    unselectedTextColor = UnSelectedIconColor.copy(alpha = 0.4f),
                    indicatorColor = ButtonColor
                )
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
                route = Screens.WordMainScreen,
                icon = R.drawable.ic_home
            ),
            BottomNavItem(
                title = "Learned",
                route = Screens.LearnedWordsScreen,
                icon = R.drawable.ic_learned
            ),
            BottomNavItem(
                title = "WordGame",
                route = Screens.WordGameScreen,
                icon = R.drawable.ic_matchword
            )
        )
    )
}
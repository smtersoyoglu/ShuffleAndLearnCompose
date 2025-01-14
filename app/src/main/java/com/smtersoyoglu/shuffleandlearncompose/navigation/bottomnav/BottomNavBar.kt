package com.smtersoyoglu.shuffleandlearncompose.navigation.bottomnav

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.smtersoyoglu.shuffleandlearncompose.R
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.BottomNavBackgroundColor
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.ButtonColor
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.FredokaRegular
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.UnSelectedIconColor

@Composable
fun BottomNavBar(navController: NavController, items: List<BottomNavItem>) {
    NavigationBar(
        containerColor = BottomNavBackgroundColor,
        contentColor = Color.White,
        tonalElevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 14.dp, end = 14.dp, bottom = 14.dp, top = 4.dp)
            .clip(RoundedCornerShape(26.dp))
            .size(90.dp)

    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                modifier = Modifier.padding(top = 22.dp),
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 14.sp,
                        fontFamily = FredokaRegular,
                        fontWeight = FontWeight.SemiBold
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
                route = "home",
                icon = R.drawable.ic_home
            ),
            BottomNavItem(
                title = "Learned",
                route = "learned",
                icon = R.drawable.ic_learned
            ),
            BottomNavItem(
                title = "WordGame",
                route = "wordgame",
                icon = R.drawable.ic_matchword
            )
        )
    )
}
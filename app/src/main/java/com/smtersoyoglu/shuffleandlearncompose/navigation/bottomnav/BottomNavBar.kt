package com.smtersoyoglu.shuffleandlearncompose.navigation.bottomnav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
@Composable
fun BottomNavBar(navController: NavController, items: List<BottomNavItem>) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        containerColor = Color.White, // Arka plan rengi
        tonalElevation = 8.dp // Hafif gölge efekti
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                icon = {
                    if (isSelected) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFEDE7F6), // Seçili arka plan rengi
                                    shape = RoundedCornerShape(60.dp) // Oval şekil
                                )
                                .padding(horizontal = 21.dp, vertical = 10.dp) // İç boşluk
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = Color(0xFF6200EE) // Seçili ikon rengi
                            )
                            Spacer(modifier = Modifier.width(8.dp)) // İkon ve metin arasındaki boşluk
                            Text(
                                text = item.title,
                                color = Color(0xFF6200EE), // Seçili metin rengi
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = Color.Black // Seçili olmayan ikon rengi
                        )
                    }
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent // Default indicator tamamen kaldırılıyor
                ),
                alwaysShowLabel = false // Metni her zaman göstermiyoruz, sadece seçilince gösteriliyor
            )
        }
    }
}
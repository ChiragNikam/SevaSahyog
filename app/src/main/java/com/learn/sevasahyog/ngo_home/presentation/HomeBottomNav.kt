package com.learn.sevasahyog.ngo_home.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.R
import com.learn.sevasahyog.data.BottomNavigationItem

@Composable
fun HomeBottomNav(
    navHostController: NavHostController)
{
    val bottomBarItems = listOf(
        BottomNavigationItem(
            title = "Home",
            route = "home",
            selectedIcon = painterResource(id = R.drawable.material_symbols_light_home),
            unselectedIcon = painterResource(id = R.drawable.material_symbols_light_home_outline)
        ),

        BottomNavigationItem(
            title = "Event",
            route = "event",
            selectedIcon = painterResource(id = R.drawable.material_symbols_event),
            unselectedIcon = painterResource(id = R.drawable.material_symbols_event_outline)),

        BottomNavigationItem(
            title = "Profile",
            route = "profile",
            selectedIcon = painterResource(id = R.drawable.iconamoon_profile_fill),
            unselectedIcon = painterResource(id = R.drawable.iconamoon_profile_light)
        )

    )

    val bottomNavController= rememberNavController()

    Scaffold(bottomBar = {
       BottomNav(items = bottomBarItems, navController = bottomNavController) {
           bottomNavController.navigate(it.route)
       }
   }) {
       Surface (modifier = Modifier.padding(it)){
         //  NgoBottomNavigation(navHostController)
       }
   }
}


// Bottom Navigation Bar for Home Screen
@Composable
fun BottomNav(
    items: List<BottomNavigationItem>,
    navController: NavController,
    onItemClicked: (BottomNavigationItem) -> Unit
) {
    NavigationBar(
        modifier = Modifier.shadow(8.dp),
        containerColor = MaterialTheme.colorScheme.onPrimary
    ) { // to remember back state
        val backStackEntry = navController.currentBackStackEntryAsState()

        items.forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                selected = bottomNavigationItem.route == backStackEntry.value?.destination?.route,
                onClick = {
                    onItemClicked(bottomNavigationItem)
                },
                label = {
                    if (bottomNavigationItem.route == backStackEntry.value?.destination?.route) {
                        Text(
                            text = bottomNavigationItem.title,
                            fontWeight = FontWeight(900),
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Text(
                            text = bottomNavigationItem.title,
                            fontWeight = FontWeight(700),
                            color = Color.Gray
                        )
                    }
                },
                alwaysShowLabel = true,
                icon = {
                    if (bottomNavigationItem.route == backStackEntry.value?.destination?.route) {
                        Icon(
                            bottomNavigationItem.selectedIcon,
                            contentDescription = bottomNavigationItem.title,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Icon(
                            painter = bottomNavigationItem.unselectedIcon,
                            contentDescription = bottomNavigationItem.title,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewHomeBottomNav() {
    HomeBottomNav(navHostController = rememberNavController())

}
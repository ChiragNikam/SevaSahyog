package com.learn.sevasahyog.ngo_home.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.learn.sevasahyog.ngo_home.presentation.CreateEvent
import com.learn.sevasahyog.ngo_home.presentation.EventDetailScreen
import com.learn.sevasahyog.ngo_home.presentation.EventScreen
import com.learn.sevasahyog.ngo_home.presentation.ProfileScreen
import com.learn.sevasahyog.ngo_home.presentation.HomeScreen

@Composable
fun NgoBottomNavigation(
    navHostController:NavHostController
) {

    NavHost(navController = navHostController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navHostController)
        }

        composable("event") {
            EventScreen(navController = navHostController)
        }
        navigation(startDestination = "event/eventScreen", route = "event_screen"){
            composable(route="event/eventScreen"){
                EventScreen(navController = navHostController)
            }
            composable(route="event/createEventScreen"){
                CreateEvent(navController = navHostController)
            }
            composable(route="event/eventDetailScreen"){
                EventDetailScreen()
            }
        }

        composable("profile") {
            ProfileScreen(navController = navHostController)
        }

    }

}

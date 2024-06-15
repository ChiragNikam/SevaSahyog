package com.learn.sevasahyog.ngo_home.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.learn.sevasahyog.ngo_home.presentation.EventScreen
import com.learn.sevasahyog.ngo_home.presentation.ProfileScreen
import com.learn.sevasahyog.home_screens.HomeScreen

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

        composable("profile") {
            ProfileScreen(navController = navHostController)
        }

    }

}

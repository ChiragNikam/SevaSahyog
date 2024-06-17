package com.learn.sevasahyog.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.learn.sevasahyog.auth.presentation.ChooseAccountType
import com.learn.sevasahyog.auth.presentation.SignIn
import com.learn.sevasahyog.auth.presentation.SignUpNgo
import com.learn.sevasahyog.auth.presentation.SingUpUser
import com.learn.sevasahyog.ngo_home.presentation.CreateEvent
import com.learn.sevasahyog.ngo_home.presentation.EventScreen
import com.learn.sevasahyog.ngo_home.presentation.HomeBottomNav

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "ngo"
    ) {
        navigation(startDestination = "auth/signIn", route = "auth") {
            composable(route = "auth/signIn") {
                SignIn(navController = navController)
            }
            composable(route = "auth/chooseAccountType") {
                ChooseAccountType(navController = navController)
            }
            composable(route = "auth/signUpUser") {
                SingUpUser(navController = navController)
            }
            composable(route = "auth/signUpNgo") {
                SignUpNgo(navController = navController)
            }
        }
        navigation(startDestination = "ngo/bottomNav", route = "ngo") {
            composable(route = "ngo/bottomNav") {
                HomeBottomNav(navHostController = navController)
            }
            navigation(startDestination = "ngo/eventScreen", route = "event_screen"){
                composable(route="ngo/event_screen"){
                   EventScreen(navController = navController)
                }
                composable(route="ngo/create_event"){
                   CreateEvent()
                }
            }


        }
    }
}
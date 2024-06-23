package com.learn.sevasahyog.nav

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.learn.sevasahyog.SplashScreen
import com.learn.sevasahyog.auth.presentation.ChooseAccountType
import com.learn.sevasahyog.auth.presentation.SignIn
import com.learn.sevasahyog.auth.presentation.SignUpNgo
import com.learn.sevasahyog.auth.presentation.SingUpUser
import com.learn.sevasahyog.ngo_home.items.event.presentation.CreateEvent
import com.learn.sevasahyog.ngo_home.items.event.presentation.EventDetailScreen
import com.learn.sevasahyog.ngo_home.presentation.HomeBottomNav

@Composable
fun AppNavigation(navController: NavHostController, isLoggedIn: Boolean) {
    val inHorizontal = slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(durationMillis = 300)
    )
    val outHorizontal = slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(durationMillis = 300)
    )
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        navigation(startDestination = "splashScreen", route = "splash") {
            composable(route = "splashScreen") {
                SplashScreen { isLoggedIn, userType, isLoginSuccess ->
                    navController.popBackStack()
                    if (isLoggedIn && isLoginSuccess) {
                        if (userType == "user")
                            navController.navigate("")
                        else
                            navController.navigate("ngo")
                    } else {
                        navController.navigate("auth")
                    }
                }
            }
        }
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
            navigation(
                startDestination = "event/eventDetailScreen",
                route = "event_screen",
            ) {
                composable(
                    route = "event/createEventScreen",
                    enterTransition = { inHorizontal },
                    popExitTransition = { outHorizontal }
                ) {
                    CreateEvent(navController)
                }
                composable(
                    route = "event/eventDetailScreen",
                    enterTransition = { inHorizontal },
                    popExitTransition = { outHorizontal }
                ) {
                    EventDetailScreen(navController = navController)
                }
            }
        }
    }
}
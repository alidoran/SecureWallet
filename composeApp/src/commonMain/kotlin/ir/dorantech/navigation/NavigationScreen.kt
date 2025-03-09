package ir.dorantech.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.dorantech.feature1.viewmodel.UserViewModel
import ir.dorantech.ui.screen.SignInScreen
import ir.dorantech.ui.model.FirstScreenButtons
import ir.dorantech.ui.screen.MainScreen
import ir.dorantech.ui.screen.SignUpScreen
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        val viewModel by localDI().instance<UserViewModel>()
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = RouteAuth.SignUpRoute,
        ) {
            composable<RouteApp.MainRoute> {
                MainScreen(
                    onButtonClick = { clickedButton ->
                        when (clickedButton) {
                            FirstScreenButtons.Retrofit ->
                                navController.navigate(RouteApp.Feature1Route)
                        }
                    },
                    modifier = modifier
                )
            }

            composable<RouteApp.SignInRoute> {
                SignInScreen(
                    onSignInSuccess = { navController.navigate(RouteApp.Feature1Route) },
                    modifier = modifier.fillMaxSize()
                )
            }

            composable<RouteAuth.SignUpRoute> {
                SignUpScreen(
                    onSignUpSuccess = { navController.navigate(RouteApp.Feature1Route) },
                    modifier = modifier.fillMaxSize()
                )
            }
        }
    }
}
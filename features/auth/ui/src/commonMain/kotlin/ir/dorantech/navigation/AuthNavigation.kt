package ir.dorantech.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.dorantech.ui.screen.SignInScreen
import ir.dorantech.ui.screen.SignUpScreen

@Composable
fun AuthNavigation(
    modifier: Modifier = Modifier,
    onAuthSuccess: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RouteAuth.SignInRoute
    ) {
        composable<RouteAuth.SignInRoute> {
            SignInScreen(
                onSignInSuccess = {
                    navController.previousBackStackEntry?.savedStateHandle?.set("result", true)
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable<RouteAuth.SignUpRoute> {
            SignUpScreen(
                onSignUpSuccess = {
                    onAuthSuccess()
                    navController.navigate(RouteAuth.SignInRoute)
                },
                modifier = modifier
            )
        }
//
//        composable<RouteAuth.ForgotPasswordRoute> {
//            ForgotPasswordScreen(
//                onPasswordResetSuccess = {
//                    navController.navigate(RouteAuth.SignInRoute)
//                },
//                modifier = modifier
//            )
//        }
    }
}

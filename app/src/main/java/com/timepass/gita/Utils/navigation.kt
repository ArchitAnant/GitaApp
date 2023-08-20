package com.timepass.gita.Utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.timepass.gita.ViewModels.GitaViewModel
import com.timepass.gita.ViewModels.MainViewModel
import com.timepass.gita.ui.GitaUi.GitaScreen
import com.timepass.gita.ui.GitaUi.Screen


@Composable
fun Navigation(navController: NavHostController = rememberNavController()) {
    val controller = rememberSystemUiController()
    NavHost(navController = navController, startDestination = Screen.Main.route){
        composable(route = Screen.Main.route){
            val viewModel = MainViewModel()
            Screen(viewModel,controller){
                navController.navigate(route = Screen.GitaExpanded.route)
            }
        }
        composable(route = Screen.GitaExpanded.route){
            val gitaViewModel = GitaViewModel()
            GitaScreen(gitaViewModel = gitaViewModel, controller = controller)
        }
    }
}
sealed class Screen(val route:String){
    object Main: Screen(route = "gita_main")
    object GitaExpanded : Screen(route = "expanded_gita")
}
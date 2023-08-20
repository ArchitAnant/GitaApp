package com.timepass.gita

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.timepass.gita.Utils.Navigation
import com.timepass.gita.ViewModels.MainViewModel
import com.timepass.gita.ui.GitaUi.Screen
import com.timepass.gita.ui.theme.GitaTheme

/*
    Disclaimer:
    The "Gita" App has been designed and developed by Archit Anant as a personal project and not intended to be sold
    The "Gita" App  ©Archit Anant(https://github.com/ArchitAnant)
    The apis used are mentioned below:
    Weather API: https://api.weatherapi.com/v1
    Gita API: https://bhagavadgitaapi.in/
 */


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}
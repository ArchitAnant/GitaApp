package com.timepass.gita.ui.GitaUi

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import  androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.timepass.gita.ViewModels.MainViewModel
import com.timepass.gita.Utils.backgroundGradient
import com.timepass.gita.Utils.demoGita
import com.timepass.gita.Utils.englishFont
import com.timepass.gita.Utils.gitaPalletColor
import com.timepass.gita.Utils.hindiFont
import com.timepass.gita.Utils.pallet
import com.timepass.gita.weather.WeatherData
import com.timepass.gita.Utils.weatherPalletTextColor

@Composable
fun Screen(viewModel: MainViewModel,controller: SystemUiController,onNavigation:()->Unit) {
    SideEffect {
        controller.setStatusBarColor(color = Color(0xFF19245E),darkIcons = false)
    }

    val weather = viewModel.weatherData.collectAsState().value
    val gita  = viewModel.gitaData.collectAsState().value
    Column(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = backgroundGradient,
                    startY = 0.1f,
                )
            )
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        WeatherPallet(
            weather = weather
        )
        Spacer(modifier = Modifier.height(20.dp))
        GitaPallet(
            chapterNum = gita.chapter,
            verseNum = gita.verse,
            verse = gita.slok,
            desc = gita.rams.ht,
            onForwardClick = {
                viewModel._gitaDataN.value = demoGita
                viewModel.onForwardClick(chapter = gita.chapter, verse = gita.verse)
                             },
            onBackClick = {
                viewModel._gitaDataN.value = demoGita
                viewModel.OnBackClick(chapter = gita.chapter, verse = gita.verse)
            },
            onNavigation = onNavigation
        )
        val context = LocalContext.current
        Text(
            text = "About App!",
            fontFamily = englishFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = gitaPalletColor,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Toast.makeText(context,"This has been developed by Archit Anant!",Toast.LENGTH_LONG).show()
                }

        )
        Spacer(modifier = Modifier.weight(1f))
    }

}
    


@Composable
fun WeatherPallet(
    weather: WeatherData
) {
    val temp = weather.current.temp.toString()
    val weatherTag = weather.current.condition.text
    val windSpeed = weather.current.windph.toString()
    val moisture = weather.current.precipIn.toString()
    val humidity = weather.current.humidity.toString()
    val location = weather.location.country
    var finalImageUrl = "cdn.weatherapi.com/weather/64x64/night/143.png"
    Card(
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 30.dp)
            .height(350.dp),
        shape = RoundedCornerShape(size = 19.dp),
        colors = CardDefaults.cardColors(containerColor = pallet[0])
    ){
        if(location == "No Location"){
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = weatherPalletTextColor)
            }
        }
        else{
            finalImageUrl = weather.current.condition.icon.substring(startIndex = 2)
            Column(Modifier.padding(start = 30.dp, end = 30.dp, top = 40.dp, bottom = 40.dp)){
                Row(Modifier.fillMaxWidth()) {
                    Column(Modifier.weight(2f)){
                        Row{
                            Text(
                                text = temp,
                                fontFamily = englishFont,
                                fontWeight = FontWeight.Medium,
                                fontSize = 48.sp,
                                color = weatherPalletTextColor,
                            )
                            Text(
                                text = "°C",
                                fontFamily = englishFont,
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp,
                                color = weatherPalletTextColor,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = weatherTag,
                            fontFamily = englishFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            color = weatherPalletTextColor,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https:$finalImageUrl")
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.size(110.dp),
                        colorFilter = ColorFilter.tint(weatherPalletTextColor),
                        )

                }
                Spacer(modifier = Modifier.weight(2f))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                    detailWeather(Icons.Default.Air,windSpeed)
                    detailWeather(Icons.Default.Water,moisture)
                    detailWeather(Icons.Default.WaterDrop,humidity)
                }
            }
        }

    }
}

@Composable
fun detailWeather(detailIcon: ImageVector, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(detailIcon,
            contentDescription = null,
            tint = weatherPalletTextColor,
            modifier = Modifier
                .height(56.dp)
                .width(46.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = value,
            fontFamily = englishFont,
            fontWeight = FontWeight.Normal,
            color = weatherPalletTextColor,
            fontSize = 22.sp
        )
    }
}


@Composable
fun GitaPallet(chapterNum: Int,verseNum: Int,verse: String,desc: String,onBackClick: () -> Unit,onForwardClick: () -> Unit,onNavigation:()->Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
            .height(420.dp)
            .clickable(enabled = true, onClick = onNavigation),
        shape = RoundedCornerShape(size = 19.dp),
        colors = CardDefaults.cardColors(containerColor = pallet[1]),
    ){
        Column(Modifier.padding(20.dp)){
            if(verse == "No slok"){
                Box(modifier = Modifier.fillMaxSize()){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = gitaPalletColor)
                }
            }
            else{
                gitaTopBar(onBackClick = onBackClick, onForwardClick = onForwardClick)
                ChapterAndVerse(chapterNum = chapterNum, verseNum = verseNum)
                Spacer(modifier = Modifier.weight(1f))
                verseDisplay(verse = verse , desc =  desc)
            }
        }
    }
}


@Composable
fun gitaTopBar(onForwardClick:()->Unit,onBackClick:()->Unit){
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()){
        IconButton(onClick = onBackClick) {
            Icon(Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                tint = gitaPalletColor,
                modifier = Modifier.size(30.dp)
            )
        }
        Text(
            text = "Gita",
            fontFamily = englishFont,
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            color = gitaPalletColor
        )
        IconButton(onClick = onForwardClick) {
            Icon(Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = gitaPalletColor,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun ChapterAndVerse(chapterNum: Int,verseNum : Int) {
    Row(modifier = Modifier
        .padding(start = 5.dp, end = 8.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = "Chapter $chapterNum",
            fontFamily = englishFont,
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            color = gitaPalletColor,
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )
        Text(
            text = "Verse $verseNum",
            fontFamily = englishFont,
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            color = gitaPalletColor,
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )
    }
}

@Composable
fun verseDisplay(verse:String,desc:String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = verse,
            fontFamily = hindiFont,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = gitaPalletColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Divider(color = gitaPalletColor,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp))
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = desc,
            fontFamily = hindiFont,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = gitaPalletColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview
@Composable
fun UiPreview() {

}
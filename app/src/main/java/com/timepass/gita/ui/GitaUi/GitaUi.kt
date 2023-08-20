package com.timepass.gita.ui.GitaUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.timepass.gita.Utils.demoExpandedGita
import com.timepass.gita.Utils.englishFont
import com.timepass.gita.Utils.expandedGitaVerseColor
import com.timepass.gita.Utils.expandedVerseColor
import com.timepass.gita.Utils.gitaPalletColor
import com.timepass.gita.Utils.hindiFont
import com.timepass.gita.ViewModels.GitaViewModel




@Composable
fun GitaScreen(gitaViewModel: GitaViewModel,controller: SystemUiController) {
    SideEffect {
        controller.setStatusBarColor(color = Color(0xFF55304A),darkIcons = false)
    }
    val tempObject  = gitaViewModel.gitaObject.collectAsState().value
    val map  = gitaViewModel.map
    var chapNum by remember {
        mutableIntStateOf(1)
    }
    var verseNum by remember {
        mutableIntStateOf(1)
    }
    callData(gitaViewModel = gitaViewModel, chapterNum = chapNum, verseNum =verseNum )
    Column (modifier = Modifier
        .background(Color(0xFF55304A))
        .fillMaxSize()
        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        ButtonList(map,tempObject.chapter,  onVerseLoad = {chapter,verse ->
            gitaViewModel._gitaObject.value = demoExpandedGita
            chapNum = chapter
            verseNum = verse
        })
        Spacer(modifier = Modifier.height(15.dp))
        if (tempObject.slok=="No slok"){
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = gitaPalletColor)
            }
        }
        else {
            GitaPalletIn(
                chapterNum = tempObject.chapter,
                verseNum = tempObject.verse,
                verse = tempObject.slok,
                desc = tempObject.rams.ht,
                onBackClick = { chapter, verse ->
                    if(verse>0) {
                        gitaViewModel._gitaObject.value = demoExpandedGita
                        chapNum = chapter
                        verseNum = verse
                    }
                },
                onForwardClick = { chapter, verse ->
                    if(verse<= gitaViewModel.map[chapNum]!!) {
                        gitaViewModel._gitaObject.value = demoExpandedGita
                        chapNum = chapter
                        verseNum = verse
                    }
                },
            )
        }

        Text(
            text = "About App!",
            fontFamily = englishFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = gitaPalletColor
        )
    }
}

fun callData(gitaViewModel: GitaViewModel,chapterNum: Int,verseNum: Int){
        gitaViewModel.getVerse(chapterNum, verseNum)
}


@Composable
fun GitaPalletIn(chapterNum: Int,
                 verseNum: Int,
                 verse: String,
                 desc: String,
                 onBackClick: (Int,Int) -> Unit,
                 onForwardClick: (Int,Int) -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            ,
        shape = RoundedCornerShape(size = 19.dp),
        colors = CardDefaults.cardColors(containerColor = expandedGitaVerseColor)
    ){
        Column(
            Modifier
                .padding(20.dp)
                .fillMaxWidth()){
            if(verse == "No slok"){
                Box(modifier = Modifier.fillMaxSize()){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = expandedGitaVerseColor)
                }
            }
            else{
                GitaTop(onBackClick = onBackClick, onForwardClick = onForwardClick, chapterNum = chapterNum, verseNum = verseNum)
                Spacer(modifier = Modifier.weight(1f))
                VerseDis(verse = verse , desc =  desc)
            }
        }
    }
}


@Composable
fun GitaTop(onForwardClick:(Int,Int)->Unit,onBackClick:(Int,Int)->Unit,chapterNum:Int,verseNum: Int){
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()){
        IconButton(onClick = { onBackClick(chapterNum,verseNum-1) }) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                tint = gitaPalletColor,
                modifier = Modifier.size(30.dp)
            )
        }
        Column (horizontalAlignment =Alignment.CenterHorizontally ){
            Text(
                text = "Chapter $chapterNum",
                fontFamily = englishFont,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = gitaPalletColor
            )
            Text(
                text = "Verse $verseNum",
                fontFamily = englishFont,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = expandedVerseColor
            )
        }
        IconButton(onClick = { onForwardClick(chapterNum,verseNum+1) }) {
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = gitaPalletColor,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}


@Composable
fun VerseDis(verse:String,desc:String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.weight(1f))
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
        Text(
            text = "लेखक-",
            fontFamily = hindiFont,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = expandedVerseColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = "स्वामी रामसुखदास",
            fontFamily = hindiFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = expandedVerseColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
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
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ButtonList(map: HashMap<Int, Int>,chapter:Int,onVerseLoad:(Int,Int)-> Unit) {
    var chap by remember {
        mutableIntStateOf(chapter)
    }
    Column {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(13.dp),
        ) {
            items(map.size) { chapterNum ->
                FeatureButton(text = "Chapter" ,135.dp,chapterNum+1, 49.dp, 19.dp, 14.sp){chap = chapterNum+1}
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(13.dp),
        ) {
            items(map[chap]!!){verseNum->
                FeatureButton(text = "Verse",110.dp,verseNum+1,40.dp,14.dp,12.sp) {
                    onVerseLoad(chap,it)
                }
            }
        }

    }
}

@Composable
fun FeatureButton(text: String,
                  width: Dp,
                  chapterNo:Int,
                  height: Dp,
                  corner: Dp,
                  fontSize: TextUnit,
                  onClick:(Int) -> Unit
    ){
    Button(onClick = { onClick(chapterNo) },
        colors = ButtonDefaults.buttonColors(Color(0xFF693C5C)),
        shape = RoundedCornerShape(corner),
        modifier = Modifier
            .width(width)
            .height(height)
    ) {
        Text(
            text = "$text $chapterNo",
            fontFamily = englishFont,
            fontWeight = FontWeight.Medium,
            fontSize = fontSize,
            color = gitaPalletColor,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun GitaScreenPreview() {
}

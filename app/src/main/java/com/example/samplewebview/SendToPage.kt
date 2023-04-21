package com.example.samplewebview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun SendToPage(sendController: NavHostController) {
    NavHost(navController = sendController, startDestination = "webViewSample"){
        composable(route = "webViewSample") {
            webViewSample(sendController)
        }
        composable(route = "pushNotification") {
            pushNotification(sendController)
        }
    }

}
//Box(modifier = Modifier.weight(2f)) {
//    webViewSample("Android")
//}
//Box(modifier = Modifier.weight(1f)) {
//    pushNotification()
//}


@Composable
fun Screen1(onClickButton: ()->Unit = {}) {
    Column {
        Button(onClick = onClickButton) {
            Text(text = "Go to Screen 2")
        }
        Text(text = "Screen 1")
    }
}

@Composable
fun Screen2(onClickButton: ()->Unit = {}) {
    Column {
        Button(onClick = onClickButton) {
            Text(text = "Back to Screen 1")
        }
        Text(text = "Screen 2")
    }
}

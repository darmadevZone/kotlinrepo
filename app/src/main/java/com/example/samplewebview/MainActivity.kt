package com.example.samplewebview

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.R
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.example.samplewebview.ui.theme.SampleWebViewTheme
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import java.lang.IllegalStateException


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()


            var permissionResupester = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission(),
            ) {}
            LaunchedEffect(Unit) {
//                context.startActivity(
//                    Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
//                        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
//                    }
//                )
                val NotificationPermission = Manifest.permission.POST_NOTIFICATIONS
                permissionResupester.launch(NotificationPermission)
                Log.d("Notificaton ", "ok")
            }
            SampleWebViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text ="Navigation Main Page" )
                                }
                            )
                        }
                    ) {
                        Column(modifier = Modifier.padding(it)) {
                            Box(modifier = Modifier.weight(1f)) {
                                SendToPage(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun webViewSample(navController: NavController) {
    val state = rememberWebViewState("https://web-camp.io/magazine/archives/71007")
    Column() {
        Button(onClick = {
            navController.navigate(route = "pushNotification")
        }) {
            Text(text = "Send to pushNotification Screen")
        }
        WebView(state, onCreated = { it.settings.javaScriptEnabled = true })
    }
}

@Composable
fun pushNotification(navController: NavController) {
    val context = LocalContext.current
    var title = "push Notification title"
    var text = "push Notification Text"
    Column {
        Button(
            onClick = {
                navController.navigate(route = "webViewSample")
            }) {
            Text(text = "Send to WebView Screen")
        }
        Button(onClick = {
            val channelId = createNotificationChannel(context = context)
            var builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.notification_tile_bg).setContentTitle(title)
                .setContentText(text).setPriority(NotificationCompat.PRIORITY_HIGH)
            with(NotificationManagerCompat.from(context)) {

                notify(1, builder.build())
            }
        }) {
            Text("notication")
        }
    }
}

private fun createNotificationChannel(context: Context): String {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "channel name"
        val descriptionText = "push notification Text"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
            name, name, importance
        ).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        return name
    }
    throw IllegalStateException("error method createNotification ")
}

@Composable
fun SetupNotification() {
    val context = LocalContext.current
    var status by remember { mutableStateOf("loading") }
    var permissionResupester = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) {}

    val goToSetting =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
}

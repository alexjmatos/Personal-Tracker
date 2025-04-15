package com.masa.personaltracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.masa.personaltracker.data.AppDatabase
import com.masa.personaltracker.ui.theme.PersonalTrackerTheme
import com.masa.personaltracker.utils.GameManager
import com.masa.personaltracker.utils.Notifications
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class MainActivity : ComponentActivity(), CoroutineScope by MainScope(){
    private lateinit var gameManager: GameManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database
        AppDatabase.getDatabase(this, this)

        // Initialize game manager
         gameManager = GameManager(this, this)

        // Set up notification channel
        Notifications.createNotificationChannel(this)

        setContent {
            PersonalTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PersonalTrackerApp()
                }
            }
        }
    }
}
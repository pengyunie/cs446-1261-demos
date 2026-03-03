package ca.uwaterloo.cs446

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ca.uwaterloo.cs446.tiptime.TipTimeScreen
import ca.uwaterloo.cs446.ui.theme.TestingDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestingDemoTheme {
                TipTimeScreen()
            }
        }
    }
}
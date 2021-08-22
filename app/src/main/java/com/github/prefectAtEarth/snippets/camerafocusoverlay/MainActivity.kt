package com.github.prefectAtEarth.snippets.camerafocusoverlay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.prefectAtEarth.snippets.camerafocusoverlay.ui.camera.CameraScreen
import com.github.prefectAtEarth.snippets.camerafocusoverlay.ui.theme.CameraFocusOverlayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraFocusOverlayTheme {
                CameraScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CameraFocusOverlayTheme {
        CameraScreen(modifier = Modifier.fillMaxSize())
    }
}
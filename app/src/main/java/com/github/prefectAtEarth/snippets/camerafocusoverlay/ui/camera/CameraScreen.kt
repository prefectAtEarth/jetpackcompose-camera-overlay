package com.github.prefectAtEarth.snippets.camerafocusoverlay.ui.camera

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.prefectAtEarth.snippets.camerafocusoverlay.ui.overlay.ScannerOverlayGraphic
import com.google.accompanist.permissions.rememberPermissionState


@Composable
fun CameraScreen(modifier: Modifier = Modifier) {

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (cameraPermissionState.hasPermission) {
        CameraView(modifier.fillMaxSize(), graphicOverlay = ScannerOverlayGraphic())
    } else {
        CameraPermissionScreen(cameraPermissionState = cameraPermissionState)
    }
}

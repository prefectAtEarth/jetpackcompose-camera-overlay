package com.github.prefectAtEarth.snippets.camerafocusoverlay.ui.camera

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.github.prefectAtEarth.snippets.camerafocusoverlay.ui.permissions.PermissionRequest
import com.google.accompanist.permissions.PermissionState


@Composable
fun CameraPermissionScreen(cameraPermissionState: PermissionState, ) {

    PermissionRequest(
        permissionState = cameraPermissionState,
        permissionNotGrantedContent = { AskCameraPermission(cameraPermissionState) },
        permissionNotAvailableContent = { CameraPermissionDenied() },
    )
}

@Composable
fun AskCameraPermission(cameraPermissionState: PermissionState) {
    Column {
        Text("This app needs access to your camera")
        Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
            Text(text = "Ask for camera permission")
        }
    }
}

@Composable
fun CameraPermissionDenied() {
    Column {
        Text(
            "Permission denied. Please, grant access on the Settings screen."
        )
    }
}
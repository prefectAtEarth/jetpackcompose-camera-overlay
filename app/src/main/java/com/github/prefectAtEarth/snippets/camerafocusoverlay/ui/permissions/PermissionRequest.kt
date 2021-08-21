package com.github.prefectAtEarth.snippets.camerafocusoverlay.ui.permissions

import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState

@Composable
fun PermissionRequest(
    permissionState: PermissionState,
    permissionNotGrantedContent: @Composable () -> Unit,
    permissionNotAvailableContent: @Composable () -> Unit,
) {

    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = { permissionNotGrantedContent() },
        permissionNotAvailableContent = { permissionNotAvailableContent() }
    ) {}
}
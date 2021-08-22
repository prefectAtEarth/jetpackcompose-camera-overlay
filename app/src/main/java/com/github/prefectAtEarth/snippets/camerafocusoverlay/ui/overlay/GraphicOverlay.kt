package com.github.prefectAtEarth.snippets.camerafocusoverlay.ui.overlay

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize


@Composable
fun GraphicOverlay(
    modifier: Modifier = Modifier,
    scannerOverlayGraphic: ScannerOverlayGraphic = ScannerOverlayGraphic()
) {

    var size by remember { mutableStateOf(Size.Zero) }

// calculating Dp to Float based on current resolution, so it always looks the same on each screen
    val density = LocalDensity.current
    val focusBorderStrokeWidth =
        with(density) { scannerOverlayGraphic.focusBorderStrokeWidth.toPx() }
    val focusBorderCornerRadius =
        with(density) { scannerOverlayGraphic.focusBorderCornerRadius.toPx() }
    val focusBorderStrokeLength =
        with(density) { scannerOverlayGraphic.focusBorderStrokeLength.toPx() }
    val focusBorderStrokeGap = with(density) { scannerOverlayGraphic.focusBorderStrokeGap.toPx() }
    val focusBorderWidth = with(density) { scannerOverlayGraphic.focusBorderWidth.toPx() }
    val focusBorderHeight = with(density) { scannerOverlayGraphic.focusBorderHeight.toPx() }


    val stroke = Stroke(
        width = focusBorderStrokeWidth,
        pathEffect = PathEffect.chainPathEffect(
            PathEffect.dashPathEffect(
                floatArrayOf(
                    focusBorderStrokeLength,
                    focusBorderStrokeGap
                ), focusBorderStrokeLength / 2f
            ),
            PathEffect.cornerPathEffect(focusBorderCornerRadius)
        ),
        cap = scannerOverlayGraphic.focusBorderStrokeCap
    )

    Box(
        modifier = modifier
            .onGloballyPositioned { layoutCoordinates ->
                size = layoutCoordinates.size.toSize()
            },
        contentAlignment = Alignment.Center
    ) {
        if(scannerOverlayGraphic.backgroundDimmed) {
            Canvas(modifier = modifier.fillMaxSize()) {
                drawPath(
                    drawDimmedBackgroundPath(
                        size,
                        Size(focusBorderWidth, focusBorderHeight),
                        focusBorderCornerRadius + (focusBorderStrokeWidth)
                    ),
                    color = Color(0x9a3a3a3a)
                )
            }
        }
        Canvas(
            modifier = Modifier
                .size(
                    scannerOverlayGraphic.focusBorderWidth,
                    scannerOverlayGraphic.focusBorderHeight
                )
        ) {
            drawRoundRect(color = scannerOverlayGraphic.focusBorderColor, style = stroke)
        }
    }
}


data class ScannerOverlayGraphic(
    val focusBorderColor: Color = Color.White,
    val focusBorderWidth: Dp = 250.dp,
    val focusBorderHeight: Dp = 250.dp,
    val focusBorderStrokeWidth: Dp = 4.dp,
    val focusBorderStrokeLength: Dp = 40.dp,
    val focusBorderStrokeGap: Dp = 8.dp,
    val focusBorderCornerRadius: Dp = 16.dp,
    val focusBorderStrokeCap: StrokeCap = StrokeCap.Butt,
    val backgroundDimmed: Boolean = true
)

private fun drawDimmedBackgroundPath(
    overAllSize: Size,
    cutoutSize: Size,
    cutoutCornerRadius: Float
): Path {
    val screenHeight = overAllSize.height
    val screenWidth = overAllSize.width
    val cutoutHeight = cutoutSize.height
    val cutoutWidth = cutoutSize.width

    return Path().apply {
        reset()
        // top left to top middle
        lineTo(screenWidth / 2, 0f)
        // top middle to middle top of CO
        lineTo(screenWidth / 2, (screenHeight - cutoutHeight) / 2)

        // middle top of CO to left top of CO
        lineTo(
            ((screenWidth - cutoutWidth) / 2) + cutoutCornerRadius,
            (screenHeight - cutoutHeight) / 2
        )
        // top left arc
        arcTo(
            rect = Rect(
                left = ((screenWidth - cutoutWidth) / 2),
                top = ((screenHeight - cutoutHeight) / 2),
                right = ((screenWidth - cutoutWidth) / 2) + cutoutCornerRadius,
                bottom = ((screenHeight - cutoutHeight) / 2) + cutoutCornerRadius
            ),
            startAngleDegrees = -90f,
            sweepAngleDegrees = -90f,
            forceMoveTo = false
        )
        // left top of CO to left bottom of CO
        lineTo(
            (screenWidth - cutoutWidth) / 2,
            (screenHeight - (screenHeight - cutoutHeight) / 2) - cutoutCornerRadius
        )
        // bottom left arc
        arcTo(
            rect = Rect(
                left = (screenWidth - cutoutWidth) / 2,
                top = (screenHeight - (screenHeight - cutoutHeight) / 2) - cutoutCornerRadius,
                right = ((screenWidth - cutoutWidth) / 2) + cutoutCornerRadius,
                bottom = (screenHeight - (screenHeight - cutoutHeight) / 2),
            ),
            startAngleDegrees = -180f,
            sweepAngleDegrees = -90f,
            forceMoveTo = false
        )
        // left bottom of CO to right bottom of CO
        lineTo(
            (screenWidth - ((screenWidth - cutoutWidth) / 2)) - cutoutCornerRadius,
            (screenHeight - ((screenHeight - cutoutHeight) / 2))
        )
        // right bottom arc
        arcTo(
            rect = Rect(
                left = (screenWidth - ((screenWidth - cutoutWidth) / 2)) - cutoutCornerRadius,
                top = (screenHeight - ((screenHeight - cutoutHeight) / 2)) - cutoutCornerRadius,
                right = (screenWidth - ((screenWidth - cutoutWidth) / 2)),
                bottom = (screenHeight - ((screenHeight - cutoutHeight) / 2))
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = -90f,
            forceMoveTo = false
        )
        // right bottom of CO to right top of CO
        lineTo(
            screenWidth - ((screenWidth - cutoutWidth) / 2),
            ((screenHeight - cutoutHeight) / 2) + cutoutCornerRadius
        )
        // top right arc
        arcTo(
            rect = Rect(
                left = (screenWidth - ((screenWidth - cutoutWidth) / 2)) - cutoutCornerRadius,
                top = (screenHeight - cutoutHeight) / 2,
                right = (screenWidth - ((screenWidth - cutoutWidth) / 2)),
                bottom = ((screenHeight - cutoutHeight) / 2) + cutoutCornerRadius
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = -90f,
            forceMoveTo = false
        )
        // right top of CO to top middle of CO
        lineTo(
            screenWidth / 2,
            (screenHeight - cutoutHeight) / 2
        )
        // top middle of CO to top middle
        lineTo(screenWidth / 2, 0f)
        // top middle to right top
        lineTo(screenWidth, 0f)
        // right top to right bottom
        lineTo(screenWidth, screenHeight)
        // right bottom to left bottom
        lineTo(0f, screenHeight)
        // left bottom to top left
        close()
    }
}

@Preview(showBackground = true)
@Composable
fun GraphicOverlayPreview() {
    GraphicOverlay(Modifier.fillMaxSize())
}
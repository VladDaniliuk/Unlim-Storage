package com.shov.coreui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.shov.coreui.ui.buttons.AnimatedIconButton
import com.shov.coreui.ui.texts.AnimatedText

@Composable
fun CustomTopAppBar(
    prevRouteImageVector: ImageVector?,
    onPrevRouteClick: () -> Unit,
    prevRouteEnabled: Boolean,
    title: String?,
    onTitleClick: (() -> Unit)? = null,
    nextRouteImageVector: ImageVector?,
    onNextRouteClick: () -> Unit,
    nextRouteEnabled: Boolean,
) {
    Box(
        modifier = Modifier.background(
            centerAlignedTopAppBarColors().containerColor(1f).value
        )
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier.statusBarsPadding(),
            title = {
                AnimatedText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            enabled = onTitleClick != null,
                            onClick = onTitleClick ?: {}
                        ),
                    text = title
                )
            },
            navigationIcon = {
                AnimatedIconButton(
                    imageVector = prevRouteImageVector,
                    onClick = onPrevRouteClick,
                    enabled = prevRouteEnabled,
                )
            },
            actions = {
                AnimatedIconButton(
                    imageVector = nextRouteImageVector,
                    onClick = onNextRouteClick,
                    enabled = nextRouteEnabled,
                )
            },
            colors = centerAlignedTopAppBarColors(
                containerColor = centerAlignedTopAppBarColors().containerColor(1f).value
            )
        )
    }
}

@Preview
@Composable
private fun CustomTopAppBarPreview() {
    CustomTopAppBar(
        prevRouteImageVector = Icons.Rounded.ArrowBack,
        onPrevRouteClick = {},
        prevRouteEnabled = true,
        title = "Title",
        nextRouteImageVector = Icons.Rounded.ArrowBack,
        onNextRouteClick = {},
        nextRouteEnabled = true,
    )
}

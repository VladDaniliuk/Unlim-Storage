package com.shov.coreui.views

import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.shov.coreui.ui.LocalHostState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScaffold(
	title: (@Composable () -> Unit)? = null,
	navigationIcon: (@Composable () -> Unit)? = null,
	actions: (@Composable RowScope.() -> Unit)? = null,
	content: @Composable (PaddingValues) -> Unit
) {
	val decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay()
	val scrollBehavior: TopAppBarScrollBehavior = remember(decayAnimationSpec) {
		TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
	}
	val hostState: SnackbarHostState = remember { SnackbarHostState() }

	Scaffold(
		modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
		topBar = {
			if ((title != null) or (actions != null) or (navigationIcon != null)) {
				Box(
					Modifier.background(
						TopAppBarDefaults.largeTopAppBarColors()
							.containerColor(scrollBehavior.scrollFraction).value
					)
				) {
					LargeTopAppBar(
						title = title ?: {},
						navigationIcon = navigationIcon ?: {},
						actions = actions ?: {},
						scrollBehavior = scrollBehavior,
						modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
					)
				}
			}
		},
		snackbarHost = {
			SnackbarHost(
				modifier = Modifier.navigationBarsPadding(),
				hostState = hostState
			)
		}
	) { paddingValues ->
		CompositionLocalProvider(LocalHostState provides hostState) {
			Surface(color = MaterialTheme.colorScheme.background) {
				content(paddingValues)
			}
		}
	}
}


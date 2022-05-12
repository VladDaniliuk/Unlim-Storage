package com.shov.coreui.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.shov.coreui.views.CustomScaffold

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterial3Api::class)
fun NavGraphBuilder.bottomSheetComposable(route: String, content: @Composable () -> Unit) =
	bottomSheet(route) {
		BottomSheetScaffold(content = content)
	}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
fun NavGraphBuilder.horizontalComposable(
	route: String,
	content: @Composable (PaddingValues) -> Unit
) = composable(
	route = route,
	enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left, tween(350)) },
	exitTransition = null,
	popEnterTransition = null,
	popExitTransition = {
		slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, tween(350))
	}
) {
	CustomScaffold(content = content)
}

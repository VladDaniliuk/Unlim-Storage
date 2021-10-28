package com.shov.unlimstorage

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.ProvideWindowInsets
import com.shov.unlimstorage.values.CustomTheme
import com.shov.unlimstorage.viewModels.singletonViewModel
import com.shov.unlimstorage.viewModels.updateViewModel
import com.shov.unlimstorage.views.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
@ExperimentalCoilApi
class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		WindowCompat.setDecorFitsSystemWindows(window, false)

		installSplashScreen()

		setContent {
			CustomTheme {
				ProvideWindowInsets {
					MainScreen(
						updateViewModel = updateViewModel(),
						downloadViewModel = singletonViewModel(),
						topAppBarViewModel = singletonViewModel()
					)
				}
			}
		}
	}
}

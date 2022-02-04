package com.shov.unlimstorage

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.shov.unlimstorage.ui.themes.CustomTheme
import com.shov.unlimstorage.views.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		WindowCompat.setDecorFitsSystemWindows(window, false)

		installSplashScreen()

		setContent {
			CustomTheme {
				ProvideWindowInsets {
					MainScreen()
				}
			}
		}
	}
}

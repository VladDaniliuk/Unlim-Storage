package com.shov.dropboxstorage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shov.dropboxstorage.views.DropBoxAuthScreen

class DropBoxAuthActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			DropBoxAuthScreen()
		}
	}
}

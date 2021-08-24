package com.shov.unlimstorage.views

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.TextNavigation
import com.shov.unlimstorage.values.navAccounts

@Composable
fun MainFragment(navController: NavController) {
	if (true) {//check if has no files to show
		Box(modifier = Modifier.fillMaxSize()) {
			TextNavigation(
				stringIdArray = arrayOf(
					R.string.nothing_to_show,
					R.string.settings,
					R.string.dot
				),
				clickableStringIndex = 1,
				direction = navAccounts,
				navController = navController,
				modifier = Modifier.align(Alignment.Center)
			)
		}
	}
}

@Preview
@Composable
fun MainFragmentPreview() {
	MainFragment(navController = rememberNavController())
}

package com.shov.unlimstorage.utils

import androidx.navigation.NavHostController

fun NavHostController.popBackStack(size: Int) {
	for (i in 0 until size) popBackStack()
}

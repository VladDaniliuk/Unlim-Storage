package com.shov.storagerepositories.utils

import java.io.File

internal fun File.createFile(
	onExist: File.() -> Unit = {},
	onCreate: File.() -> Unit = {},
	onError: (() -> Unit)? = null
) {
	when {
		createNewFile() -> onCreate()
		exists() -> onExist()
		else -> onError?.invoke() ?: throw IllegalAccessError()
	}
}

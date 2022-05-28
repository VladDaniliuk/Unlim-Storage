package com.shov.unlimstorage.utils.files

import java.io.File

fun File.createFile(
	name: String,
	onExist: File.() -> Unit = {},
	onCreate: File.() -> Unit = {},
	onError: (() -> Unit)? = null
) {
	File(this, name).let { file ->
		when {
			file.createNewFile() -> file.onCreate()
			file.exists() -> file.onExist()
			else -> onError?.invoke() ?: throw IllegalAccessError()
		}
	}
}

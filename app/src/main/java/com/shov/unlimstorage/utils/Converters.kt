package com.shov.unlimstorage.utils

fun Long.fromLongToString(convertType: Int = 0): String {
	val typeArray = arrayOf("Bytes", "KB", "MB", "GB", "TB")
	var result = "$this ${typeArray[convertType]}"
	if (typeArray.lastIndex != convertType) {
		(this / 1024).let { newSize ->
			if (newSize > 1) {
				result = (newSize.fromLongToString(convertType + 1))
			}
		}
	}
	return result
}

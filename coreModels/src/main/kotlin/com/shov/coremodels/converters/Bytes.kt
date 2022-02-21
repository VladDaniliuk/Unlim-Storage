package com.shov.coremodels.converters

import com.shov.coremodels.R

fun Long.toBytes(convertType: Int = 0): Pair<String, Int> {
	val typeList = listOf(
		R.string.bytes,
		R.string.kilobytes,
		R.string.megabytes,
		R.string.gigabytes,
		R.string.terabytes
	)

	return if ((convertType != typeList.lastIndex) and (this > 1024)) {
		(this / 1024).toBytes(convertType + 1)
	} else toString() to typeList[convertType]
}

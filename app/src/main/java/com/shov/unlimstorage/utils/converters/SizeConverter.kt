package com.shov.unlimstorage.utils.converters

import android.content.Context
import com.shov.unlimstorage.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface SizeConverter {
	fun Long.toBytes(convertType: Int = 0): String
}

class SizeConverterImpl @Inject constructor(
	@ApplicationContext val context: Context
) : SizeConverter {
	override fun Long.toBytes(convertType: Int): String {
		val typeList = listOf(
			context.getString(R.string.bytes),
			context.getString(R.string.kilobytes),
			context.getString(R.string.megabytes),
			context.getString(R.string.gigabytes),
			context.getString(R.string.terabytes)
		)

		var result = context.getString(
			R.string.size,
			this.toString(),
			typeList[convertType]
		)

		if (typeList.lastIndex != convertType) {
			(this / 1024).let { newSize ->
				if (newSize > 1) {
					result = newSize.toBytes(convertType + 1)
				}
			}
		}

		return result
	}
}

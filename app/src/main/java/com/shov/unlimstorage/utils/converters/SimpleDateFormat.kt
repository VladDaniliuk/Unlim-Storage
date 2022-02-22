package com.shov.unlimstorage.utils.converters

import android.icu.text.SimpleDateFormat
import com.shov.unlimstorage.values.DEFAULT_DATE_FORMAT
import com.shov.unlimstorage.values.GOOGLE_DATE_FORMAT
import java.util.*

fun Date.toPrettyTime(
	pattern: String = DEFAULT_DATE_FORMAT,
	locale: Locale = Locale.getDefault()
): String = SimpleDateFormat(pattern, locale).format(this)

fun String.toPrettyTime(
	incomeFormat: String = GOOGLE_DATE_FORMAT,
	pattern: String = DEFAULT_DATE_FORMAT,
	locale: Locale = Locale.getDefault()
): String =
	SimpleDateFormat(pattern, locale).format(SimpleDateFormat(incomeFormat, locale).parse(this))

package com.shov.autoupdatefeature.utils

import android.icu.text.SimpleDateFormat
import java.util.*

fun String.toPrettyTime(
	incomeFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'",
	pattern: String = "yyyy MMMM d HH:mm",
	locale: Locale = Locale.getDefault()
): String =
	SimpleDateFormat(pattern, locale).format(SimpleDateFormat(incomeFormat, locale).parse(this))

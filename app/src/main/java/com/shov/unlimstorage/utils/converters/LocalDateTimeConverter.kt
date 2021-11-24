package com.shov.unlimstorage.utils.converters

import android.os.Build
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import java.time.format.TextStyle
import java.util.*

fun LocalDateTime.toPrettyString() = "${this.dayOfMonth} ${this.month.toPrettyString()}, " +
		"${this.year}, ${this.hour.toTime()}:${this.minute.toTime()}"

fun Month.toPrettyString(): String {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
		this.getDisplayName(TextStyle.FULL, Locale.getDefault())
	} else {
		this.name
	}
}

fun Int.toTime() = "${if (this < 10) 0 else ""}$this"

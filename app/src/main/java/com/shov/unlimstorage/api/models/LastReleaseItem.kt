package com.shov.unlimstorage.api.models

import kotlinx.datetime.LocalDateTime

data class LastReleaseItem(
	val version: String,
	val releaseName: String,
	val applicationName: String,
	val applicationSize: Long,
	val releaseDate: LocalDateTime,
	val downloadUrl: String
)

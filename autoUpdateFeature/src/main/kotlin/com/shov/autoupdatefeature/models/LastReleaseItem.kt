package com.shov.autoupdatefeature.models

data class LastReleaseItem(
	val version: String,
	val releaseName: String,
	val applicationName: String,
	val applicationSize: Long,
	val releaseDate: String,
	val downloadUrl: String
)

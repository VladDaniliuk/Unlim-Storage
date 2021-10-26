package com.shov.unlimstorage.utils.converters

import com.google.gson.JsonElement
import com.shov.unlimstorage.api.models.LastReleaseItem
import com.shov.unlimstorage.values.LastRelease
import kotlinx.datetime.LocalDateTime


class LastReleaseConverter {
	fun JsonElement.toLastReleaseItem(): LastReleaseItem = LastReleaseItem(
		version = getString(LastRelease.tagName),
		releaseName = getString(LastRelease.name),
		applicationName = getArrayedObject(LastRelease.assets).getString(LastRelease.name),
		applicationSize = getArrayedObject(LastRelease.assets).getLong(LastRelease.size),
		releaseDate = getLocalDateTime(LastRelease.publishedAt),
		downloadUrl = getArrayedObject(LastRelease.assets).getString(LastRelease.browserDownloadUrl)
	)

	private fun JsonElement.getString(key: String) = this.asJsonObject.get(key).asString
	private fun JsonElement.getLong(key: String) = this.asJsonObject.get(key).asLong
	private fun JsonElement.getArrayedObject(key: String) =
		this.asJsonObject.get(key).asJsonArray.get(0).asJsonObject

	private fun JsonElement.getLocalDateTime(key: String) =
		LocalDateTime.parse(getString(key).replace("Z", ""))
}

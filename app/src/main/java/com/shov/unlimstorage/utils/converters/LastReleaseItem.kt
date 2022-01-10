package com.shov.unlimstorage.utils.converters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.shov.unlimstorage.api.models.LastReleaseItem
import com.shov.unlimstorage.values.LastRelease
import kotlinx.datetime.LocalDateTime
import java.lang.reflect.Type

class LastReleaseDeserializer : JsonDeserializer<LastReleaseItem> {
	override fun deserialize(
		json: JsonElement,
		typeOfT: Type?,
		context: JsonDeserializationContext?
	): LastReleaseItem {
		return LastReleaseItem(
			version = json.getString(LastRelease.tagName),
			releaseName = json.getString(LastRelease.name),
			applicationName = json.getArrayedObject(LastRelease.assets).getString(LastRelease.name),
			applicationSize = json.getArrayedObject(LastRelease.assets).getLong(LastRelease.size),
			releaseDate = json.getLocalDateTime(LastRelease.publishedAt),
			downloadUrl = json.getArrayedObject(LastRelease.assets)
				.getString(LastRelease.browserDownloadUrl)
		)
	}
}

private fun JsonElement.getString(key: String) = this.asJsonObject.get(key).asString
private fun JsonElement.getLong(key: String) = this.asJsonObject.get(key).asLong
private fun JsonElement.getArrayedObject(key: String) =
	this.asJsonObject.get(key).asJsonArray.get(0).asJsonObject

private fun JsonElement.getLocalDateTime(key: String) =
	LocalDateTime.parse(getString(key).replace("Z", ""))
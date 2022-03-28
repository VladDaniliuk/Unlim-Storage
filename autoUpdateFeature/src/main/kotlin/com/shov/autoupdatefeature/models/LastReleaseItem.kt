package com.shov.autoupdatefeature.models

import com.google.gson.annotations.SerializedName

data class LastReleaseItem(
	@SerializedName("html_url") val url: String,//github website of release
	@SerializedName("author") val author: Author,// author of release
	@SerializedName("tag_name") val tagName: String,// tag of release. Used like apk release version
	@SerializedName("name") val name: String,
	@SerializedName("created_at") val createdAt: String,
	@SerializedName("published_at") val publishedAd: String,
	@SerializedName("assets") val assets: List<Asset>,// files, which attached to the release
	@SerializedName("body") val body: String// Text (release notes)
)

data class Author(
	@SerializedName("login") val login: String,
	@SerializedName("avatar_url") val avatarUrl: String,// link to author's avatar
	@SerializedName("html_url") val htmlUrl: String,// github website of author
)

data class Asset(
	@SerializedName("name") val name: String,//file name
	@SerializedName("label") val label: String,
	@SerializedName("uploader") val uploader: Author,//author of uploaded file
	@SerializedName("content_type") val contentType: String,//type of file ('application/vnd.android.package-archive' for android application)
	@SerializedName("state") val state: String,
	@SerializedName("size") val size: Long,//size in bytes
	@SerializedName("download_count") val downloadCount: Long,//counts of downloads
	@SerializedName("created_at") val createdAt: String,
	@SerializedName("updated_at") val updatedAt: String,
	@SerializedName("browser_downloaded_url") val browserDownloadedUrl: String// link for downloading file
)

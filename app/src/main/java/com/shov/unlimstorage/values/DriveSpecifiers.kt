package com.shov.unlimstorage.values

import com.box.androidsdk.content.requests.BoxRequestsFolder

const val DROPBOX_ROOT_FOLDER = ""
const val DROPBOX_CLIENT_IDENTIFIER = "Dbx_US/0.1.0"

const val GOOGLE_FIELDS = "files(id,size,name,mimeType,parents)"
const val GOOGLE_METADATA = "id,name,mimeType,description,permissions,shared,starred,version," +
		"webViewLink,createdTime,modifiedTime,owners,size"

fun getGoogleQ(folderId: String? = null) = "parents = '${folderId ?: "root"}' and trashed = false"

fun getBoxFields() = arrayOf(
	"name",
	"description",
	"collections",
	"shared_link",
	"created_at",
	"created_by",
	"modified_at",
	"size"
)

fun BoxRequestsFolder.GetFolderItems.setItemFields(): BoxRequestsFolder.GetFolderItems =
	this.setFields("size", "name", "parent")

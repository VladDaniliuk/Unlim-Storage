package com.shov.unlimstorage.values

const val SIZE = "size"
const val NAME = "name"
const val PARENT = "parent"

const val DROPBOX_ROOT_FOLDER = ""

const val GOOGLE_FIELDS = "files(id,size,name,mimeType,parents)"

fun getGoogleQ(folderId: String?) = "parents = '${folderId ?: "root"}' and trashed = false"

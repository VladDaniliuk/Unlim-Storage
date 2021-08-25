package com.shov.unlimstorage.values

const val ARRAY_INDEX_EXCEPTION = "ClickableStringIndex is more than stringArray size"

const val ARGUMENT_SIGN_IN = "SignIn"
const val ARGUMENT_FILES = "Files"
const val ARGUMENT_METADATA = "FolderMetadata or FileMetadata"
const val ARGUMENT_ANY = "Any"

fun argumentException(sampleClass: String, type: String) =
	IllegalArgumentException("Unknown $sampleClass class $type")

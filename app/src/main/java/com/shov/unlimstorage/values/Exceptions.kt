package com.shov.unlimstorage.values

//Annotation
const val UNCHECKED_CAST = "UNCHECKED_CAST"
const val UNUSED_VALUE = "UNUSED_VALUE"
const val NEVER_ACCESSED = "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE"

//Arguments
const val ARGUMENT_SIGN_IN = "SignIn"
const val ARGUMENT_FILES = "Files"
const val ARGUMENT_METADATA = "FolderMetadata or FileMetadata"
const val ARGUMENT_ANY = "Any"

//Exceptions
const val ARRAY_INDEX_EXCEPTION = "ClickableStringIndex is more than stringArray size"
fun argumentException(sampleClass: String, type: String) =
	IllegalArgumentException("Unknown $sampleClass class $type")

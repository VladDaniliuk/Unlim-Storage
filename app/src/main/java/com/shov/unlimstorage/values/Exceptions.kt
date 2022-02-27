package com.shov.unlimstorage.values

//Annotation
const val UNCHECKED_CAST = "UNCHECKED_CAST"

//Arguments
const val ARGUMENT_SIGN_IN = "SignIn"
const val ARGUMENT_FILES = "Files"

//Exceptions
class UnknownClassInheritance(sampleClass: String, type: String) :
	Exception("Unknown $sampleClass class $type")

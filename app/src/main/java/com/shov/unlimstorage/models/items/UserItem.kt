package com.shov.unlimstorage.models.items

data class User(
	val email: String?,
	val role: String,
	val name: String?,
	val photoLink: String? = null
)

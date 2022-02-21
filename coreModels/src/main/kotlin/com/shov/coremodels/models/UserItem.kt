package com.shov.coremodels.models

data class UserItem(
	val email: String?,
	val role: String,
	val name: String?,
	val photoLink: String? = null
)

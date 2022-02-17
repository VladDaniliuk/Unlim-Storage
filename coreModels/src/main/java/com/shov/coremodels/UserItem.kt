package com.shov.coremodels

data class UserItem(
	val email: String?,
	val role: String,
	val name: String?,
	val photoLink: String? = null
)

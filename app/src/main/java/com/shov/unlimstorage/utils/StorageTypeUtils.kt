package com.shov.unlimstorage.utils

import com.shov.unlimstorage.models.repositories.signIn.StorageType

fun <T> reduce(operation: (StorageType) -> List<T>?): List<T> {
	val list = mutableListOf<T>()

	StorageType.values().forEach {
		operation(it)?.let(list::addAll)
	}

	return list
}

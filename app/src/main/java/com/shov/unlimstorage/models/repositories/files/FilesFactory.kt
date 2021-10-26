package com.shov.unlimstorage.models.repositories.files

import com.shov.unlimstorage.models.repositories.signIn.StorageType
import com.shov.unlimstorage.values.ARGUMENT_FILES
import com.shov.unlimstorage.values.UnknownClassInheritance
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

interface Factory {
	fun create(type: StorageType): FilesInteractor
}

@Singleton
class FilesFactory @Inject constructor(
	private val storageTypeMap: Map<StorageType,
			@JvmSuppressWildcards Provider<FilesInteractor>>
) : Factory {
	override fun create(type: StorageType): FilesInteractor {
		val filesProvider = storageTypeMap[type]
			?: throw UnknownClassInheritance(ARGUMENT_FILES, type.name)
		return filesProvider.get()
	}
}

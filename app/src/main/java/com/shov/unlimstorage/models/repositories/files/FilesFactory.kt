package com.shov.unlimstorage.models.repositories.files

import com.shov.coremodels.models.StorageType
import com.shov.storage.FilesDataSource
import com.shov.unlimstorage.values.ARGUMENT_FILES
import com.shov.unlimstorage.values.UnknownClassInheritance
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

interface Factory {
	fun create(type: StorageType): FilesDataSource
}

@Singleton
class FilesFactory @Inject constructor(
	private val storageTypeMap: Map<StorageType,
			@JvmSuppressWildcards Provider<FilesDataSource>>
) : Factory {
	override fun create(type: StorageType): FilesDataSource {
		val filesProvider = storageTypeMap[type]
			?: throw UnknownClassInheritance(ARGUMENT_FILES, type.name)
		return filesProvider.get()
	}
}

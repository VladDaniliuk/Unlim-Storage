package com.shov.unlimstorage.models.filesRepository

import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.values.ARGUMENT_FILES
import com.shov.unlimstorage.values.argumentException
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
			?: throw argumentException(ARGUMENT_FILES, type.name)
		return filesProvider.get()
	}
}

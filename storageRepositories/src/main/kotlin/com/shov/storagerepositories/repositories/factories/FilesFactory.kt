package com.shov.storagerepositories.repositories.factories

import com.shov.coremodels.models.StorageType
import com.shov.storage.FilesDataSource
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

internal interface FilesFactoryInterface {
	fun create(type: StorageType): FilesDataSource
}

@Singleton
class FilesFactory @Inject constructor(
	private val storageTypeMap: Map<StorageType,
			@JvmSuppressWildcards Provider<FilesDataSource>>
) : FilesFactoryInterface {
	override fun create(type: StorageType): FilesDataSource {
		val filesProvider = storageTypeMap[type]
			?: throw Exception("Unknown FilesDataSource class $type")
		return filesProvider.get()
	}
}

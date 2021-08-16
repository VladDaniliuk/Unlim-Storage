package com.shov.unlimstorage.models.filesRepository

import com.shov.unlimstorage.models.signInModels.SignInType
import com.shov.unlimstorage.values.ARGUMENT_FILES
import com.shov.unlimstorage.values.argumentException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

interface Factory {
	fun create(type: SignInType): FilesSample
}

@Singleton
class FilesFactory @Inject constructor(
	private val signInTypeMap: Map<SignInType,
			@JvmSuppressWildcards Provider<FilesSample>>
) : Factory {
	override fun create(type: SignInType): FilesSample {
		val filesProvider = signInTypeMap[type]
			?: throw argumentException(ARGUMENT_FILES, type.name)
		return filesProvider.get()
	}
}

package com.shov.unlimstorage.viewModels.files

import androidx.lifecycle.ViewModel
import com.shov.coremodels.models.StorageType
import com.shov.storagerepositories.repositories.signIn.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ChooseDriveViewModel @Inject constructor(
	private val signInRepository: SignInRepository
) : ViewModel() {
	fun checkAccess() = StorageType.values().filter(signInRepository::isSuccess)
}

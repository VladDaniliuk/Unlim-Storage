package com.shov.signinfeature.viewModels

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import com.shov.coremodels.models.StorageType
import com.shov.storagerepositories.repositories.signIn.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
	private val signInRepository: SignInRepository
) : ViewModel() {
	var storageTypes: List<StorageType>
		private set

	fun checkAccessWithResult(result: ActivityResult, storageType: StorageType) {
		signInRepository.isSuccess(storageType, result)
	}

	fun getAccess(storageType: StorageType): Intent = signInRepository.signIn(storageType)

	init {
		storageTypes = StorageType.values().filterNot(signInRepository::isSuccess)
	}
}
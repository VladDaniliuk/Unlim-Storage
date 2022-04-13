package com.shov.signinfeature.viewModels

import androidx.lifecycle.ViewModel
import com.shov.coremodels.models.StorageType
import com.shov.storagerepositories.repositories.signIn.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
	private val signInRepository: SignInRepository
) : ViewModel() {
	var storageTypes: List<StorageType>
		private set
	val isAddAccountAvailable: Boolean
		get() = StorageType.values().toList().let(storageTypes::containsAll).not()

	init {
		storageTypes = StorageType.values().filter(signInRepository::isSuccess)
	}
}

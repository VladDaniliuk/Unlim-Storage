package com.shov.signinfeature.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.coremodels.models.StorageType
import com.shov.coreutils.values.argStorageType
import com.shov.storagerepositories.repositories.signIn.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RevokeAccountViewModel @Inject constructor(
	private val signInRepository: SignInRepository,
	savedStateHandle: SavedStateHandle
) : ViewModel() {
	var storageType: StorageType

	fun signOut(onRevoked: () -> Unit) {
		storageType.let { storageType ->
			viewModelScope.launch {
				signInRepository.signOut(storageType)
			}.invokeOnCompletion {
				onRevoked()
			}
		}
	}

	init {
		storageType = StorageType.valueOf(savedStateHandle.get<String>(argStorageType)!!)
	}
}

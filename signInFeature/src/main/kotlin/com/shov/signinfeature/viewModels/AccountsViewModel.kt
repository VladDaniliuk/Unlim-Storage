package com.shov.signinfeature.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.coremodels.models.StorageType
import com.shov.storagerepositories.repositories.signIn.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
	private val signInRepository: SignInRepository
) : ViewModel() {
	var showRevokeDialog by mutableStateOf<StorageType?>(null)
		private set
	var isShowAddAccountBottomSheet by mutableStateOf(false)
		private set

	fun showRevokeDialog(storageType: StorageType? = null) {
		showRevokeDialog = storageType
	}

	fun showAddAccountBottomSheet(isShow: Boolean = false) {
		isShowAddAccountBottomSheet = isShow
	}

	fun checkAllAccess(isSuccess: Boolean = true) = StorageType.values()
		.filter { storageType -> signInRepository.isSuccess(storageType) == isSuccess }

	fun signOut(storageType: StorageType) {
		viewModelScope.launch(Dispatchers.IO) {
			signInRepository.signOut(storageType)
		}.invokeOnCompletion {
			showRevokeDialog()
		}
	}
}

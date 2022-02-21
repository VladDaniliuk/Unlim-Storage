package com.shov.unlimstorage.viewModels.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shov.coremodels.models.StorageType
import com.shov.unlimstorage.models.repositories.signIn.AuthorizerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
	private val authorizerFactory: AuthorizerFactory
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

	fun checkAccess(storageType: StorageType): Boolean =
		authorizerFactory.create(storageType).isSuccess()

	fun checkAllAccess(isSuccess: Boolean = true) = StorageType.values()
		.filter { type -> authorizerFactory.create(type).isSuccess() == isSuccess }

	fun signOut(storageType: StorageType) {
		viewModelScope.launch(Dispatchers.IO) {
			authorizerFactory.create(storageType).signOut()
		}.invokeOnCompletion {
			showRevokeDialog()
		}
	}
}

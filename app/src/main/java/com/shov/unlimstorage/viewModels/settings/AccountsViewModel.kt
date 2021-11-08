package com.shov.unlimstorage.viewModels.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.repositories.signIn.AuthorizerFactory
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
	private val authorizerFactory: AuthorizerFactory
) : ViewModel() {
	var showRevokeDialog by mutableStateOf<StorageType?>(null)
		private set
	var isShowAddAccountBottomSheet by mutableStateOf<Boolean?>(null)
		private set
	var isAllSignedIn by mutableStateOf(true)
		private set

	fun showRevokeDialog(storageType: StorageType? = null) {
		showRevokeDialog = storageType
	}


	fun showAddAccountBottomSheet(isShow: Boolean? = null) {
		isShowAddAccountBottomSheet = isShow
	}

	fun setIsAllSignedIn(isAllSignedIn: Boolean) {
		this.isAllSignedIn = isAllSignedIn
	}

	fun checkAccess(storageType: StorageType): Boolean =
		authorizerFactory.create(storageType).isSuccess()

	fun signOut(storageType: StorageType): Boolean {
		return authorizerFactory.create(storageType).signOut()
	}
}

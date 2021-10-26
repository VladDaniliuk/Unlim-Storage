package com.shov.unlimstorage.viewModels

import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.repositories.signIn.AuthorizerFactory
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
	private val authorizerFactory: AuthorizerFactory
) : ViewModel() {
	/**Dialog: revoke access to account*/
	private val _showRevokeDialog = MutableStateFlow<StorageType?>(null)
	val showRevokeDialog = _showRevokeDialog.asStateFlow()
	fun setShowRevokeDialog(storageType: StorageType?) {
		_showRevokeDialog.value = storageType
	}

	/**Dialog: add account*/
	private val _showAddAccountBottomSheet = MutableStateFlow<Boolean?>(null)
	val showAddAccountBottomSheet = _showAddAccountBottomSheet.asStateFlow()
	fun setShowAddAccountBottomSheet(isShow: Boolean?) {
		_showAddAccountBottomSheet.value = isShow
	}

	private val _isAllSignedIn = MutableStateFlow(true)
	val isAllSignedIn = _isAllSignedIn.asStateFlow()

	fun setAllSignedIn(isAllSignedIn: Boolean) {
		_isAllSignedIn.value = isAllSignedIn
	}

	fun checkAccess(storageType: StorageType): Boolean =
		authorizerFactory.create(storageType).isSuccess()

	fun signOut(storageType: StorageType): Boolean {
		return authorizerFactory.create(storageType).signOut()
	}
}

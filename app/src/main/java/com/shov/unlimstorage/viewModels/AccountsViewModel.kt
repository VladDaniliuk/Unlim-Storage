package com.shov.unlimstorage.viewModels

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
	/**Dialog: revoke access to account*/
	private var _showRevokeDialog by mutableStateOf<StorageType?>(null)
	val showRevokeDialog get() = _showRevokeDialog
	fun setShowRevokeDialog(storageType: StorageType?) {
		_showRevokeDialog = storageType
	}

	/**Dialog: add account*/
	private var _showAddAccountBottomSheet by mutableStateOf<Boolean?>(null)
	val showAddAccountBottomSheet get() = _showAddAccountBottomSheet
	fun setShowAddAccountBottomSheet(isShow: Boolean?) {
		_showAddAccountBottomSheet = isShow
	}

	private var _isAllSignedIn by mutableStateOf(true)
	val isAllSignedIn get() = _isAllSignedIn

	fun setAllSignedIn(isAllSignedIn: Boolean) {
		_isAllSignedIn = isAllSignedIn
	}

	fun checkAccess(storageType: StorageType): Boolean =
		authorizerFactory.create(storageType).isSuccess()

	fun signOut(storageType: StorageType): Boolean {
		return authorizerFactory.create(storageType).signOut()
	}
}

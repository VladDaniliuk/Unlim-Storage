package com.shov.unlimstorage.viewModels

import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.SignInButtonInfo
import com.shov.unlimstorage.models.signInModels.Authorizer
import com.shov.unlimstorage.models.signInModels.AuthorizerFactory
import com.shov.unlimstorage.models.signInModels.StorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
	private val authorizerFactory: AuthorizerFactory
) : ViewModel() {
	/**Dialog: revoke access to account*/
	private val _showRevokeDialog = MutableStateFlow<SignInButtonInfo?>(null)
	val showRevokeDialog = _showRevokeDialog.asStateFlow()
	fun setShowRevokeDialog(signInButtonInfo: SignInButtonInfo?) {
		_showRevokeDialog.value = signInButtonInfo
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
		authorizerFactory.create<Authorizer>(storageType).isSuccess()

	fun signOut(storageType: StorageType?): Boolean =
		storageType?.let { authorizerFactory.create<Authorizer>(storageType).signOut() } ?: false
}

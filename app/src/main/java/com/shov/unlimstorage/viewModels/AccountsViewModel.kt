package com.shov.unlimstorage.viewModels

import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.SignInButtonInfo
import com.shov.unlimstorage.models.signInModels.SignInFactory
import com.shov.unlimstorage.models.signInModels.SignInType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
	private val signInFactory: SignInFactory
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

	fun checkAccess(signInType: SignInType): Boolean =
		signInFactory.create(signInType).isSuccess()

	fun signOut(signInType: SignInType?): Boolean =
		signInType?.let { signInFactory.create(signInType).signOut() } ?: false
}

package com.shov.unlimstorage.viewModels

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.SignInButtonInfo
import com.shov.unlimstorage.models.signInModels.SignInFactory
import com.shov.unlimstorage.models.signInModels.SignInSample
import com.shov.unlimstorage.models.signInModels.SignInType
import com.shov.unlimstorage.repositories.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
	private val signInFactory: SignInFactory,
	private val signInRepository: SignInRepository
) : ViewModel() {
	private val _serviceAccess = MutableStateFlow(false)
	val serviceAccess = _serviceAccess.asStateFlow()

	private val _showDialog = MutableStateFlow<Pair<Boolean, SignInButtonInfo?>>(Pair(false, null))
	val showDialog = _showDialog.asStateFlow()

	fun setShowDialog(isShow: Boolean, signInButtonInfo: SignInButtonInfo?) {
		_showDialog.value = Pair(isShow, signInButtonInfo)
	}

	val checkAccessWithResult: (result: ActivityResult, signInType: SignInType) -> Unit =
		{ activityResult: ActivityResult, signInType: SignInType ->
			signInFactory.create<SignInSample>(signInType).isSuccess(activityResult).apply {
				signInRepository.setLogIn(this)
				_serviceAccess.value = this
			}
		}

	fun checkAccess(signInType: SignInType): Boolean =
		signInFactory.create<SignInSample>(signInType).isSuccess()

	val getAccess: (
		startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
		signInClass: SignInType
	) -> Unit = { signInForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
	              signInType: SignInType ->
		signInFactory.create<SignInSample>(signInType).signIn(signInForResult)
	}

	fun signOut(signInType: SignInType?): Boolean =
		signInType?.let { signInFactory.create<SignInSample>(signInType).signOut()}?: false
}

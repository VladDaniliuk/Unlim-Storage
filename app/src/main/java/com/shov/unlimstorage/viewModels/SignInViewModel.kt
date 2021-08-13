package com.shov.unlimstorage.viewModels

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
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

	val checkAccessWithResult: (result: ActivityResult, signInType: SignInType) -> Unit =
		{ activityResult: ActivityResult, signInType: SignInType ->
			signInFactory.create<SignInSample>(signInType).isSuccess(activityResult).apply {
				signInRepository.setLogIn(this)
				_serviceAccess.value = this
			}
		}

	val getAccess: (
		startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
		signInClass: SignInType
	) -> Unit = { signInForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
	              signInType: SignInType ->
		signInFactory.create<SignInSample>(signInType).signIn(signInForResult)
	}
}

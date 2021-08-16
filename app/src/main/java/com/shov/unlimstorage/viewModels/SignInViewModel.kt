package com.shov.unlimstorage.viewModels

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.signInModels.Authorizer
import com.shov.unlimstorage.models.signInModels.AuthorizerFactory
import com.shov.unlimstorage.models.signInModels.StorageType
import com.shov.unlimstorage.repositories.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
	private val authorizerFactory: AuthorizerFactory,
	private val signInRepository: SignInRepository
) : ViewModel() {
	private val _serviceAccess = MutableStateFlow(false)
	val serviceAccess = _serviceAccess.asStateFlow()

	val isLogIn: Boolean
		get() = signInRepository.isLogIn

	val checkAccess: (result: ActivityResult, storageType: StorageType) -> Unit =
		{ activityResult: ActivityResult, storageType: StorageType ->
			authorizerFactory.create<Authorizer>(storageType).isSuccess(activityResult).apply {
				signInRepository.setLogIn(this)
				_serviceAccess.value = this
			}
		}

	val getAccess: (
		startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
		storageClass: StorageType
	) -> Unit = { signInForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
	              storageType: StorageType ->
		authorizerFactory.create<Authorizer>(storageType).signIn(signInForResult)
	}
}

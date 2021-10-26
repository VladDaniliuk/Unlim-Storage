package com.shov.unlimstorage.viewModels

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.repositories.signIn.AuthorizerFactory
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
	private val authorizerFactory: AuthorizerFactory
) : ViewModel() {
	private val _serviceAccess = MutableStateFlow(false)
	val serviceAccess = _serviceAccess.asStateFlow()

	val checkAccessWithResult: (result: ActivityResult, storageType: StorageType) -> Unit =
		{ activityResult: ActivityResult, storageType: StorageType ->
			authorizerFactory.create(storageType).isSuccess(activityResult).apply {
				_serviceAccess.value = this
			}
		}

	val getAccess: (
		startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
		storageType: StorageType
	) -> Unit = { signInForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
	              storageType: StorageType ->
		authorizerFactory.create(storageType).signIn(signInForResult)
	}
}

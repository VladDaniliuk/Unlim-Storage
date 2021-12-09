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

	fun checkAccessWithResult(result: ActivityResult, storageType: StorageType) {
		authorizerFactory.create(storageType).isSuccess(result).apply {
			_serviceAccess.value = this
		}
	}

	fun getAccess(
		startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
		storageType: StorageType
	) {
		authorizerFactory.create(storageType).signIn(startForResult)
	}
}

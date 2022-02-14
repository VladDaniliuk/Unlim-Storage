package com.shov.unlimstorage.viewModels

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.repositories.signIn.AuthorizerFactory
import com.shov.unlimstorage.models.repositories.signIn.StorageType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
	private val authorizerFactory: AuthorizerFactory
) : ViewModel() {
	var serviceAccess by mutableStateOf(false)
		private set

	fun singIn(onSignIn: () -> Unit) {
		if (serviceAccess) onSignIn()
	}

	fun checkAccessWithResult(result: ActivityResult, storageType: StorageType) {
		authorizerFactory.create(storageType).isSuccess(result).also { access ->
			serviceAccess = access
		}
	}

	fun getAccess(
		startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
		storageType: StorageType
	) {
		authorizerFactory.create(storageType).signIn(startForResult)
	}
}

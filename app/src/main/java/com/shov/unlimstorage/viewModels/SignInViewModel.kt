package com.shov.unlimstorage.viewModels

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shov.coremodels.models.StorageType
import com.shov.unlimstorage.models.repositories.signIn.AuthorizerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import com.shov.unlimstorage.models.repositories.PreferenceRepository
import com.shov.unlimstorage.values.IS_AUTH
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
	private val authorizerFactory: AuthorizerFactory,
	preferences: PreferenceRepository
) : ViewModel() {
	var serviceAccess by mutableStateOf(false)
		private set
	var isAuth by preferences.getPref(IS_AUTH, false)
		private set

	fun singIn(onSignIn: () -> Unit) {
		if (serviceAccess) {
			isAuth = true
			onSignIn()
		}
	}

	fun onDropBoxSignIn() {
		serviceAccess = true
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

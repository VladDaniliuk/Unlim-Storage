package com.shov.unlimstorage.viewModels

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shov.coremodels.models.StorageType
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.unlimstorage.models.repositories.signIn.AuthorizerFactory
import com.shov.unlimstorage.values.IS_AUTH
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
	private val authorizerFactory: AuthorizerFactory,
	preferences: PreferencesDataSource
) : ViewModel() {
	var serviceAccess by mutableStateOf(false)
		private set
	private var isAuth by preferences.getPref(IS_AUTH, false)

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

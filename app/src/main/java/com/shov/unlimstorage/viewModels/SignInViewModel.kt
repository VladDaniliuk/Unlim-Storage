package com.shov.unlimstorage.viewModels

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shov.coremodels.models.StorageType
import com.shov.preferences.datasources.PreferencesDataSource
import com.shov.storagerepositories.repositories.signIn.SignInRepository
import com.shov.unlimstorage.values.IS_AUTH
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
	private val signInRepository: SignInRepository,
	preferences: PreferencesDataSource
) : ViewModel() {
	var serviceAccess by mutableStateOf(false)
		private set
	private var isAuth by preferences.getPref(IS_AUTH, false)

	fun signIn(onSignIn: () -> Unit) {
		if (serviceAccess) {
			isAuth = true
			onSignIn()
		}
	}

	fun checkAccessWithResult(result: ActivityResult, storageType: StorageType) {
		serviceAccess = signInRepository.isSuccess(storageType, result)
	}

	fun getAccess(storageType: StorageType): Intent = signInRepository.signIn(storageType)
}

package com.shov.unlimstorage.viewModels

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.models.signInModels.SignInFactory
import com.shov.unlimstorage.models.signInModels.SignInSample
import com.shov.unlimstorage.models.signInModels.SignInType
import com.shov.unlimstorage.repositories.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
	private val signInFactory: SignInFactory,
	private val signInRepository: SignInRepository
) : ViewModel() {

	private val _serviceAccess = MutableLiveData<Boolean>()
	val serviceAccess: LiveData<Boolean>
		get() = _serviceAccess

	val isLogIn: Boolean
		get() = signInRepository.isLogIn

	val checkAccess: (result: ActivityResult, signInType: SignInType) -> Unit =
		{ activityResult: ActivityResult, signInType: SignInType ->
			if (signInFactory.create<SignInSample>(signInType).isSuccess(activityResult)) {
				signInRepository.setLogIn(true)
				_serviceAccess.postValue(true)
			} else _serviceAccess.postValue(false)
		}

	val getAccess: (
		startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
		signInClass: SignInType
	) -> Unit = { signInForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
	              signInType: SignInType ->
		signInFactory.create<SignInSample>(signInType).signIn(signInForResult)
	}
}

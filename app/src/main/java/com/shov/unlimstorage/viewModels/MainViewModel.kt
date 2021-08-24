package com.shov.unlimstorage.viewModels

import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.R
import com.shov.unlimstorage.repositories.SignInRepository
import com.shov.unlimstorage.ui.TopBarObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val signInRepository: SignInRepository) :
	ViewModel() {
	private val _topAppBar = MutableStateFlow(TopBarObject(text = R.string.app_name))
	val topAppBar = _topAppBar.asStateFlow()

	fun setTopAppBar(topBarObject: TopBarObject) {
		_topAppBar.value = topBarObject
	}

	val isLogIn: Boolean
		get() = signInRepository.isLogIn
}

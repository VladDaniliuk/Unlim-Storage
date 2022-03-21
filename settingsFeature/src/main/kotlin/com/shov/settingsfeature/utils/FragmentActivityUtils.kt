package com.shov.settingsfeature.utils

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.shov.settingsfeature.R

fun FragmentActivity.showBiometricAuthentication(onAuthenticationSucceeded: () -> Unit) {
	val promptInfo = BiometricPrompt.PromptInfo.Builder()
		.setTitle(getString(R.string.biometric_logging))
		.setSubtitle(getString(R.string.log_in_by_biometric))
		.setNegativeButtonText(getString(R.string.cancel))
		.setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
		.build()

	BiometricPrompt(this, object : BiometricPrompt.AuthenticationCallback() {
		override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
			onAuthenticationSucceeded()
		}
	}).authenticate(promptInfo)
}

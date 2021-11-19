package com.shov.unlimstorage.viewModels

import androidx.lifecycle.ViewModel
import com.shov.unlimstorage.utils.converters.SizeConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SizeConverterViewModel @Inject constructor(
	private val sizeConverter: SizeConverter
) : ViewModel() {
	fun toBytes(size: Long?) = sizeConverter.run { size?.toBytes() }
}
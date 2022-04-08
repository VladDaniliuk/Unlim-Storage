package com.shov.filesfeature.viewModels.newObject

import android.os.ParcelFileDescriptor
import androidx.lifecycle.ViewModel
import java.io.FileInputStream
import java.io.InputStream

class UploadNavigationViewModel : ViewModel() {
	var inputStream: InputStream? = null
		private set

	private var fileDescriptor: ParcelFileDescriptor? = null

	fun setFileDescriptor(fileDescriptor: ParcelFileDescriptor?) {
		this.fileDescriptor = fileDescriptor

		inputStream = FileInputStream(fileDescriptor?.fileDescriptor)
	}

	fun closeFileDescriptor() {
		fileDescriptor?.close()
	}
}

package com.shov.coremodels.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.shov.coremodels.R

enum class StorageType(@DrawableRes val imageId: Int, @StringRes val nameId: Int) {
	GOOGLE(R.drawable.ic_google_drive, R.string.google),
	BOX(R.drawable.ic_box, R.string.box),
	DROPBOX(R.drawable.ic_drop_box, R.string.dropbox),
	//ONEDRIVE(R.drawable.ic_one_drive, R.string.onedrive)
}

package com.shov.coremodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.ui.graphics.vector.ImageVector

enum class ItemType(val imageVector: ImageVector) {
	FILE(Icons.Rounded.Description), FOLDER(Icons.Rounded.Folder);
}

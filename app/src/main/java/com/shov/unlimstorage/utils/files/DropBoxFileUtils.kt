package com.shov.unlimstorage.utils.files

import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.oauth.DbxCredential
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.DbxUserFilesRequests
import com.shov.unlimstorage.values.DROPBOX_CLIENT_IDENTIFIER

fun createDbxUserFilesRequests(dropBoxCredential: String): DbxUserFilesRequests? =
	if (dropBoxCredential.isNotEmpty()) DbxClientV2(
		DbxRequestConfig(DROPBOX_CLIENT_IDENTIFIER),
		DbxCredential.Reader.readFully(dropBoxCredential)
	).files() else null

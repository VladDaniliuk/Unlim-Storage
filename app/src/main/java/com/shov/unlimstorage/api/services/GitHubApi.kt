package com.shov.unlimstorage.api.services

import com.shov.autoupdatefeature.models.LastReleaseItem
import com.shov.unlimstorage.values.GitHub
import retrofit2.Response
import retrofit2.http.GET

interface GitHubApi {
	@GET(GitHub.latest)
	suspend fun getLastRelease(): Response<LastReleaseItem>
}

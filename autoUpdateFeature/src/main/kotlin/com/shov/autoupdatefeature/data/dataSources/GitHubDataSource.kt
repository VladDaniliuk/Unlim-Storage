package com.shov.autoupdatefeature.data.dataSources

import com.shov.autoupdatefeature.models.LastReleaseItem
import com.shov.autoupdatefeature.values.GitHub
import retrofit2.Response
import retrofit2.http.GET

interface GitHubDataSource {
	@GET(GitHub.latest)
	suspend fun getLastRelease(): Response<LastReleaseItem>
}

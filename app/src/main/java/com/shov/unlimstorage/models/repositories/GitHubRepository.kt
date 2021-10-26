package com.shov.unlimstorage.models.repositories

import com.shov.unlimstorage.api.models.LastReleaseItem
import com.shov.unlimstorage.api.services.GitHubApi
import retrofit2.Response
import javax.inject.Inject

interface GitHubRepository {
	suspend fun getLastRelease(): Response<LastReleaseItem>
}

class GitHubRepositoryImpl @Inject constructor(
	private val gitHubApi: GitHubApi
) : GitHubRepository {
	override suspend fun getLastRelease(): Response<LastReleaseItem> {
		return gitHubApi.getLastRelease()
	}
}

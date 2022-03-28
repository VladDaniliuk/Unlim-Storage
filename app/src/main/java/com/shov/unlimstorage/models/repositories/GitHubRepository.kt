package com.shov.unlimstorage.models.repositories

import com.shov.autoupdatefeature.models.GitHubDataSource
import com.shov.autoupdatefeature.models.LastReleaseItem
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

interface GitHubRepository {
	suspend fun getLastRelease(): Response<LastReleaseItem>
}

@Singleton
class GitHubRepositoryImpl @Inject constructor(
	private val gitHubDataSource: GitHubDataSource
) : GitHubRepository {
	override suspend fun getLastRelease(): Response<LastReleaseItem> =
		gitHubDataSource.getLastRelease()
}

package com.shov.autoupdatefeature.data.repositories

import com.shov.autoupdatefeature.data.dataSources.GitHubDataSource
import com.shov.autoupdatefeature.models.LastReleaseItem
import com.shov.autoupdatefeature.utils.compareWithOld
import javax.inject.Inject
import javax.inject.Singleton

interface GitHubRepository {
	suspend fun getLastRelease(
		currentVersion: String,
		onNewerAction: (LastReleaseItem) -> Unit,
		onEqualsAction: () -> Unit
	)
}

@Singleton
class GitHubRepositoryImpl @Inject constructor(
	private val gitHubDataSource: GitHubDataSource
) : GitHubRepository {
	override suspend fun getLastRelease(
		currentVersion: String,
		onNewerAction: (LastReleaseItem) -> Unit,
		onEqualsAction: () -> Unit
	) {
		gitHubDataSource.getLastRelease().body()?.let { lastRelease ->
			lastRelease.tagName.compareWithOld(
				currentVersion,
				onNewerAction = {
					onNewerAction(lastRelease)
				},
				onEqualsAction = onEqualsAction
			)
		}
	}
}

package com.shov.autoupdatefeature.utils

fun String.compareWithOld(
	oldVersion: String?,
	onNewerAction: () -> Unit = {},
	onOlderAction: () -> Unit = {},
	onEqualsAction: () -> Unit = {}
) {
	val oldVersionSplit = oldVersion?.split(".")

	this.split(".").forEachIndexed { index, newElement ->
		if (newElement.toLong() > (oldVersionSplit?.getOrNull(index)?.toLong() ?: 0)) {
			onNewerAction()
			return
		} else if (newElement.toLong() < (oldVersionSplit?.getOrNull(index)?.toLong() ?: 0)) {
			onOlderAction()
			return
		}
	}

	onEqualsAction()
}
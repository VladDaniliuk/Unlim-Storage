package com.shov.unlimstorage.views.files

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.UserInfo
import com.shov.unlimstorage.ui.buttons.CustomIconButton
import com.shov.unlimstorage.ui.icons.ItemTypeIcon
import com.shov.unlimstorage.ui.texts.CustomText
import com.shov.unlimstorage.ui.texts.TextInfo
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.viewModels.DownloadViewModel
import com.shov.unlimstorage.viewModels.common.ScaffoldViewModel
import com.shov.unlimstorage.viewModels.common.TopAppBarViewModel
import com.shov.unlimstorage.viewModels.files.FileInfoViewModel
import com.shov.unlimstorage.viewModels.provider.singletonViewModel
import com.shov.unlimstorage.viewStates.FileInfoState
import com.shov.unlimstorage.viewStates.rememberFileInfoState
import com.shov.unlimstorage.views.Permission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FileInfoScreen(
	downloadViewModel: DownloadViewModel = singletonViewModel(),
	fileInfoState: FileInfoState = rememberFileInfoState(),
	fileInfoViewModel: FileInfoViewModel = hiltViewModel(),
	navigateTo: (String) -> Unit,
	popBack: () -> Unit,
	scaffoldViewModel: ScaffoldViewModel = singletonViewModel(),
	topAppBarViewModel: TopAppBarViewModel = singletonViewModel(),
) {
	val isConnected by fileInfoState.context.observeConnectivityAsFlow()
		.collectAsState(initial = false)

	if (fileInfoViewModel.isDialogShown) {
		Permission(
			onDismissRequest = { fileInfoViewModel.setShowDialog(false) },
			onHasAccess = {
				fileInfoViewModel.setShowDialog(false)
				fileInfoViewModel.downloadFile(downloadViewModel::setProgress)
			}
		)
	}

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.verticalScroll(state = rememberScrollState())
	) {
		fileInfoViewModel.storeItem?.let { item ->
			ItemTypeIcon(
				modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
				iconSize = 64.dp,
				mainIcon = item.type.imageVector,
				mainTint = MaterialTheme.colors.onBackground,
				contentDescription = item.type.name,
				secondaryIcon = painterResource(item.disk.imageId),
				secondaryAlignment = Alignment.BottomEnd
			)

			fileInfoViewModel.getBeautySize()?.let { size ->
				TextInfo(name = stringResource(R.string.size_description), value = size)
			}

			TextInfo(name = stringResource(R.string.id_description), value = item.id)
		}

		fileInfoViewModel.storeMetadata?.let { metadata ->
			listOf(
				stringResource(R.string.created_time) to metadata.createdTime,
				stringResource(R.string.modified_time) to metadata.modifiedTime,
				stringResource(R.string.version_description) to metadata.version
			).forEach { pair ->
				pair.second?.let { value ->
					TextInfo(name = pair.first, value = value)
				}
			}
		}

		Divider()

		Box(modifier = Modifier.fillMaxWidth()) {
			Text(
				modifier = Modifier
					.padding(start = 16.dp)
					.align(Alignment.CenterStart),
				text = stringResource(R.string.description),
				fontSize = Typography().subtitle2.fontSize,
				fontStyle = Typography().subtitle2.fontStyle,
				fontWeight = Typography().subtitle2.fontWeight,
			)

			IconButton(
				modifier = Modifier
					.padding(end = 16.dp)
					.align(Alignment.CenterEnd),
				onClick = { fileInfoViewModel.storeMetadata?.id?.let(navigateTo) }
			) {
				Icon(
					imageVector = Icons.Rounded.Edit,
					contentDescription = Icons.Rounded.Edit.name
				)
			}
		}

		CustomText(
			modifier = Modifier.padding(horizontal = 16.dp),
			text = fileInfoViewModel.storeMetadata?.description,
			textStyle = Typography().subtitle1
		)

		Divider()


		fileInfoViewModel.storeMetadata?.sharingUsers?.let { users ->
			CustomText(
				modifier = Modifier.padding(horizontal = 16.dp),
				text = stringResource(R.string.sharing_access),
				textStyle = Typography().subtitle2
			)

			LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
				items(items = users) { user ->
					UserInfo(
						modifier = Modifier.padding(start = 8.dp),
						onClick = {
							scaffoldViewModel.showSnackbar(
								fileInfoState.context.getString(R.string.user_click_message)
							)
						},
						onLongClick = {
							fileInfoState.hapticFeedback.performHapticFeedback(
								HapticFeedbackType.LongPress
							)
							user.email?.let {
								fileInfoState.clipboardManager.setText(AnnotatedString(user.email))

								scaffoldViewModel.showSnackbar(
									fileInfoState.context
										.getString(R.string.user_long_click_message)
								)
							} ?: scaffoldViewModel.showSnackbar(
								fileInfoState.context.getString(R.string.dont_have_email)
							)
						},
						iconLink = user.photoLink,
						iconSize = 32.dp,
						title = user.name ?: stringResource(R.string.user),
						subtitle = user.role
					)
				}
			}

			Divider()
		}

		fileInfoViewModel.storeMetadata?.let { metadata ->

			CustomText(
				modifier = Modifier.padding(horizontal = 16.dp),
				text = stringResource(R.string.link_description),
				textStyle = Typography().subtitle2
			)

			metadata.link?.let { link ->
				Row {
					CustomIconButton(
						imageVector = Icons.Rounded.OpenInBrowser,
						text = stringResource(R.string.open_in_browser),
						onClick = { fileInfoState.uriHandler.openUri(link) }
					)

					CustomIconButton(
						imageVector = Icons.Rounded.CopyAll,
						text = stringResource(R.string.copy_link)
					) {
						fileInfoState.clipboardManager.setText(AnnotatedString(link))

						scaffoldViewModel.showSnackbar(
							fileInfoState.context.getString(R.string.link_copied)
						)
					}

					CustomIconButton(
						imageVector = Icons.Rounded.Share,
						text = stringResource(R.string.share_link),
					) {
						fileInfoState.context.startActivity(
							Intent.createChooser(
								Intent().apply {
									action = Intent.ACTION_SEND
									putExtra(Intent.EXTRA_TEXT, link)
									type = "text/*"
								},
								null
							)
						)
					}
				}
			} ?: Button(
				modifier = Modifier
					.padding(horizontal = 16.dp)
					.fillMaxWidth(),
				onClick = {
					scaffoldViewModel.showSnackbar(
						fileInfoState.context.getString(R.string.doesnt_work_now)
					)
				}
			) {
				Text(text = stringResource(R.string.create_link))
			}

			Button(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 8.dp),
				shape = CircleShape,
				onClick = fileInfoViewModel::setShowDialog
			) {
				Text(text = stringResource(id = R.string.download))
			}

			Spacer(modifier = Modifier.navigationBarsPadding())
		}
	}

	LaunchedEffect(key1 = null) {
		launch(Dispatchers.IO) {
			fileInfoViewModel.getStoreItem()
		}.invokeOnCompletion {
			if (isConnected) {
				fileInfoViewModel.getFileMetadata()
			}
		}
	}

	LaunchedEffect(key1 = isConnected) {
		if (isConnected.and(fileInfoViewModel.storeMetadata == null)) {
			fileInfoViewModel.getFileMetadata()
		}
	}

	LaunchedEffect(key1 = fileInfoViewModel.storeItem?.name) {
		topAppBarViewModel.setTopBar(
			Icons.Rounded.ArrowBack to popBack,
			fileInfoViewModel.storeItem?.name,
			if (fileInfoViewModel.storeMetadata?.isStarred == true) {
				Icons.Rounded.Star
			} else {
				Icons.Rounded.StarBorder
			} to {
				scaffoldViewModel.showSnackbar(
					fileInfoState.context.getString(R.string.doesnt_work_now)
				)
			}
		)
	}
}

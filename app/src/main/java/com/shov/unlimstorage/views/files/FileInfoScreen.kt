package com.shov.unlimstorage.views.files

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.navigationBarsPadding
import com.shov.unlimstorage.R
import com.shov.unlimstorage.ui.*
import com.shov.unlimstorage.utils.converters.toPrettyString
import com.shov.unlimstorage.utils.observeConnectivityAsFlow
import com.shov.unlimstorage.values.*
import com.shov.unlimstorage.viewModels.files.FileInfoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
@Composable
fun FileInfoScreen(
	fileInfoViewModel: FileInfoViewModel,
	filesNavController: NavController,
	scaffoldState: ScaffoldState,
	setTopBar: (
		prevRoute: Pair<ImageVector, (() -> Unit)>?,
		title: String?,
		nextRoute: Pair<ImageVector, (() -> Unit)>?
	) -> Unit
) {
	val coroutineScope = rememberCoroutineScope()
	val currentClipboardManager = LocalClipboardManager.current
	val currentContext = LocalContext.current
	val currentHapticFeedback = LocalHapticFeedback.current
	val currentUriHandler = LocalUriHandler.current
	val isConnected =
		currentContext.observeConnectivityAsFlow().collectAsState(initial = false).value

	setTopBar(
		Icons.Rounded.ArrowBack to { filesNavController.popBackStack() },
		fileInfoViewModel.storeItem.name,
		if (fileInfoViewModel.storeMetadata?.isStarred == true) {
			Icons.Rounded.Star
		} else {
			Icons.Rounded.StarBorder
		} to {
			coroutineScope.launch {
				scaffoldState.snackbarHostState.showSnackbar("Doesn't work now")
			}
		}
	)

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.verticalScroll(state = rememberScrollState())
	) {
		ItemTypeIcon(
			modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
			iconSize = SIZE_ICON_BIG,
			mainIcon = fileInfoViewModel.storeItem.type.imageVector,
			mainTint = MaterialTheme.colors.onBackground,
			contentDescription = fileInfoViewModel.storeItem.type.name,
			secondaryIcon = painterResource(fileInfoViewModel.storeItem.disk.imageId),
			secondaryAlignment = Alignment.BottomEnd
		)

		listOf(
			stringResource(R.string.size_description) to fileInfoViewModel.storeItem.size,
			stringResource(R.string.id_description) to fileInfoViewModel.storeItem.id
		).forEach { pair ->
			pair.second?.let { value ->
				TextInfo(name = pair.first, value = value)
			}
		}



		fileInfoViewModel.storeMetadata?.let { metadata ->
			listOf(
				stringResource(R.string.created_time) to metadata.createdTime?.toPrettyString(),
				stringResource(R.string.modified_time) to metadata.modifiedTime?.toPrettyString(),
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
					.padding(start = PADDING_MEDIUM)
					.align(Alignment.CenterStart),
				text = stringResource(R.string.description),
				fontSize = Typography().subtitle2.fontSize,
				fontStyle = Typography().subtitle2.fontStyle,
				fontWeight = Typography().subtitle2.fontWeight,
			)

			IconButton(
				modifier = Modifier
					.padding(end = PADDING_MEDIUM)
					.align(Alignment.CenterEnd),
				onClick = {
					filesNavController.currentBackStackEntry
						?.arguments
						?.putParcelable(argStoreMetadata, fileInfoViewModel.storeMetadata)
					filesNavController.navigate(navFileDescription)
				}
			) {
				Icon(
					imageVector = Icons.Rounded.Edit,
					contentDescription = Icons.Rounded.Edit.name
				)
			}
		}

		CustomText(
			modifier = Modifier.padding(horizontal = PADDING_MEDIUM),
			text = fileInfoViewModel.storeMetadata?.description,
			textStyle = Typography().subtitle1
		)

		Divider()


		fileInfoViewModel.storeMetadata?.sharingUsers?.let { users ->
			CustomText(
				modifier = Modifier.padding(horizontal = PADDING_MEDIUM),
				text = stringResource(R.string.sharing_access),
				textStyle = Typography().subtitle2
			)

			LazyRow(modifier = Modifier.padding(vertical = PADDING_SMALL_PLUS)) {
				items(items = users) { user ->
					val onClickMessage = stringResource(R.string.user_click_message)
					val onLongClickMessage = stringResource(R.string.user_long_click_message)

					UserInfo(
						modifier = Modifier.padding(start = PADDING_SMALL_PLUS),
						onClick = {
							coroutineScope.launch {
								scaffoldState.snackbarHostState
									.showSnackbar(message = onClickMessage)
							}
						},
						onLongClick = {
							currentHapticFeedback.performHapticFeedback(
								HapticFeedbackType.LongPress
							)

							currentClipboardManager.setText(AnnotatedString(user.email))

							coroutineScope.launch {
								scaffoldState.snackbarHostState
									.showSnackbar(message = onLongClickMessage)
							}
						},
						contentDescription = user.name,
						iconLink = user.photoLink,
						iconSize = SIZE_ICON_SMALL,
						title = user.name,
						subtitle = user.role
					)
				}
			}

			Divider()
		}

		fileInfoViewModel.storeMetadata?.let { metadata ->

			CustomText(
				modifier = Modifier.padding(horizontal = PADDING_MEDIUM),
				text = stringResource(R.string.link_description),
				textStyle = Typography().subtitle2
			)

			metadata.link?.let { link ->
				val message = stringResource(R.string.link_copied)

				LazyRow {
					items(
						items = listOf(
							Triple(Icons.Rounded.OpenInBrowser, R.string.open_in_browser) {
								currentUriHandler.openUri(link)
							}, Triple(Icons.Rounded.CopyAll, R.string.copy_link) {

								currentClipboardManager.setText(AnnotatedString(link))

								coroutineScope.launch {
									scaffoldState.snackbarHostState.showSnackbar(message = message)
								}
							}, Triple(Icons.Rounded.Share, R.string.share_link) {
								val sendIntent: Intent = Intent().apply {
									action = Intent.ACTION_SEND
									putExtra(Intent.EXTRA_TEXT, link)
									type = "text/*"
								}
								val shareIntent = Intent.createChooser(sendIntent, null)

								currentContext.startActivity(shareIntent)
							}
						)
					) { item ->
						CustomIconButton(
							image = item.first,
							text = stringResource(item.second),
							onClick = item.third
						)
					}
				}
			} ?: Button(
				modifier = Modifier
					.padding(horizontal = PADDING_MEDIUM)
					.fillMaxWidth(),
				onClick = {
					coroutineScope.launch {
						scaffoldState.snackbarHostState.showSnackbar("Doesn't work now")
					}
				}
			) {
				Text(text = stringResource(R.string.create_link))
			}

			Spacer(modifier = Modifier.navigationBarsPadding())
		}
	}

	LaunchedEffect(key1 = isConnected) {
		if (isConnected.and(fileInfoViewModel.storeMetadata == null)) {
			fileInfoViewModel.getFileMetadata()
		}
	}
}

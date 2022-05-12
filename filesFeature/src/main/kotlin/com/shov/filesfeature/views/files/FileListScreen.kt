package com.shov.filesfeature.views.files

import androidx.compose.animation.*
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.shov.coreui.ui.buttons.AnimatedIconButton
import com.shov.coreui.ui.texts.CustomText
import com.shov.coreui.viewModels.NavigationViewModel
import com.shov.coreui.views.CustomScaffold
import com.shov.coreutils.values.BottomSheet
import com.shov.coreutils.values.Screen
import com.shov.coreutils.viewModels.singletonViewModel
import com.shov.filesfeature.ui.NavigationChain
import com.shov.filesfeature.ui.scaffold.FABScaffold
import com.shov.filesfeature.viewModels.FileListViewModel
import com.shov.filesfeature.views.FileListNavigation

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FileListScreen(
	navigationViewModel: NavigationViewModel = singletonViewModel(),
	fileListViewModel: FileListViewModel = hiltViewModel(),
	filesNavController: NavHostController = rememberAnimatedNavController()
) {
	CustomScaffold {
		Column {
			Row(
				modifier = Modifier
					.padding(
						top = WindowInsets.statusBars
							.asPaddingValues()
							.calculateTopPadding()
					)
					.padding(horizontal = 16.dp, vertical = 8.dp)
					.clip(shape = RoundedCornerShape(50))
					.background(
						TopAppBarDefaults
							.centerAlignedTopAppBarColors()
							.containerColor(1f).value
					)
					.clickable(
						onClick = {
							navigationViewModel.navigateTo(Screen.Search.route)
						},
						indication = LocalIndication.current,
						interactionSource = remember { MutableInteractionSource() })
					.height(48.dp)
					.fillMaxWidth(),
			) {
				Box(
					modifier = Modifier
						.align(Alignment.CenterVertically)
						.padding(start = 2.dp)
				) {
					AnimatedIconButton(
						imageVector = if (fileListViewModel.backStacks.isNotEmpty()) Icons.Rounded.ArrowBack else null,
						onClick = { fileListViewModel.dropFromBackStack(filesNavController) },
						enabled = true
					)
				}

				AnimatedContent(
					modifier = Modifier
						.weight(1f)
						.align(Alignment.CenterVertically)
						.padding(horizontal = 4.dp),
					targetState = "title",
					transitionSpec = {
						(slideInVertically { height -> height } + fadeIn()
								with slideOutVertically { height -> -height } + fadeOut())
							.using(SizeTransform(clip = false))
					}
				) { targetText ->
					CustomText(
						modifier = Modifier.fillMaxWidth(),
						maxLines = 1,
						overflow = TextOverflow.Ellipsis,
						text = targetText,
						textAlign = TextAlign.Center,
						textStyle = Typography().titleLarge,
					)
				}

				Box(
					modifier = Modifier
						.align(Alignment.CenterVertically)
						.padding(end = 2.dp)
				) {
					AnimatedIconButton(
						imageVector = Icons.Rounded.AccountCircle,
						onClick = { navigationViewModel.navigateTo(Screen.Settings.route) },
						enabled = true
					)
				}
			}

			FABScaffold(
				modifier = Modifier
					.padding(it)
					.weight(1f),
				onCreateNewFolderClick = {
					navigationViewModel.navigateTo(
						BottomSheet.NewFolder.setParent(
							fileListViewModel.backStacks.lastOrNull()?.storageType,
							fileListViewModel.backStacks.lastOrNull()?.folderId
						)
					)
				},
				onUploadFile = {
					navigationViewModel.navigateTo(
						BottomSheet.UploadFile.setParent(
							fileListViewModel.backStacks.lastOrNull()?.storageType,
							fileListViewModel.backStacks.lastOrNull()?.folderId
						)
					)
				}
			) {
				Column {
					NavigationChain(
						backStacks = fileListViewModel.backStacks,
						iconEnabled = fileListViewModel.backStacks.isNotEmpty(),
						iconOnClick = {
							fileListViewModel.dropAllFromBackStack(filesNavController)
						},
					) { index ->
						fileListViewModel.dropFromBackStack(filesNavController, index)
					}

					FileListNavigation(filesNavHostController = filesNavController) {
						fileListViewModel.dropFromBackStack(filesNavController)
					}
				}
			}
		}
	}

	LaunchedEffect(key1 = filesNavController.currentBackStackEntryAsState().value) {
		fileListViewModel.compareBackStack(filesNavController)
	}
}

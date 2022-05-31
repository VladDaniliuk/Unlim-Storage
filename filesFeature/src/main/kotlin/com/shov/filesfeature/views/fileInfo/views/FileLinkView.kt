package com.shov.filesfeature.views.fileInfo.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material.icons.rounded.OpenInBrowser
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shov.coreui.ui.buttons.CustomIconButton
import com.shov.coreui.ui.texts.CustomText
import com.shov.filesfeature.R
import com.shov.filesfeature.ui.button.MaxWidthButton
import com.shov.filesfeature.utils.share

@Composable
fun FileLinkView(
    link: String?,
    onShowSnackbar: (String) -> Unit,
    onShareLink: () -> Unit
) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val clipboardManager = LocalClipboardManager.current

    Column {
        CustomText(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.link_description),
            textStyle = Typography().titleSmall
        )

        if (link != null) {
            Row {
                CustomIconButton(
                    imageVector = Icons.Rounded.OpenInBrowser,
                    text = stringResource(R.string.open_in_browser),
                    onClick = { uriHandler.openUri(link) }
                )

                CustomIconButton(
                    imageVector = Icons.Rounded.CopyAll,
                    text = stringResource(R.string.copy_link),
                    onClick = {
                        clipboardManager.setText(AnnotatedString(link))
                        onShowSnackbar(context.getString(R.string.link_copied))
                    }
                )

                CustomIconButton(
                    imageVector = Icons.Rounded.Share,
                    text = stringResource(R.string.share_link)
                ) { context.share(link) }
            }
        } else {
            MaxWidthButton(stringResource(R.string.create_link)) {
                onShareLink()
            }
        }
    }
}

@Preview
@Composable
fun FileLinkPreview() {
    FileLinkView(
        link = "link",
        onShowSnackbar = {}
    ) {}
}

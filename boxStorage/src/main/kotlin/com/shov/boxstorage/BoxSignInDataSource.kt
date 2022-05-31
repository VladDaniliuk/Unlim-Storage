package com.shov.boxstorage

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import com.box.androidsdk.content.models.BoxSession
import com.box.androidsdk.content.models.BoxUser
import com.shov.storage.SignInDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BoxSignInDataSource @Inject constructor(
    @ApplicationContext val context: Context
) : SignInDataSource {
    override fun signIn(): Intent = Intent(context, BoxAuthActivity::class.java)

    override fun isSuccess(result: ActivityResult): Boolean =
        (BoxSession(context).user?.status == BoxUser.Status.ACTIVE) and (result.resultCode == RESULT_OK)

    override fun isSuccess(): Boolean {
        return BoxSession(context).user?.status == BoxUser.Status.ACTIVE
    }

    override suspend fun signOut() {
        BoxSession(context).logout()
    }
}

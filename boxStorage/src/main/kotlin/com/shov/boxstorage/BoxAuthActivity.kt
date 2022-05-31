package com.shov.boxstorage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.box.androidsdk.content.BoxConfig
import com.box.androidsdk.content.auth.BoxAuthentication
import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo
import com.box.androidsdk.content.models.BoxSession

class BoxAuthActivity : ComponentActivity(), BoxAuthentication.AuthListener {

    private var mSession: BoxSession? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_box_auth)
        configureClient()
        initSession()
    }

    private fun configureClient() {
        BoxConfig.CLIENT_ID = BOX_CLIENT_ID
        BoxConfig.CLIENT_SECRET = BOX_CLIENT_SECRET
        BoxConfig.REDIRECT_URL = BOX_REDIRECT_URL
    }

    private fun initSession() {
        mSession = BoxSession(this)
        mSession!!.setSessionAuthListener(this)
        mSession!!.authenticate(this)
    }

    override fun onRefreshed(info: BoxAuthenticationInfo?) {}

    override fun onAuthCreated(info: BoxAuthenticationInfo?) {
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }

    override fun onAuthFailure(info: BoxAuthenticationInfo?, ex: Exception?) {
        if ((info == null) and (mSession != null)) {
            onAuthCreated(mSession!!.authInfo)
        } else finish()
    }

    override fun onLoggedOut(info: BoxAuthenticationInfo?, ex: Exception?) {
        initSession()
    }
}

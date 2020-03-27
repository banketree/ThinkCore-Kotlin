@file:Suppress("NOTHING_TO_INLINE")
package com.thinkcore.kandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Patterns
import androidx.fragment.app.Fragment

inline fun <reified T : Any> IntentFor(context: Context): Intent = Intent(context, T::class.java)

inline fun Intent.start(context: Context) = context.startActivity(this)

inline fun Intent.startForResult(activity: Activity, requestCode: Int) = activity.startActivityForResult(this, requestCode)

inline fun Intent.startForResult(fragment: Fragment, requestCode: Int) = fragment.startActivityForResult(this, requestCode)

inline fun WebIntent(url: String): Intent =
        if (Patterns.WEB_URL.matcher(url).matches()) {
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        } else {
            throw IllegalArgumentException("Passed url: $url does not match URL pattern.")
        }
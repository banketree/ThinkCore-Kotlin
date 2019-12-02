package com.thinkcore.kotlin.extend.all

import android.content.Intent
import android.net.Uri
import android.content.Context

public inline fun Intent(body: Intent.() -> Unit): Intent {
    val intent = Intent()
    intent.body()
    return intent
}

public inline fun Intent(o: Intent?, body: Intent.() -> Unit): Intent {
    val intent = Intent(o)
    intent.body()
    return intent
}

public inline fun Intent(action: String?, body: Intent.() -> Unit): Intent {
    val intent = Intent(action)
    intent.body()
    return intent
}

public inline fun String.toIntent(body: Intent.() -> Unit): Intent = Intent(this, body)

public inline fun String.toIntent(): Intent = Intent(this)

public inline fun Intent(action: String?, uri: Uri?, body: Intent.() -> Unit): Intent {
    val intent = Intent(action, uri)
    intent.body()
    return intent
}

public inline fun Intent(packageContext: Context?, cls: Class<*>?, body: Intent.() -> Unit): Intent {
    val intent = Intent(packageContext, cls)
    intent.body()
    return intent
}

public inline fun Intent(action: String?, uri: Uri?, packageContext: Context?, cls: Class<*>?, body: Intent.() -> Unit): Intent {
    val intent = Intent(action, uri, packageContext, cls)
    intent.body()
    return intent
}

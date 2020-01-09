@file:Suppress("NOTHING_TO_INLINE")

package com.thinkcore.activity

import android.content.Intent
import android.os.Bundle
import java.lang.NullPointerException
import java.util.*

/**
 * @author banketree
 * @time 2020/1/9 10:54
 * @description
 * 跳转封装成扩展函数
 */
inline fun <reified T : Any> TAppActivity.jumpToActivity(
    targetIntent: Intent? = null,
    bundle: Bundle? = null
) = run {
    var actionIntent = targetIntent
    if (targetIntent == null) {
        actionIntent = Intent(this, T::class.java)
    }
    bundle?.let {
        actionIntent?.putExtras(it)
    }

    startActivity(actionIntent)
}

inline fun <reified T : Any> TAppActivity.jumpToNewTopActivity(
    targetIntent: Intent? = null,
    bundle: Bundle?
) = run {
    var actionIntent = targetIntent
    if (targetIntent == null) {
        actionIntent = Intent(this, T::class.java)
    }
    bundle?.let {
        actionIntent?.putExtras(it)
    }
    actionIntent?.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    startActivity(actionIntent)
}

inline fun <reified T : Any> TAppActivity.jumpToActivityForResult(
    bundle: Bundle? = null
    , resultId: Int = Random().nextInt(10000) + Random().nextInt(1000) + Random().nextInt(100)
    , iActivityResult: IActivityResult? = null
) = run {
    var actionIntent = Intent(this, T::class.java)

    iActivityResult?.let {
        this.iActivityResult[resultId] = it
    }
    bundle?.let {
        actionIntent.putExtras(it)
    }
    startActivityForResult(actionIntent, resultId)
}

inline fun TAppActivity.jumpToActivityForResult(
    targetIntent: Intent,
    bundle: Bundle? = null
    , resultId: Int = Random().nextInt(10000) + Random().nextInt(1000) + Random().nextInt(100)
    , iActivityResult: IActivityResult? = null
) = run {
    iActivityResult?.let {
        this.iActivityResult[resultId] = it
    }
    bundle?.let {
        targetIntent.putExtras(it)
    }
    startActivityForResult(targetIntent, resultId)
}
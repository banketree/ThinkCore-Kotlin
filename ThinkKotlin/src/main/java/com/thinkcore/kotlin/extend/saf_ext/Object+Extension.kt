package com.thinkcore.kotlin.extend.saf_ext

fun <T : Any> T.TAG() = this::class.simpleName
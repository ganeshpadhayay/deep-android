package com.example.ganesh.deep_android.scopestorage

import android.os.Build

/**
 * Created by Ganesh Padhayay on 28/05/21.
 */
inline fun <T> sdk29AndAbove(onSdk29: () -> T): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        onSdk29.invoke()
    } else null
}
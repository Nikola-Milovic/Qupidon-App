package com.nikolam.common.navigation

import android.net.Uri
import androidx.navigation.NavDirections

class NavManager {

    private var navEventListener: ((uri: Uri) -> Unit)? = null
    private var popBackStackListener: (() -> Unit)? = null

    fun navigate(uri: Uri) {
        navEventListener?.invoke(uri)
    }

    fun setOnNavEvent(navEventListener:(uri: Uri) -> Unit) {
        this.navEventListener = navEventListener
    }

    fun popBackStack() {
        popBackStackListener?.invoke()
    }

    fun setPopBackStack(popBackStackListener:() -> Unit) {
        this.popBackStackListener = popBackStackListener
    }
}

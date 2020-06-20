package com.vbstudio.covid19.core.utils

import android.content.Context

class UIUtils {

    companion object {
        fun getDisplayWidth(context: Context?) = context?.resources?.displayMetrics?.widthPixels

        fun getDisplayHeight(context: Context?) = context?.resources?.displayMetrics?.heightPixels

        fun getDisplayDensity(context: Context?) = context?.resources?.displayMetrics?.density
    }

}

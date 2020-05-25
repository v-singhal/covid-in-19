package com.vbstudio.covid19.core.utils

import android.util.Log

class LogUtils {
    companion object{
        fun d(logTag: String?, messageString: String?) {
            Log.d(logTag, messageString)
        }
    }
}
package com.vbstudio.covid19.core.networking.interfaces

interface ResponseErrorListener<T> {

    fun onError(t: Throwable?)

    fun onError(response: T?, statusCode: Int)
}
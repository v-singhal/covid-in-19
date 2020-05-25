package com.vbstudio.covid19.core.networking.interfaces

interface ResponseListener<T> {

    fun onResponse(response: T?)
}
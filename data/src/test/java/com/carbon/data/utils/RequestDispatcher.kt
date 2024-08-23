package com.carbon.data.utils


import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest


internal class RequestDispatcher : Dispatcher() {

    private var shouldFail = false

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {

            else -> throw IllegalArgumentException("Unknown Request Path ${request.path}")
        }
    }

    fun shouldReturnError(value: Boolean) {
        shouldFail = value
    }
}

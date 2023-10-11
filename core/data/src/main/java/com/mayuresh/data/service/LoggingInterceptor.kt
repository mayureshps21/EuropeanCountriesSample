package com.mayuresh.data.service

import android.util.Log
import androidx.multidex.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val requestBody = request.body
        val requestContent = requestBody?.let { bodyToString(it) } ?: "No Request Body"
        val requestLog = " Request: ${request.method} ${request.url}\n$requestContent"

        val responseBody = response.body
        val responseContent = responseBody?.let { bodyToString(it) } ?: "No Response Body"
        val responseLog = " Response: ${response.code} $responseContent"

        if (BuildConfig.DEBUG) {
            Log.d(this.javaClass.name, requestLog)
            Log.d(this.javaClass.name, responseLog)
        }

        return response
    }

    private fun bodyToString(requestBody: okhttp3.RequestBody): String {
        return try {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "Error reading request body."
        }
    }

    private fun bodyToString(responseBody: okhttp3.ResponseBody): String {
        return try {
            responseBody.byteStream().toString()
        } catch (e: IOException) {
            "Error reading request body."
        }
    }
}

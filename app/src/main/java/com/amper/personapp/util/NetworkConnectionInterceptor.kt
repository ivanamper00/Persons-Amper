package com.amper.personapp.util

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws


class NetworkConnectionInterceptor(
    private val context: Context
): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isConnected()) throw NoConnectivityException()
        val request = chain.request().newBuilder().build()
        return chain.proceed(request)
    }

    private fun isConnected(): Boolean {
        val netInfo = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return (netInfo != null && netInfo.isConnected)
    }
}

class NoConnectivityException(override val message: String? = null): IOException(message)
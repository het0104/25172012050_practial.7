package com.example.a24012011122_mad_pr7

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpRequest {

    fun makeServiceCall(
        reqUrl: String,
        token: String
    ): String? {

        var response: String? = null
        var connection: HttpURLConnection? = null

        try {

            val url = URL(reqUrl)

            connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"

            connection.setRequestProperty(
                "Authorization",
                "Bearer $token"
            )

            connection.setRequestProperty(
                "Content-Type",
                "application/json"
            )

            connection.connectTimeout = 15000
            connection.readTimeout = 15000

            val responseCode = connection.responseCode

            if (responseCode in 200..299) {

                val inputStream = connection.inputStream

                response = convertStreamToString(inputStream)

            } else {

                Log.e(
                    TAG,
                    "HTTP Error: $responseCode"
                )
            }

        } catch (e: Exception) {

            Log.e(
                TAG,
                "makeServiceCall: ${e.message}",
                e
            )

        } finally {

            connection?.disconnect()
        }

        return response
    }

    private fun convertStreamToString(
        inputStream: java.io.InputStream
    ): String {

        val reader = BufferedReader(
            InputStreamReader(inputStream)
        )

        return reader.useLines { lines ->
            lines.joinToString("\n")
        }
    }

    companion object {
        private const val TAG = "HttpRequest"
    }
}
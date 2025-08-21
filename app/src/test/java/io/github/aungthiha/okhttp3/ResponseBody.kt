package io.github.aungthiha.okhttp3

import io.github.aungthiha.network.okhttp3.MEDIA_TYPE_JSON
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

fun String.toResponseJsonBody(): ResponseBody = toResponseBody(MEDIA_TYPE_JSON)

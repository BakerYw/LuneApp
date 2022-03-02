package com.nyw.lune.data.model

data class ApiErrorResponse<T>(val status: Int, val error: String, val msg: String)
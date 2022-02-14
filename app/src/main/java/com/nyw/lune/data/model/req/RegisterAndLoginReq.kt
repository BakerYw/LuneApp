package com.nyw.lune.data.model.req


data class RegisterAndLoginReq(
    var account: String,
    var mobile: String,
    var password: String,
    var smsCode: String
)
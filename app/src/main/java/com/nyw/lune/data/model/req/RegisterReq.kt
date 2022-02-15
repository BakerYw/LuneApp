package com.nyw.lune.data.model.req


data class RegisterReq(
    var account: String?,//账户名
    var clbum: String?,//班级
    var grade: String?,//学校
    var mobile: String?,//手机号
    var orgCode: String?,//机构码,开发时填写【1234】
    var password: String?,//密码
    var realName: String?,//真实姓名
    var school: String?,//学校
    var score: String?,//分数
    var smsCode: String?//1234
)
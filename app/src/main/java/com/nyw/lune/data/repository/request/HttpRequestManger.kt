package com.nyw.lune.data.repository.request

/**
 * 作者　: hegaojian
 * 时间　: 2020/5/4
 * 描述　: 处理协程的请求类
 */

val HttpRequestCoroutine: HttpRequestManger by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    HttpRequestManger()
}

class HttpRequestManger {
//
//    /**
//     * 注册并登陆
//     */
//    suspend fun register(req: RegisterAndLoginReq): ApiResponse<UserInfo> {
//        // 获取到我们的jsonobject参数，并toJSONString
//        val mediaType = MediaType.parse("application/json; charset=UTF-8")
//        val str: String = GsonUtils.toJson(req, RegisterAndLoginReq::class.java)
//        val requestBody = RequestBody.create(mediaType, str)
//        val registerData = apiService.register(requestBody)
//        //判断注册结果 注册成功，调用登录接口
//        if (registerData.isSucces()) {
//            return apiService.login(requestBody)
//        } else {
//            //抛出错误异常
//            throw AppException(registerData.code, registerData.msg)
//        }
//    }


}
package com.nyw.lune.app.util

import android.util.Base64
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


object FileBase64 {
    /**
     * encodeBase64File:(将文件转成base64 字符串).
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encodeBase64File(path: String?): String? {
        val file = File(path)
        val inputStream = FileInputStream(file)
        val bytes = ByteArray(inputStream.available())
        val length = inputStream.read(bytes)
        return Base64.encodeToString(bytes, 0, length, Base64.DEFAULT)
    }

    /**
     * decoderBase64File:(将base64字符解码保存文件).
     * @param base64Code 编码后的字串
     * @param savePath  文件保存路径
     * @throws Exception
     */
    @Throws(java.lang.Exception::class)
    fun decoderBase64File(base64Code: String?, savePath: String?) {
        val buffer = Base64.decode(base64Code, Base64.DEFAULT)
        val out = FileOutputStream(savePath)
        out.write(buffer)
        out.close()
    }

}


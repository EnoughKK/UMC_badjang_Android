package com.umc.badjang.PostWritePage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

import android.util.Base64

// 사용자 디바이스에 저장된 이미지를 받아서 서버에 저장하려면 Bitmap을 String으로 변환하는 작업 필요
class BitmapConverter {
    // Bitmap -> String
    fun bitmapToString(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        val byteArray = stream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    // String -> Bitmap
    fun StringToBitmap(encodedString: String?): Bitmap? {
        val decodedString = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }
}
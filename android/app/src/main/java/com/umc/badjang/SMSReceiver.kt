package com.umc.badjang

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log

class SMSReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        //문자가 왔다면?
        if(intent?.action.equals("android.provider.Telephony.SMS_RECEIVED")){
            //intent로부터 내용을 받아온 후에 함수 호출해 내용 파싱함함
           val bundle = intent?.extras
            val messages = smsMessageParse((bundle!!))

            if(messages?.size!! > 0) {
                //수신한 문자 메시지의 내용
                val content = messages[0]?.messageBody.toString()
                val certNumber = content?.replace("[^0-9]".toRegex(), "")
                //인증번호 만 찍힘
                Log.d("인증번호 추출 ", certNumber)
            }
        }
    }

    /*
    override fun onDestroy(){
        unregister(smsReceiver)
        super.onDestroy()
    }
     */

    //문자내용을 파싱해오는 함수
    private fun smsMessageParse(bundle: Bundle): Array<SmsMessage?>? {
        val objs = bundle["pdus"] as Array<Any>?
        val messages: Array<SmsMessage?> = arrayOfNulls<SmsMessage>(objs!!.size)
        for (i in objs!!.indices) {
            messages[i] = SmsMessage.createFromPdu(objs[i] as ByteArray)
        }
        return messages
    }


}




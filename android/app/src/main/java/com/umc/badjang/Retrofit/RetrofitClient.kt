package com.umc.badjang.Retrofit

import android.util.Log
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import com.umc.badjang.utils.API
import com.umc.badjang.utils.Constants.TAG
import com.umc.badjang.utils.isJsonArray
import com.umc.badjang.utils.isJsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

// 장학금 조회 API 레트로핏 클라이언트
object SearchScholarshipRC {
    // 레트로핏 클라이언트 선언
    private var retrofitClient: Retrofit? = null

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called : searchScholarship")

        // 로깅 인터셉터 추가

        // okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        // 로그를 찍기 위해 로깅 인터셉터 추가
        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{

            override fun log(message: String) {
                Log.d(TAG, "RetrofitClient - log() called / message: $message")

                when {
                    message.isJsonObject() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    else -> {
                        try {
                            Log.d(TAG, JSONObject(message).toString(4))
                        } catch (e: Exception) {
                            Log.d(TAG, message)
                        }
                    }
                }
            }
        })

        // 인터셉터 레벨 설정
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // 설정한 인터셉터를 클라잉언트에 추가
        client.addInterceptor(loggingInterceptor)

        if(retrofitClient == null) {
            // 레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build()) // 위에서 설장한 클라이언트로 레트로핏 클라이언트를 설정
                .build()
        }
        return retrofitClient
    }
}

// 지원금 조회 API 레트로핏 클라이언트
object SearchSupportRC {
    // 레트로핏 클라이언트 선언
    private var retrofitClient: Retrofit? = null

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called / searchSupport")

        // 로깅 인터셉터 추가

        // okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        // 로그를 찍기 위해 로깅 인터셉터 추가
        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{

            override fun log(message: String) {
                Log.d(TAG, "RetrofitClient2 - log() called / message: $message")

                when {
                    message.isJsonObject() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    else -> {
                        try {
                            Log.d(TAG, JSONObject(message).toString(4))
                        } catch (e: Exception) {
                            Log.d(TAG, message)
                        }
                    }
                }
            }
        })

        // 인터셉터 레벨 설정
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // 설정한 인터셉터를 클라잉언트에 추가
        client.addInterceptor(loggingInterceptor)

        if(retrofitClient == null) {
            // 레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build()) // 위에서 설장한 클라이언트로 레트로핏 클라이언트를 설정
                .build()
        }
        return retrofitClient
    }
}

// 인덱스로 장학금 조회 (조회수 증가) API 레트로핏 클라이언트
object ScholarshipViewCountRC {
    // 레트로핏 클라이언트 선언
    private var retrofitClient: Retrofit? = null

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called / scholarshipView")

        // 로깅 인터셉터 추가

        // okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        // 로그를 찍기 위해 로깅 인터셉터 추가
        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{

            override fun log(message: String) {
//                Log.d(TAG, "RetrofitClient3 - log() called / message: $message")

                when {
                    message.isJsonObject() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    else -> {
                        try {
                            Log.d(TAG, JSONObject(message).toString(4))
                        } catch (e: Exception) {
                            Log.d(TAG, message)
                        }
                    }
                }
            }
        })

        // 인터셉터 레벨 설정
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // 설정한 인터셉터를 클라잉언트에 추가
        client.addInterceptor(loggingInterceptor)

        if(retrofitClient == null) {
            // 레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build()) // 위에서 설장한 클라이언트로 레트로핏 클라이언트를 설정
                .build()
        }
        return retrofitClient
    }
}

// 인덱스로 지원금 조회 (조회수 증가) API 레트로핏 클라이언트
object SupportViewCountRC {
    // 레트로핏 클라이언트 선언
    private var retrofitClient: Retrofit? = null

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called/ supportView")

        // 로깅 인터셉터 추가

        // okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        // 로그를 찍기 위해 로깅 인터셉터 추가
        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{

            override fun log(message: String) {
                Log.d(TAG, "RetrofitClient4 - log() called / message: $message")

                when {
                    message.isJsonObject() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    else -> {
                        try {
                            Log.d(TAG, JSONObject(message).toString(4))
                        } catch (e: Exception) {
                            Log.d(TAG, message)
                        }
                    }
                }
            }
        })

        // 인터셉터 레벨 설정
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // 설정한 인터셉터를 클라잉언트에 추가
        client.addInterceptor(loggingInterceptor)

        if(retrofitClient == null) {
            // 레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build()) // 위에서 설장한 클라이언트로 레트로핏 클라이언트를 설정
                .build()
        }
        return retrofitClient
    }
}

object SupportOpenAPIRC {
    // 레트로핏 클라이언트 선언
    private var retrofitClient: Retrofit? = null

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called/ supportView")

        // 로깅 인터셉터 추가

        // okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        // 로그를 찍기 위해 로깅 인터셉터 추가
        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{

            override fun log(message: String) {
                Log.d(TAG, "RetrofitClient4 - log() called / message: $message")

                when {
                    message.isJsonObject() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    else -> {
                        try {
                            Log.d(TAG, JSONObject(message).toString(4))
                        } catch (e: Exception) {
                            Log.d(TAG, message)
                        }
                    }
                }
            }
        })

        // 인터셉터 레벨 설정
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // 설정한 인터셉터를 클라잉언트에 추가
        client.addInterceptor(loggingInterceptor)

        // 기본 파라메터 추가
//        val baseParameterInterceptor : Interceptor = (object : Interceptor{
//            override fun intercept(chain: Interceptor.Chain): Response {
//                Log.d(TAG, "intercept: ")
//                // 오리지널 리퀘스트
//                val originalRequest = chain.request()
//
//                // 쿼리 파라메터 추가하기
//                val addedUrl = originalRequest.url.newBuilder().addQueryParameter("client_id", API.OPI_SUPPORT_ID).build()
//
//                val finalRequest = originalRequest.newBuilder()
//                                            .url(addedUrl)
//                                            .method(originalRequest.method, originalRequest.body).build()
//                return chain.proceed(finalRequest)
//            }
//        })

        // 기본파라메터 인터셉터를 okhttp 클라이언트에 추가
//        client.addInterceptor(baseParameterInterceptor)

        if(retrofitClient == null) {
            val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()

            // 레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .client(client.build()) // 위에서 설장한 클라이언트로 레트로핏 클라이언트를 설정
                .build()
        }
        return retrofitClient
    }
}
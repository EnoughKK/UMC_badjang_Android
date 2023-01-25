package com.umc.badjang.LoginPage

import com.umc.badjang.LoginPage.models.KakaoOauthRequest
import com.umc.badjang.LoginPage.models.LoginRequest
import com.umc.badjang.LoginPage.models.LoginResponse
import com.umc.badjang.LoginPage.models.OAuthTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginRetrofit {

    @POST("/users/logIn") //로그인임
   fun requestLogin(@Body params: LoginRequest): Call<LoginResponse>

   @POST("/oauth/kakao") //카카오로그인
    fun getKakaoToken(@Body KakaoOauthRequest: KakaoOauthRequest): Call<OAuthTokenResponse>

    @GET("/oauth/authorize") //인가코드
    fun getkakaoAuth(@Query("response_type")param: String): Call<LoginResponse>

    @POST("/auth/kakaoLogin")
    fun kakaoLogin(@Query("accessToken") param: String): Call<LoginResponse>
}


/*import requests

class KakaoSignInCallbackView(View):
    def get(self, request):
        try:
            authorize_code = request.GET.get("code")
            app_key = KAKAO_KEY
            redirect_uri = "http://127.0.0.1:8000/account/sign-in/kakao/callback"

            token_request = requests.get(
                f"https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id={app_key}&redirect_uri={redirect_uri}&code={authorize_code}"
            )

            token_json = token_request.json()
            error = token_json.get("error", None)

            if error is not None:
                return JsonResponse({"message": "INVALID_CODE"}, status=400)

            access_token = token_json.get("access_token")

            return JsonResponse({'access_token': access_token}, status=200)

        except KeyError:
            return JsonResponse({"message": "INVALID_TOKEN"}, status=400)

        except access_token.DoesNotExist:
            return JsonResponse({"message": "INVALID_TOKEN"}, status=400)*/
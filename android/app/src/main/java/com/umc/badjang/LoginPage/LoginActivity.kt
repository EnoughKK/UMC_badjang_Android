package com.umc.badjang.LoginPage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.umc.badjang.ApplicationClass
import com.umc.badjang.LoginPage.Kakao.KakaoSignup
import com.umc.badjang.LoginPage.Login.FindIDActivity
import com.umc.badjang.LoginPage.Login.FindPWActivity
import com.umc.badjang.LoginPage.Login.LoginRetrofit
import com.umc.badjang.LoginPage.SignUp.SignUpActivity
import com.umc.badjang.LoginPage.Login.models.LoginRequest
import com.umc.badjang.LoginPage.Login.models.LoginResponse
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.databinding.ActivityLoginBinding
import retrofit2.*

class LoginActivity : AppCompatActivity(),View.OnClickListener {

    //firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth
    //google client
    private lateinit var googleSignInClient: GoogleSignInClient
    //private const val TAG = "GoogleActivity"
    private val RC_SIGN_IN = 99

    private lateinit var binding: ActivityLoginBinding// viewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩 초기화
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //LoginRetrofit으로 전역변수로 지정된 sRetrifit으로 연결
        val loginRetrofit = ApplicationClass.sRetrofit.create(LoginRetrofit::class.java)


        //HashKey확인
        val KeyHash = Utility.getKeyHash(this)
        Log.e("Hash", "--------------------------------------------------------")
        Log.e("Hash", "KeyHash: $KeyHash")


        // 회원가입 창으로
        binding.LoginSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        //아이디찾기 창으로
        binding.LoginFindID.setOnClickListener{
            startActivity(Intent(this, FindIDActivity::class.java))
       }
        //비밀번호찾기 창으로
       binding.LoginFindPW.setOnClickListener{
           startActivity(Intent(this, FindPWActivity::class.java))
       }

        // 로그인 버튼
        binding.LoginBtn.setOnClickListener {

            var email = binding.LoginEmail.text.toString()
            var password =binding.LoginPassword.text.toString()

            val loginRequest: LoginRequest = LoginRequest(user_email = email , user_password = password )

            loginRetrofit.requestLogin(loginRequest).enqueue(object :Callback<LoginResponse>{


                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                    var receive: LoginResponse? = response.body()


                    if(receive?.isSuccess==true){

                        if(receive?.result!=null){
                            val jwt = receive?.result.jwt
                            val useridx = receive?.result.user_idx


                            //토큰값 저장
                            ApplicationClass.sSharedPreferences.edit().putString("X-ACCESS-TOKEN", jwt).commit()
                            Log.e("토큰값 확인jwt","${jwt}")
                            //user idx 저장
                            ApplicationClass.bSharedPreferences.edit().putInt("USER-IDX",useridx).commit()
                            Log.e("useridx 확인","${useridx}")
                        }

                        // 정상적으로 통신이 성고된 경우
                        Log.d("로그인", "onResponse 성공: " + receive?.toString())



                        Toast.makeText(this@LoginActivity,"로그인 성공!",Toast.LENGTH_SHORT).show()
                        Intent(this@LoginActivity,MainActivity::class.java).apply{
                            startActivity(this)
                        }
                        finish()

                    }
                    else if (receive?.isSuccess==false){
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d("로그인", "onResponse 실패")
                        onLoginFailure(receive.code)
                    }

                /*result 사용
                *val sharedPref = getSharedPreferences(getString(R.string.shared_perference_user_info),Context.MODE_PRIVATE)
                *val result = sharedPref.getString(getString.R.string.user_token),"no data")
                * Log.d("로그","userToken:${userToken}")*/

                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("로그인","${t.localizedMessage}")
                }
            })


        }

        //카카오톡 로그인 버튼
        binding.LoginKakaoBtn.setOnClickListener {
           kakaoLogin()
         //이거 아닌듯 = startActivity(Intent(this,KakaoWebViewActivity::class.java))
        }



        // 구글 로그인 버튼
        binding.LoginGoogleBtn.setOnClickListener { googleLogin() }

        //Google 로그인 옵션 구성. requestIdToken 및 Email 요청
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            //'R.string.default_web_client_id' 에는 본인의 클라이언트 아이디를 넣어주시면 됩니다.
            //저는 스트링을 따로 빼서 저렇게 사용했지만 스트링을 통째로 넣으셔도 됩니다.
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //firebase auth 객체
        firebaseAuth = FirebaseAuth.getInstance()

    }

    // 로그아웃하지 않을 시 자동 로그인 , 회원가입시 바로 로그인 됨
    public override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account !== null) { // 이미 로그인 되어있을시 바로 메인 액티비티로 이동
            toMainActivity(firebaseAuth.currentUser)
        }
    } //onStart End

    // onActivityResult
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("LoginActivity", "Google sign in failed", e)
            }
        }
    } // onActivityResult End

    // firebaseAuthWithGoogle
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.id!!)

        //Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.w("LoginActivity", "firebaseAuthWithGoogle 성공", task.exception)
                    toMainActivity(firebaseAuth?.currentUser)
                } else {
                    Log.w("LoginActivity", "firebaseAuthWithGoogle 실패", task.exception)
                    Toast.makeText(this@LoginActivity, "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show()
                    //Snackbar.make(activity_login, "로그인에 실패하였습니다.", Snackbar.LENGTH_SHORT).show()
                }
            }
    }// firebaseAuthWithGoogle END

    // toMainActivity
    fun toMainActivity(user: FirebaseUser?) {
        if (user != null) { // MainActivity 로 이동
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    } // toMainActivity End

    // signIn
    private fun googleLogin() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // signIn End

    override fun onClick(p0: View?) {
    }

    private fun signOut() { // 로그아웃
        // Firebase sign out
        firebaseAuth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            //updateUI(null)
        }
    }

    private fun revokeAccess() { //회원탈퇴
        // Firebase sign out
        firebaseAuth.signOut()
        googleSignInClient.revokeAccess().addOnCompleteListener(this) {

        }
    }

    private fun kakaoLogin(){
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("LOGIN", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("LOGIN", "카카오계정으로 로그인 성공 ${token.accessToken}")
                val kakaoSignup = KakaoSignup(token.accessToken)
                kakaoSignup.PostAccessToken()//액세스 토큰 보내기

                //여기에서 엑세스 토큰을 받았는데, jwt가 없으면, 이 엑세스 토큰을 서버에 넘겨주는 과정 필요
                //이 엑세스 토큰을 서버에 넘겨주면, response로 jwt를 받는다. jwt가 있으면 자동 로그인시킨다.
                //카카오용 jwt를 따로 만들어서 저장하고, jwt 검사해서 만약 없으면, 회원가입 창으로 넘어가고,
                //회원가입 창에서는 유저 정보를 입력해서 넘겨준다. - 그냥 회원 가입 하기
                //있으면 자동으로 로그인 시킨다.
                //저장되어있는 jwt가 없으면 추가정보 입력 화면으로 이동

                if(ApplicationClass.sSharedPreferences.getString("J-ACCESS-TOKEN","")==""){
                    Log.e("로그인 jwt 없음", "jwt 없음")
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                //저장되어있는 jwt가 있으면, 바로 자동 로그인
                else{
                    val kakao_jwt1 = ApplicationClass.sSharedPreferences.getString("J-ACCESS-TOKEN",null)
                    ApplicationClass.sSharedPreferences.edit().putString("X-ACCESS-TOKEN",kakao_jwt1).commit()
                    dialogShow("추가 정보 입력",binding.loginToSns,false)
                }
            }
        }

        /*UserApiClient.instance.unlink { error ->
            if (error != null) {
                Toast.makeText(this, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                //val intent = Intent(this, MainActivity::class.java)
                //startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                //finish()
            }
        }*/

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity) { token, error ->
                if (error != null) {
                    Log.e("LOGIN", "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = callback)
                } else if (token != null) {
                    Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    val kakaoSignup = KakaoSignup(token.accessToken)
                    kakaoSignup.PostAccessToken()//액세스 토큰 보내기

                    //여기에서 엑세스 토큰을 받았는데, jwt가 없으면, 이 엑세스 토큰을 서버에 넘겨주는 과정 필요
                    //이 엑세스 토큰을 서버에 넘겨주면, response로 jwt를 받는다. jwt가 있으면 자동 로그인시킨다.
                    //카카오용 jwt를 따로 만들어서 저장하고, jwt 검사해서 만약 없으면, 회원가입 창으로 넘어가고,
                    //회원가입 창에서는 유저 정보를 입력해서 넘겨준다. - 그냥 회원 가입 하기
                    //있으면 자동으로 로그인 시킨다.
                    //저장되어있는 jwt가 없으면 추가정보 입력 화면으로 이동

                    if(ApplicationClass.sSharedPreferences.getString("J-ACCESS-TOKEN","")==""){
                        Log.e("로그인 jwt 없음", "jwt 없음")
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                    //저장되어있는 jwt가 있으면, 바로 자동 로그인
                    else{
                        val kakao_jwt2 = ApplicationClass.sSharedPreferences.getString("J-ACCESS-TOKEN",null)
                        ApplicationClass.sSharedPreferences.edit().putString("X-ACCESS-TOKEN",kakao_jwt2).commit()
                        dialogShow("추가 정보 입력",binding.loginToSns,false)
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }


    fun onLoginFailure(code: Int) {
        when(code) {

            2020 -> {
                Toast.makeText(this@LoginActivity, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                binding.LoginEmail.setText(null)
                binding.LoginPassword.setText(null)
                onStart()
            }
            2021 -> {
                Toast.makeText(this@LoginActivity, "이메일 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                binding.LoginEmail.setText(null)
                binding.LoginPassword.setText(null)
                onStart()
            }
            2030 -> {
                Toast.makeText(this@LoginActivity, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                binding.LoginEmail.setText(null)
                binding.LoginPassword.setText(null)
                onStart()
            }
            2100 -> {
                Toast.makeText(this@LoginActivity, "JWT를 입력해주세요.", Toast.LENGTH_SHORT).show()
                binding.LoginEmail.setText(null)
                binding.LoginPassword.setText(null)
                onStart()
            }
            2101 -> {
                Toast.makeText(this@LoginActivity, "유효하지 않은 JWT입니다.", Toast.LENGTH_SHORT).show()
                binding.LoginEmail.setText(null)
                binding.LoginPassword.setText(null)
                onStart()
            }
            3000 -> {
                Toast.makeText(this@LoginActivity, "값을 불러오는데 실패하였습니다.", Toast.LENGTH_SHORT).show()
                binding.LoginEmail.setText(null)
                binding.LoginPassword.setText(null)
                onStart()
            }
            3100 -> {
                Toast.makeText(this@LoginActivity, "없는 아이디거나 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                binding.LoginEmail.setText(null)
                binding.LoginPassword.setText(null)
                onStart()
            }
            4000 -> {
                Toast.makeText(this@LoginActivity, "데이터베이스 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                binding.LoginEmail.setText(null)
                binding.LoginPassword.setText(null)
                onStart()
            }
            4011 -> {
                Toast.makeText(this@LoginActivity, "비밀번호 암호화에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                binding.LoginEmail.setText(null)
                binding.LoginPassword.setText(null)
                onStart()
            }
        }
    }
    //키보드 내려가게 해줌
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

    //다이얼로그 띄우는 함수
    private fun dialogShow(msg: String, focus: View?, success: Boolean){
        val dialog = SnsDialog(msg)
        // 버튼 클릭 이벤트 설정
        dialog.setButtonClickListener(object: SnsDialog.OnButtonClickListener {
            override fun onButtonClicked() {
                //팝업창이 닫힐 때 실행되었으면 하는 코드 넣기 - 포커스 이동
                focus?.requestFocus()
                if(success){
                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    //지금 까지 쌓여있는 모든 액티비티 지우기
                   // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
        })
        dialog.show(supportFragmentManager, "SnsDialog")
    }


}


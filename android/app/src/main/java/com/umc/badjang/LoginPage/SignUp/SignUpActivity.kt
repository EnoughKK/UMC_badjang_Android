package com.umc.badjang.LoginPage.SignUp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.umc.badjang.ApplicationClass
import com.umc.badjang.LoginPage.LoginActivity
import com.umc.badjang.LoginPage.MyDialog
import com.umc.badjang.LoginPage.SignUp.models.SignUpRequest
import com.umc.badjang.LoginPage.models.SignUpResponse
import com.umc.badjang.R
import com.umc.badjang.databinding.ActivitySignupBinding
import com.umc.badjang.databinding.DialogTermtodisagreeBinding
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding// viewBinding
    private lateinit var dialogbinding: DialogTermtodisagreeBinding //dialog fragment

    private var count by Delegates.notNull<Int>() //모두
    private var count1 by Delegates.notNull<Int>() //나이
    private var count2 by Delegates.notNull<Int>() //서비스
    private var count3 by Delegates.notNull<Int>() //개인정보
    private var count4 by Delegates.notNull<Int>() //알림
    private var term_flag by Delegates.notNull<Int>() //받은 데이터 서비스
    private var privacy_flag by Delegates.notNull<Int>() //받은 데이터 개인정보

    private lateinit var string : String //알람 동의를 위한 스트링
    private lateinit var dialog : Dialog //다이얼로그

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        // 바인딩 초기화
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //LoginRetrofit으로 전역변수로 지정된 sRetrifit으로 연결
        val signUpRetrofit = ApplicationClass.sRetrofit.create(SignUpRetrofit::class.java)


        //이전 창으로 이동
        binding.SignupUpBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        count = 0 //모두
        count1 =0 //나이
        count2=0 //서비스
        count3=0 //개인정보
        count4=0 //알림

        term_flag= intent.getIntExtra("Term_Btn",count2).toInt()
        privacy_flag= intent.getIntExtra("Privacy_Btn",count3).toInt()

        Log.e("서비스","${term_flag}")
        Log.e("개인정보","${privacy_flag}")
        Log.e("서비스","${count2}")
        Log.e("개인정보","${count3}")

        //모두
        binding.SignupAllDisagree.setOnClickListener {
            binding.SignupAllAgree.visibility = View.VISIBLE
            binding.SignupAllDisagree.visibility = View.INVISIBLE
            binding.SignupAgree1.visibility = View.VISIBLE
            binding.SignupDisagree1.visibility = View.INVISIBLE
            binding.SignupAgree2.visibility = View.VISIBLE
            binding.SignupDisagree2.visibility = View.INVISIBLE
            binding.SignupAgree3.visibility = View.VISIBLE
            binding.SignupDisagree3.visibility = View.INVISIBLE
            binding.SignupAgree4.visibility = View.VISIBLE
            binding.SignupDisagree4.visibility = View.INVISIBLE
            count = 1
            count1 = 1
            count2 = 1
            count3 = 1
            count4 = 1
        }

        binding.SignupAllAgree.setOnClickListener {
            binding.SignupAllAgree.visibility = View.INVISIBLE
            binding.SignupAllDisagree.visibility = View.VISIBLE
            binding.SignupAgree1.visibility = View.INVISIBLE
            binding.SignupDisagree1.visibility = View.VISIBLE
            binding.SignupAgree2.visibility = View.INVISIBLE
            binding.SignupDisagree2.visibility = View.VISIBLE
            binding.SignupAgree3.visibility = View.INVISIBLE
            binding.SignupDisagree3.visibility = View.VISIBLE
            binding.SignupAgree4.visibility = View.INVISIBLE
            binding.SignupDisagree4.visibility = View.VISIBLE
            count =0
            count1=0
            count2=0
            count3=0
            count4=0
        }

        if(count2==1){
            binding.SignupAgree2.visibility = View.VISIBLE
            binding.SignupDisagree2.visibility = View.INVISIBLE
            count2=1
        }
        if(count3==1){
            binding.SignupAgree3.visibility = View.VISIBLE
            binding.SignupDisagree3.visibility = View.INVISIBLE
            count3=1
        }


        //나이
        binding.SignupDisagree1.setOnClickListener {
            binding.SignupAgree1.visibility = View.VISIBLE
            binding.SignupDisagree1.visibility = View.INVISIBLE
            count1=1

        }
        binding.SignupAgree1.setOnClickListener {
            binding.SignupAgree1.visibility = View.INVISIBLE
            binding.SignupDisagree1.visibility = View.VISIBLE
            count1=0
            if(count==1){
                binding.SignupAllAgree.visibility = View.INVISIBLE
                binding.SignupAllDisagree.visibility = View.VISIBLE
                count=0
            }
        }
        //서비스
        binding.SignupDisagree2.setOnClickListener {
            binding.SignupAgree2.visibility = View.VISIBLE
            binding.SignupDisagree2.visibility = View.INVISIBLE
            count2=1
        }
        binding.SignupAgree2.setOnClickListener {
            binding.SignupAgree2.visibility = View.INVISIBLE
            binding.SignupDisagree2.visibility = View.VISIBLE
            count2=0
            if(count==1){
                binding.SignupAllAgree.visibility = View.INVISIBLE
                binding.SignupAllDisagree.visibility = View.VISIBLE
                count=0
            }
        }
        //개인정보
        binding.SignupDisagree3.setOnClickListener {
            binding.SignupAgree3.visibility = View.VISIBLE
            binding.SignupDisagree3.visibility = View.INVISIBLE
            count3=1
        }
        binding.SignupAgree3.setOnClickListener {
            binding.SignupAgree3.visibility = View.INVISIBLE
            binding.SignupDisagree3.visibility = View.VISIBLE
            count3=0
            if(count==1){
                binding.SignupAllAgree.visibility = View.INVISIBLE
                binding.SignupAllDisagree.visibility = View.VISIBLE
                count=0
            }
        }
        //알람 동의
        binding.SignupDisagree4.setOnClickListener {
            binding.SignupAgree4.visibility = View.VISIBLE
            binding.SignupDisagree4.visibility = View.INVISIBLE
            count4=1
        }
        binding.SignupAgree4.setOnClickListener {
            binding.SignupAgree4.visibility = View.INVISIBLE
            binding.SignupDisagree4.visibility = View.VISIBLE
            count4=0
            if(count==1){
                binding.SignupAllAgree.visibility = View.INVISIBLE
                binding.SignupAllDisagree.visibility = View.VISIBLE
                count=0
            }
        }


        // 서비스 이용약관 동의 창
        binding.SignupAgreetext2.setOnClickListener {
            startActivity(Intent(this, TermofUseActivity::class.java))
            if(TermofUseActivity().activity1 == null){
                count2=1
            }
        }

        // 개인정보 수집 및 이용 동의 창
        binding.SignupAgreetext3.setOnClickListener {
            startActivity(Intent(this, PrivacyActivity::class.java))
            if(PrivacyActivity().activity2 == null){
                count3=1
            }
        }

       binding.SignupBtn.setOnClickListener {

           binding.SignupScroll.scrollTo(0, binding.SignupPW.top)

           var email = binding.SignupEmail.text.toString()
           var password =binding.SignupPW.text.toString()
           var name = binding.SignupName.text.toString()
           var birthdate=binding.SignupBirthDate.text.toString()
           var phoneNo=binding.SignupPhone.text.toString()
           var type = "Regular"

           /*(필수)항목 미체크시 다이얼로그 출력*/
           if(count1 == 0){
               Log.d("회원가입","나이 출력")
               dialogShow("'만 14세 이상'에\n" + "동의해주세요",binding.SignupAgreetext1,false)
           }
           if(count2 == 0){
               Log.d("회원가입","서비스 출력")
               dialogShow("서비스 이용 약관에\n" + "동의해주세요",binding.SignupAgreetext2,false)
           }
           if(count3 == 0){
               Log.d("회원가입","개인정보 출력")
               dialogShow("개인정보 수집 및 이용 약관에\n" + "동의해주세요",binding.SignupAgreetext3,false)
           }

           //오류 검사
           if (!validEmail()or !validPassword()or!validConfirmPassword()or!validName()or!validBirthdate()or!validBirthdate()or!validPhone()  or (count1==0) or (count2==0)or(count3==0)) {

               return@setOnClickListener
           }

           Log.e("회원가입","${count}${count1}${count2}${count3}${count4}")

            //알람 동의 및 미동의
           if(count4==0){
               string="N"
           }
           if(count4==1){
               string="Y"
           }

           val push_yn=string.toString()


           val signUpRequest: SignUpRequest = SignUpRequest(user_email = email , user_password = password , user_name = name, user_birth = birthdate, user_phone = phoneNo, user_type = type, user_push_yn = push_yn)

           signUpRetrofit.requestSignup(signUpRequest).enqueue(object :Callback<SignUpResponse>{
               override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                   val receive_signup: SignUpResponse? = response.body()

                   if(receive_signup?.isSuccess==true){

                       if(receive_signup?.result!=null){
                           val jwt = receive_signup.result.jwt
                           //토큰값 저장
                           val sharedPref = getSharedPreferences(getString(R.string.shared_preference_user_info),Context.MODE_PRIVATE)
                           with(sharedPref.edit()){
                               putString(getString(R.string.user_token), jwt.toString())
                               apply()
                           }
                       }

                       // 정상적으로 통신이 성고된 경우
                       Log.d("회원가입", "onResponse 성공: " + receive_signup?.toString())


                       Toast.makeText(this@SignUpActivity,"회원가입이 완료되었습니다!",Toast.LENGTH_SHORT).show()
                       Intent(this@SignUpActivity,LoginActivity::class.java).apply{
                           startActivity(this)
                       }
                       finish()

                   }
                   else if (receive_signup?.isSuccess==false){
                       // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                       Log.d("회원가입", "onResponse 실패")
                       onSignUpFailure(receive_signup.code)

                   }
               }

               override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                   Log.e("회원가입","${t.localizedMessage}")
               }
           })
       }


    }

    override fun onRestart() {
        super.onRestart()
        if(count2==1)
        {
            binding.SignupAgree2.visibility=View.VISIBLE
            binding.SignupDisagree2.visibility=View.INVISIBLE
            count2==1
        }
        if(count3==1){
            binding.SignupAgree3.visibility=View.VISIBLE
            binding.SignupDisagree3.visibility=View.INVISIBLE
            count3==1
        }
    }


    fun onSignUpFailure(code: Int) {
        when(code) {

            2020 -> {
                Toast.makeText(this@SignUpActivity, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            2021 -> {
                Toast.makeText(this@SignUpActivity, "이메일 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            2022 -> {
                Toast.makeText(this@SignUpActivity, "중복된 이메일입니다.", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            2030 -> {
                Toast.makeText(this@SignUpActivity, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            2031 -> {
                Toast.makeText(this@SignUpActivity, "비밀번호에는 문자, 특수문자, 숫자가 포함되어야 합니다. (8 ~ 15자)", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            2040 -> {
                Toast.makeText(this@SignUpActivity, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            2041 -> {
                Toast.makeText(this@SignUpActivity, "이름은 2 ~ 20자 사이로 입력해주세요.", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            2050 -> {
                Toast.makeText(this@SignUpActivity, "생년월일을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            2051 -> {
                Toast.makeText(this@SignUpActivity, "생년월일 형식에 맞게 입력해주세요.", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            2052 -> {
                Toast.makeText(this@SignUpActivity, "만 14세 미만은 가입하실 수 없습니다,", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            2060 -> {
                Toast.makeText(this@SignUpActivity, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            2061 -> {
                Toast.makeText(this@SignUpActivity, "전화번호 형식에 맞게 입력해주세요.", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            3000 -> {
                Toast.makeText(this@SignUpActivity, "값을 불러오는데 실패하였습니다.", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            4000 -> {
                Toast.makeText(this@SignUpActivity, "데이터베이스 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            4001 -> {
                Toast.makeText(this@SignUpActivity, "서버와의 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                //binding.LoginEmail.setText(null)
                //binding.LoginPassword.setText(null)
                onStart()
            }
            4011 -> {
                Toast.makeText(this@SignUpActivity, "비밀번호 암호화에 실패하였습니다.", Toast.LENGTH_SHORT).show()
               // binding.LoginEmail.setText(null)
               // binding.LoginPassword.setText(null)
                onStart()
            }
        }
    }

    private fun validEmail(): Boolean {
        val value: String = binding.SignupEmail.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        return if (value.isEmpty()) {
            binding.SignupMissEmail.text = "이메일을 입력해주세요."
            binding.SignupMissEmail.visibility = View.VISIBLE
            false
        } else if (!value.matches(emailPattern.toRegex())) {
            binding.SignupMissEmail.text = "이메일 형식에 맞게 입력해주세요."
            binding.SignupMissEmail.visibility = View.VISIBLE
            false
        } else {
            binding.SignupEmail.isEnabled = false
            binding.SignupMissEmail.visibility = View.INVISIBLE
            binding.SignupEmail.error = null
            true
        }
    }

    private fun validPassword():Boolean{
        val value: String = binding.SignupPW.text.toString()
        val passwordPattern = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""

        return if (value.isEmpty()) {
            binding.SignupMissPW.text = "비밀번호를 입력해주세요."
            binding.SignupMissPW.visibility = View.VISIBLE
            false
        } else if (!value.matches(passwordPattern.toRegex())) {
            binding.SignupMissPW.text = "비밀번호에는 문자, 특수문자, 숫자가 포함되어야합니다"
            binding.SignupMissPW.visibility = View.VISIBLE
            false
        } else {
            binding.SignupPW.isEnabled = false
            binding.SignupMissPW.visibility = View.INVISIBLE
            binding.SignupPW.error = null
            true
        }
    }


    private fun validConfirmPassword():Boolean{
        val value: String = binding.SignupConfirmPW.text.toString()

        return if(value.isBlank()){
            binding.SignupMissconfirmPW.text = "비밀번호 확인를 입력해주세요."
            binding.SignupMissconfirmPW.visibility = View.VISIBLE
            false
        } else if(value!= binding.SignupPW.text.toString()||value.length < 8 || value.length > 15){
            binding.SignupMissconfirmPW.text = "비밀번호가 일치하지 않습니다"
            binding.SignupMissconfirmPW.visibility = View.VISIBLE
            false
        } else{
            binding.SignupConfirmPW.isEnabled = false
            binding.SignupMissconfirmPW.visibility = View.INVISIBLE
            binding.SignupConfirmPW.error = null
            true
        }
    }

    private fun validName():Boolean {
        val value: String = binding.SignupName.text.toString()

        return if (value.isBlank()) {
            binding.SignupMissName.text = "이름을 입력해주세요."
            binding.SignupMissName.visibility = View.VISIBLE
            false
        } else if (value.length < 2 || value.length > 20) {
            binding.SignupMissName.text = "이름은 2~20자 사이로 입력해주세요"
            binding.SignupMissName.visibility = View.VISIBLE
            false
        } else {
            binding.SignupName.isEnabled = false
            binding.SignupMissName.visibility = View.INVISIBLE
            binding.SignupName.error = null
            true
        }
    }

    private fun validBirthdate():Boolean{
        val value: String = binding.SignupBirthDate.text.toString()

        return if (value.isBlank()) {
            binding.SignupMissBirthDate.text = "생년월일을 입력해 주세요."
            binding.SignupMissBirthDate.visibility = View.VISIBLE
            false
        } else if (value.length ==8) {
            binding.SignupBirthDate.isEnabled = false
            binding.SignupMissBirthDate.visibility = View.INVISIBLE
            binding.SignupBirthDate.error = null
            true
        } else {
            binding.SignupMissBirthDate.text = "생년월일 형식에 맞게 입력해주세요"
            binding.SignupMissBirthDate.visibility = View.VISIBLE
            false
        }
    }


    private fun validPhone():Boolean{

        val value: String = binding.SignupPhone.text.toString()
        val phonePattern = ".*[0-9].*"

        return if (value.isBlank()) {
            binding.SignupMissPhone.text = "전화번호를 입력해 주세요."
            binding.SignupMissPhone.visibility = View.VISIBLE
            false
        } else if (!value.matches(phonePattern.toRegex())||value.length != 11) {
            binding.SignupMissPhone.text = "전화번호 형식에 맞게 입력해주세요"
            binding.SignupMissPhone.visibility = View.VISIBLE
            false
        } else {
            binding.SignupPhone.isEnabled = false
            binding.SignupMissPhone.visibility = View.INVISIBLE
            binding.SignupPhone.error = null
            true
        }
    }

    //다이얼로그 띄우는 함수
    private fun dialogShow(msg: String, focus: View?, success: Boolean){
        val dialog = MyDialog(msg)
        // 버튼 클릭 이벤트 설정
        dialog.setButtonClickListener(object: MyDialog.OnButtonClickListener {
            override fun onButtonClicked() {
                //팝업창이 닫힐 때 실행되었으면 하는 코드 넣기 - 포커스 이동
                focus?.requestFocus()
                if(success){
                    finish()
                }
            }
        })
        dialog.show(supportFragmentManager, "MyDialog")
    }

    //키보드 내려가게 해줌
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }
}
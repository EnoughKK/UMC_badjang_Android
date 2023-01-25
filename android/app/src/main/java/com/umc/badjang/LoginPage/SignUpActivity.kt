package com.umc.badjang.LoginPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.umc.badjang.LoginPage.models.SignUpResponse
import com.umc.badjang.MainActivity
import com.umc.badjang.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {

    var SignUp : SignUpResponse? = null

    private lateinit var binding: ActivitySignupBinding// viewBinding


    private var idFlag =false
    private var passwordFlag =false
    private var passwordCheckFlag=false
    private var nameFlag =false
    private var birthFlag=false
    private var phoneFlag=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩 초기화
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var retrofit =Retrofit.Builder()
                .baseUrl("https://prod.badjang2023.shop/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        var signUpService:SignUpRetrofit = retrofit.create(SignUpRetrofit::class.java)


        //이전 창으로 이동
        binding.SignupBtn.setOnClickListener {
           createAccount()
        }

       binding.SignupBtn.setOnClickListener {
           //val emailResult = View.binding.SignupEmail.validEmail()



           var email = binding.SignupEmail.text.toString()
           var password =binding.SignupPW.text.toString()
           var name = binding.SignupName.text.toString()
           var birthdate=binding.SignupBirthDate.text.toString()
           var phoneNo=binding.SignupPhone.text.toString()
           //var type = binding. .text.toString()
           //var push_yn=binding.text.toString()



           //회원가입 액션
           Toast.makeText(this,"회원가입이 완료되었습니다",Toast.LENGTH_SHORT).show()


       }




        // 서비스 이용약관 동의 창
        binding.SignupAgreetext2.setOnClickListener {
            startActivity(Intent(this, TermofUseActivity::class.java))
        }

        // 개인정보 수집 및 이용 동의 창
        binding.SignupAgreetext3.setOnClickListener {
            startActivity(Intent(this, PrivacyActivity::class.java))
        }

        //가입 성공과 학교선택?
        binding.SignupBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }




    }

    private fun createAccount() {
        TODO("Not yet implemented")
        val email = binding.SignupEmail.text.toString()
        val password = binding.SignupPW.text.toString()
        val name = binding.SignupName.text.toString()
        val birthdate = binding.SignupBirthDate.text.toString()
        val phoneNo = binding.SignupPhone.text.toString()
        //val agreeNo = binding.SignupAllAgree.text.toString()

        //맞다면?
        saveuserInfo(email,password,name,birthdate,phoneNo)
    }

    private fun saveuserInfo(email:String,password:String,name:String,birthdate:String,phoneNo:String){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    fun TextInputEditText.vaildEmail(): Boolean{
        val textInputLayout=this.parent.parent as TextInputLayout
        val input = this.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(input).matches()){
            textInputLayout.helperText="이메일 형식에 맞게 입력해주세요."
            return false
        }else{
            textInputLayout.helperText=""
        }
        return true
    }

    fun TextInputEditText.validPhone():Boolean{
        val textInputLayout=this.parent.parent as TextInputLayout
        val input=this.text.toString()
        if (!input.matches(".*[0-9].*".toRegex())) {
            textInputLayout.helperText= "전화번호 형식에 맞게 입력해주세요."
            return false
        }
        if (input.length != 11) {
            textInputLayout.helperText="전화번호 형식에 맞게 입력해주세요."
            return false
        }
        if(input.length==11&&input.matches(".*[0-9].*".toRegex())){
            textInputLayout.helperText=""
        }
        return true
    }

    fun TextInputEditText.validPassword():Boolean{
        val textInputLayout=this.parent.parent as TextInputLayout
        val input=this.text.toString()
        if(input.length<8){
            textInputLayout.helperText="비밀번호에는 문자, 특수문자, 숫자가 포함되어야 합니다."
            return false
        }
        if(input.length>16){
            textInputLayout.helperText="비밀번호에는 문자, 특수문자, 숫자가 포함되어야 합니다."
            return false
        }
        if(input.length in 8..16){
            textInputLayout.helperText=""
        }
        return true
    }

    fun TextInputEditText.validPasswordForUpdate():Boolean{
        val textInputLayout=this.parent.parent as TextInputLayout
        val input=this.text.toString()
        if(input.isBlank()){
            return true
        }
        if(input.length in 1..7){
            textInputLayout.helperText="Must be minimum 8 character."
            return false
        }
        if(input.length>16){
            textInputLayout.helperText="Must be maximum 16 character."
            return false
        }
        if(input.length in 8..16){
            textInputLayout.helperText=""
        }
        return true
    }

    fun TextInputEditText.validName():Boolean{
        val textInputLayout=this.parent.parent as TextInputLayout
        val input=this.text.toString()
        if(input.isBlank()){
            textInputLayout.helperText="이름은 2 ~ 20자 사이로 입력해주세요."
            return false
        }
        if(input.length<2){
            textInputLayout.helperText="이름은 2 ~ 20자 사이로 입력해주세요."
            return false
        }
        if(input.length>21){
            textInputLayout.helperText="이름은 2 ~ 20자 사이로 입력해주세요."
            return false
        }
        if(input.isNotBlank() && input.length>2){
            textInputLayout.helperText=""
        }
        return true
    }



}
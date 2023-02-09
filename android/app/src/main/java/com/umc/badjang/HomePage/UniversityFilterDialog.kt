package com.umc.badjang.HomePage

import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.umc.badjang.ApplicationClass
import com.umc.badjang.HomePagaApi.MainApiClient
import com.umc.badjang.HomePagaApi.UniversityFilterAddData
import com.umc.badjang.HomePagaApi.UniversityFilterApiService
import com.umc.badjang.HomePagaApi.UniversityFilterResponse
import com.umc.badjang.R
import com.umc.badjang.databinding.DialogScholarshipLookupBinding
import com.umc.badjang.mConnectUserId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class UniversityFilterDialog(context: Context, activity: Activity) : Dialog(context) {
    private lateinit var binding: DialogScholarshipLookupBinding
    private val activity = activity

    // api 통신을 위한 retrofit
    private var retrofit: Retrofit? = null

    // Toast
    private var mToast: Toast? = null

    // 학교, 단과대학, 학과 선택 정보
    private var selectUniversity: String? = null
    private var selectCollege: String? = null
    private var selectDepartment: String? = null

    // 학년, 학기 선택 정보
    private var selectGrade: String? = null
    private var selectSemester: String? = null

    // 도, 시군구 선택 정보
    private var selectProvince: String? = null
    private var selectCity: String? = null

    private var universityInfo: MutableList<UniversityInfo>? = null
    private var collegeNameList: MutableList<String>? = null // 단과대학 리스트
    private var departmentList: MutableList<String>? = null  // 학과 리스트
    private var cityNameList: MutableList<String>? = null    // 시군구 리스트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogScholarshipLookupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        // 현재 로그인 된 사용자 idx 조회
        mConnectUserId = ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0)

        // retrofit 세팅
        retrofit = MainApiClient.mainApiRetrofit

        // 학교 선택 관련 -------------------------------------------------------------------------------------------
        // json 파일에서 학교 정보 받아오기
        val jsonUniversityString = getJsonUniversityInfo()
        val universityGson = Gson()
        val listUniversityType = object : TypeToken<UniversitySelectData>() {}. type
        var universitys: UniversitySelectData = universityGson.fromJson(jsonUniversityString, listUniversityType)

        val universityResults: MutableList<UniversityResults> = universitys.results

        // 학교 선택 리스트
        val universityNameList = mutableListOf<String>()
        universityNameList.add("선택하기")
        for(i:Int in (0..universityResults.size - 1)) {
            universityNameList.add(universityResults[i].university_name)
        }
        val universityAdapter = ArrayAdapter(context, R.layout.custom_spinner_item, universityNameList)
        binding.spinnerSearchSchool.adapter = universityAdapter

        // 학교 선택하기 누르면 spinner 리스트
        binding.spinnerSearchSchool.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 선택된 학교
                selectUniversity = binding.spinnerSearchSchool.getSelectedItem().toString()
                selectCollege = null
                selectDepartment = null

                // 선택된 학교의 단과대학 정보 가져오기
                universityInfo = mutableListOf<UniversityInfo>()
                for(i:Int in (0..universityResults.size - 1)) {
                    if(universityResults[i].university_name == selectUniversity) {
                        universityInfo = universityResults[i].university_info
                        break
                    }
                }

                if(selectUniversity == "선택하기")
                    selectUniversity = null

                // 단과대학 선택 리스트
                collegeNameList = mutableListOf<String>()
                collegeNameList!!.add("선택하기")
                for(i:Int in (0..universityInfo!!.size - 1)) {
                    collegeNameList!!.add(universityInfo!![i].college_name)
                }

                val collegeAdapter = ArrayAdapter(context, R.layout.custom_spinner_item, collegeNameList!!)
                binding.selectionSchool.adapter = collegeAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectUniversity = null
                selectCollege = null
                selectDepartment = null
            }
        }

        // 단과대학 선택하기 누르면 spinner 리스트
        binding.selectionSchool.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 선택된 단과대학
                selectCollege = binding.selectionSchool.getSelectedItem().toString()
                selectDepartment = null

                // 선택된 단과대학의 학교 정보 가져오기 - 학교 선택 리스트
                departmentList = mutableListOf<String>()
                departmentList!!.add("선택하기")
                for(i:Int in (0..universityInfo!!.size - 1)) {
                    if(universityInfo!![i].college_name == selectCollege) {
                        departmentList!!.addAll(universityInfo!![i].university_department)
                        break
                    }
                }

                if(selectCollege == "선택하기")
                    selectCollege = null

                val departmentAdapter = ArrayAdapter(context, R.layout.custom_spinner_item, departmentList!!)
                binding.selectionMajor.adapter = departmentAdapter

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectCollege = null
                selectDepartment = null
            }
        }

        // 학과 선택하기 누르면 spinner 리스트
        binding.selectionMajor.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 선택된 학과 정보
                selectDepartment = binding.selectionMajor.getSelectedItem().toString()

                if(selectDepartment == "선택하기")
                    selectDepartment = null
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectDepartment = null
            }
        }


        // 학년 선택 리스트
        val gradeList = mutableListOf<String>()
        gradeList.add("선택하기")
        for(i:Int in (1..4)) {
            gradeList.add(i.toString()+"학년")
        }
        val gradeAdapter = ArrayAdapter(context, R.layout.custom_spinner_item, gradeList)
        binding.selectionGrade.adapter = gradeAdapter

        // 학년 선택하기 누르면 spinner 리스트
        binding.selectionGrade.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 선택된 학년 정보
                selectGrade = binding.selectionGrade.getSelectedItem().toString().substring(0, 1)

                if(selectGrade == "선택하기")
                    selectGrade = null
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectGrade = null
            }
        }

        // 학기 선택 리스트
        val semesterList = mutableListOf<String>()
        semesterList.add("선택하기")
        semesterList.add("1학기")
        semesterList.add("2학기")

        val semesterAdapter = ArrayAdapter(context, R.layout.custom_spinner_item, semesterList)
        binding.selectionSemester.adapter = semesterAdapter

        // 학기 선택하기 누르면 spinner 리스트
        binding.selectionSemester.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 선택된 학기 정보
                selectSemester = binding.selectionSemester.getSelectedItem().toString().substring(0, 1)

                if(selectSemester == "선택하기")
                    selectSemester = null
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectSemester = null
            }
        }

        // 지역 선택 관련 -------------------------------------------------------------------------------------------
        // json 파일에서 학교 정보 받아오기
        val jsonAreaString = getJsonAreaInfo()
        val areaGson = Gson()
        val listAreaType = object : TypeToken<AreaSelectData>() {}. type
        var areas: AreaSelectData = areaGson.fromJson(jsonAreaString, listAreaType)

        val areaResults: MutableList<AreaResults> = areas.results

        // 도 선택 리스트
        val provinceNameList = mutableListOf<String>()
        provinceNameList.add("선택하기")
        for(i:Int in (0..areaResults.size - 1)) {
            provinceNameList.add(areaResults[i].province_name)
        }
        val provinceAdapter = ArrayAdapter(context, R.layout.custom_spinner_item, provinceNameList)
        binding.selectionLocal1.adapter = provinceAdapter

        // 도 선택하기 누르면 spinner 리스트
        binding.selectionLocal1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 선택된 도
                selectProvince = binding.selectionLocal1.getSelectedItem().toString()
                selectCity = null

                // 시/군/구 선택 리스트 - 선택된 도의 시군구 정보 가져와서
                cityNameList = mutableListOf<String>()
                cityNameList!!.add("선택하기")
                for(i:Int in (0..areaResults.size - 1)) {
                    if(areaResults[i].province_name == selectProvince) {
                        if(areaResults[i].city_name != null)
                            cityNameList!!.addAll(areaResults[i].city_name)
                        break
                    }
                }

                // 시군구 선택지가 없는 경우
                if(cityNameList!!.size == 1)
                    selectCity = selectProvince

                if(selectProvince == "선택하기") {
                    selectProvince = null
                    selectCity = null
                }

                val cityAdapter = ArrayAdapter(context, R.layout.custom_spinner_item, cityNameList!!)
                binding.selectionLocal2.adapter = cityAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectProvince = null
                selectCity = null
            }
        }

        // 시군구 선택하기 누르면 spinner 리스트
        binding.selectionLocal2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(selectCity != selectProvince) {
                    // 선택된 시군구 정보
                    selectCity = binding.selectionLocal2.getSelectedItem().toString()

                    if (selectCity == "선택하기")
                        selectCity = null
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                if(selectCity != selectProvince)
                    selectCity = null
            }
        }

        // 취소 버튼 선택
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        // 적용하기 버튼 선택
        binding.btnConfirm.setOnClickListener {
            // 모든 값이 선택된 경우
            if(checkAllSelect()) {
                // 저장할 데이터
                val body = UniversityFilterAddData(
                    mConnectUserId!!,
                    selectUniversity,
                    selectCollege,
                    selectDepartment,
                    selectGrade,
                    selectSemester,
                    selectProvince,
                    selectCity
                )

                // api에 데이터 저장 요청
                addUniversityAreaInfo(body)

                // Dialog 닫기
                dismiss()
            }
        }

    }

    private fun initViews() = with(binding) {
        // 뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라지지 않도록
        // setCancelable(false)

        // background를 투명하게 만듦
        // (중요) Dialog는 내부적으로 뒤에 흰 사각형 배경이 존재하므로, 배경을 투명하게 만들지 않으면
        // corner radius의 적용이 보이지 않는다.
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    // 선택 안 한 값이 있는지 확인
    fun checkAllSelect(): Boolean {
        if(selectUniversity == null) {
            showToast("학교를 선택해주세요.")
            return false
        }
        else if(selectCollege == null) {
            showToast("단과대학을 선택해주세요.")
            return false
        }
        else if(selectDepartment == null) {
            showToast("학과를 선택해주세요.")
            return false
        }
        else if(selectGrade == null) {
            showToast("학년을 선택해주세요.")
            return false
        }
        else if(selectSemester == null) {
            showToast("학기를 선택해주세요.")
            return false
        }
        else if(selectProvince == null) {
            showToast("도를 선택해주세요.")
            return false
        }
        else if(selectCity == null) {
            showToast("시/군/구를 선택해주세요.")
            return false
        }

        return true
    }

    // Toast 중복 방지
    fun showToast(message: String?) {
        if (mToast == null) {
            mToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        } else {
            mToast!!.setText(message)
        }
        mToast!!.show()
    }

    // 대학 정보 JSON 파일 읽기
    fun getJsonUniversityInfo(): String? {
        val jsonString: String
        try {
            jsonString = activity.assets.open("universitySelectList.json")
                .bufferedReader()
                .use {it.readText()}
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    // 지역 정보 JSON 파일 읽기
    fun getJsonAreaInfo(): String? {
        val jsonString: String
        try {
            jsonString = activity.assets.open("areaSelectList.json")
                .bufferedReader()
                .use {it.readText()}
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    // 대학, 지역 선택 정보 서버에 저장 - api로 전달
    fun addUniversityAreaInfo(body: UniversityFilterAddData) {
        val jwt = ApplicationClass.sSharedPreferences.getString(ApplicationClass.X_ACCESS_TOKEN, null)
        retrofit!!.create(UniversityFilterApiService::class.java).addUniversityInfo(xAccessToken=jwt!!, body)
            .enqueue(object : Callback<UniversityFilterResponse> {
                override fun onResponse(call: Call<UniversityFilterResponse>, response: Response<UniversityFilterResponse>) {
                    Log.d(ContentValues.TAG,"대학, 지역 정보 저장 요청 -------------------------------------------")
                    Log.d(ContentValues.TAG, "onResponse: ${response.body().toString()}")

                    showToast("저장되었습니다.")
                }

                override fun onFailure(call: Call<UniversityFilterResponse>, t: Throwable) {
                    Log.d(ContentValues.TAG,"대학, 지역 정보 저장 요청 -------------------------------------------")
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }
}
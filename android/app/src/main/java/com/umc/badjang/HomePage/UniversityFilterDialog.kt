package com.umc.badjang.HomePage

import android.app.Activity
import android.app.Dialog
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
import com.umc.badjang.R
import com.umc.badjang.databinding.DialogScholarshipLookupBinding
import java.io.IOException

class UniversityFilterDialog(context: Context, activity: Activity) : Dialog(context) {
    private lateinit var binding: DialogScholarshipLookupBinding
    private val activity = activity

    private var selectUniversity: String? = null
    private var selectCollege: String? = null

    private var universityInfo: MutableList<UniversityInfo>? = null
    private var collegeNameList: MutableList<String>? = null // 단과대학 리스트
    private var departmentList: MutableList<String>? = null  // 학과 리스트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogScholarshipLookupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        // json 파일에서 학교 정보 받아오기
        val jsonString = getJsonUniversityInfo()
        val gson = Gson()
        val listUniversityType = object : TypeToken<UniversitySelectData>() {}. type
        var universitys: UniversitySelectData = gson.fromJson(jsonString, listUniversityType)

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
                if(position != 0) Toast.makeText(activity, universityNameList[position], Toast.LENGTH_SHORT).show()
                // 선택된 학교
                selectUniversity = binding.spinnerSearchSchool.getSelectedItem().toString()

                // 선택된 학교의 단과대학 정보 가져오기
                universityInfo = mutableListOf<UniversityInfo>()
                for(i:Int in (0..universityResults.size - 1)) {
                    if(universityResults[i].university_name == selectUniversity) {
                        universityInfo = universityResults[i].university_info
                        break
                    }
                }

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
            }
        }

        // 단과대학 선택하기 누르면 spinner 리스트
        binding.selectionSchool.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0) Toast.makeText(activity, collegeNameList!![position], Toast.LENGTH_SHORT).show()

                // 선택된 단과대학
                selectCollege = binding.selectionSchool.getSelectedItem().toString()

                // 선택된 단과대학의 학교 정보 가져오기 - 학교 선택 리스트
                var universityDepartment = mutableListOf<String>()
                for(i:Int in (0..universityInfo!!.size - 1)) {
                    if(universityInfo!![i].college_name == selectCollege) {
                        universityDepartment = universityInfo!![i].university_department
                        break
                    }
                }
                departmentList = mutableListOf<String>()
                departmentList!!.add("선택하기")
                for(i:Int in (0..universityDepartment.size - 1))
                    departmentList!!.add(universityDepartment[i])

                val departmentAdapter = ArrayAdapter(context, R.layout.custom_spinner_item, departmentList!!)
                binding.selectionMajor.adapter = departmentAdapter

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // 단과대학 선택하기 누르면 spinner 리스트
        binding.selectionMajor.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0) Toast.makeText(activity, departmentList!![position], Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
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
                if(position != 0) Toast.makeText(activity, gradeList[position], Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
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
                if(position != 0) Toast.makeText(activity, semesterList[position], Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    private fun initViews() = with(binding) {
        // 뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라지지 않도록
        //setCancelable(false)

        // background를 투명하게 만듦
        // (중요) Dialog는 내부적으로 뒤에 흰 사각형 배경이 존재하므로, 배경을 투명하게 만들지 않으면
        // corner radius의 적용이 보이지 않는다.
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

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
}
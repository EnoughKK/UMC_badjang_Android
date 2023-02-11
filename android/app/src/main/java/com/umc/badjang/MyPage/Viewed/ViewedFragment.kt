package com.umc.badjang.MyPage.Viewed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.badjang.MainActivity
import com.umc.badjang.MyPage.MyWrite.MyWriteAdapter
import com.umc.badjang.MyPage.MyWrite.WriteData
import com.umc.badjang.databinding.FragmentWriteBinding
import retrofit2.Retrofit

class ViewedFragment : Fragment() {
    private lateinit var binding: FragmentWriteBinding // viewBinding
    lateinit var rvAdapter : MyWriteAdapter
    //api 통신을 위한 retrofit
    private var retrofit : Retrofit? =null
    var dataList = arrayListOf<WriteData>()
    var activity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteBinding.inflate(layoutInflater);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
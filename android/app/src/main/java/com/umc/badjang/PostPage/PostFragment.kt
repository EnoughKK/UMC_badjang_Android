package com.umc.badjang.PostPage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.umc.badjang.MainActivity
import com.umc.badjang.R
import com.umc.badjang.databinding.FragmentPostBinding

class PostFragment : Fragment() {
    private lateinit var viewBinding: FragmentPostBinding // viewBinding

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
        viewBinding = FragmentPostBinding.inflate(layoutInflater);

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun initRecycler() {

    }

}
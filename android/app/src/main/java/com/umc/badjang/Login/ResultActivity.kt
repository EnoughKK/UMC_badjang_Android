package com.umc.badjang.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.umc.badjang.R
import com.umc.badjang.databinding.ActivityLoginBinding
import com.umc.badjang.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private var firestore : FirebaseFirestore? = null
    private var uid : String? = null
    private lateinit var binding :ActivityResultBinding// viewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        uid = FirebaseAuth.getInstance().currentUser?.uid
        firestore = FirebaseFirestore.getInstance()

        // 바인딩 초기화
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_result)
        binding.resultView.adapter = ResultViewRecyclerViewAdapter()
        binding.resultView.layoutManager = LinearLayoutManager(this)
    }

    inner class ResultViewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var Login: ArrayList<Login> = arrayListOf()

        init {
            firestore?.collection(uid!!)?.orderBy("timestamp", Query.Direction.DESCENDING)
                ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    Login.clear()
                    if (querySnapshot == null) return@addSnapshotListener

                    // 데이터 받아오기
                    for (snapshot in querySnapshot!!.documents) {
                        var item = snapshot.toObject(Login::class.java)
                        //Login.add(item!!)
                    }
                    notifyDataSetChanged()
                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }

        override fun getItemCount(): Int {
            return Login.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as CustomViewHolder).itemView

            //viewHolder.resultTextOne.text = Login!![position].textOne
            //viewHolder.resultTextTwo.text = Login!![position].textTwo

        }
    }
}
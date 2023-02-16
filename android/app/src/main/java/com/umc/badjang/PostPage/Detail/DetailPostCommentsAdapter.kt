package com.umc.badjang.PostPage.Detail

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.badjang.ApplicationClass
import com.umc.badjang.Bookmarks.BookmarkPostData
import com.umc.badjang.MainActivity
import com.umc.badjang.MyPage.Noti.DetailNotiFragment
import com.umc.badjang.PostPage.Board.PostBoardFragment
import com.umc.badjang.R
import com.umc.badjang.databinding.*

class DetailPostCommentsAdapter(private val dataSet: ArrayList<CommentData>, var context :Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: ItemCommentsBinding
    //private lateinit var postViewBinding:

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemCommentsBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> holder.bind(dataSet[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ViewHolder(private val binding: ItemCommentsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommentData) {
            Glide.with(context).load(item.user_profileimage_url).into(binding.itemCommentsIvImg)
            if(item.comment_anonymity == "N"){
                binding.itemCommentsTvNickname.text = item.user_name
            }else{
                binding.itemCommentsTvNickname.text = "익명"
            }
            binding.itemCommentsTvDate.text = item.comment_createAt.substring(0,4)+"."+item.comment_createAt.substring(5,7)+"."+item.comment_createAt.substring(8,10)
            if(item.user_idx == ApplicationClass.bSharedPreferences.getInt(ApplicationClass.USER_IDX, 0)){
                binding.itemCommentsTvOther.text = "삭제하기"
            }else{
                binding.itemCommentsTvOther.text = "신고하기"
            }
            binding.itemCommentsTvContent.text = item.comment_content
            binding.itemCommentsGoodNum.text = item.comment_recommend.toString()
            if(item.recommend_status == 1){
                binding.itemCommentsGoodIcon.setImageResource(R.drawable.ic_good_count_blue)
            }else{
                binding.itemCommentsGoodIcon.setImageResource(R.drawable.ic_recommend_stroke)
            }
            var school_name = ""
            if(item.school_idx == 1){
                school_name = "부경대학교"
            }else if(item.school_idx == 2){
                school_name = "경상국립대학교"
            }else if(item.school_idx == 3){
                school_name = "한국해양대학교"
            }else if(item.school_idx == 4){
                school_name = "부산대학교"
            }
            binding.itemCommentsTvOther.setOnClickListener {
                if(item.school_idx != 0){
                    if(binding.itemCommentsTvOther.text =="삭제하기"){
                        var dialog = SchoolCommentDeleteDialog(context, item.school_idx, item.post_idx, item.comment_idx)
                        dialog.show()
                        dialog.setOnDismissListener {
                            if(ApplicationClass.bSharedPreferences.getString("delete_school_comment_content",null) == "삭제"){
                                Handler(Looper.getMainLooper()).postDelayed({
                                    //실행할 코드
                                    var fragment = DetailPostFragment()
                                    val bundle = Bundle()
                                    bundle.putInt("post_idx", item.post_idx)

                                    bundle.putString("board_name", school_name)
                                    fragment.arguments = bundle
                                    (context as MainActivity).changeReplaceFragment(fragment)
                                }, 300)

                            }
                        }
                    }else{
                        var dialog = CommentReportDialog(context)
                        //bSharedPreferences.edit().putString("comment_idx","").commit()
                        dialog.show()
                        dialog.setOnDismissListener {

                        }
                    }
                }else{
                    if(binding.itemCommentsTvOther.text =="삭제하기"){
                        var dialog = CommentDeleteDialog(context, item.post_idx, item.post_category)
                        ApplicationClass.bSharedPreferences.edit().putInt("post_idx",item.post_idx).commit()
                        ApplicationClass.bSharedPreferences.edit().putInt("comment_idx",item.comment_idx).commit()
                        //bSharedPreferences.edit().putString("comment_idx","").commit()
                        dialog.show()
                        dialog.setOnDismissListener {
                            if(ApplicationClass.bSharedPreferences.getString("delete_comment_content",null) == "삭제"){
                                Handler(Looper.getMainLooper()).postDelayed({
                                    //실행할 코드
                                    var fragment = DetailPostFragment()
                                    val bundle = Bundle()
                                    bundle.putInt("post_idx", item.post_idx)
                                    bundle.putString("board_name", item.post_category)
                                    fragment.arguments = bundle
                                    (context as MainActivity).changeReplaceFragment(fragment)
                                }, 300)

                            }
                        }
                    }else{
                        var dialog = CommentReportDialog(context)
                        ApplicationClass.bSharedPreferences.edit().putInt("post_idx",item.post_idx).commit()
                        ApplicationClass.bSharedPreferences.edit().putInt("comment_idx",item.comment_idx).commit()
                        //bSharedPreferences.edit().putString("comment_idx","").commit()
                        dialog.show()
                        dialog.setOnDismissListener {
                            if(ApplicationClass.bSharedPreferences.getString("delete_content",null) == "삭제"){
                                //requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
                                //requireActivity().supportFragmentManager.popBackStack()
                            }
                        }
                    }
                }

            }
            // 작성자 프로필
//            Glide.with(context).load(item.user_profileimage_url).into(binding.boardWritePostProfileImg)
//            binding.boardWritePostProfileNickname.text = item.user_name
//            binding.boardWritePostProfileDate.text = item.post_createAt.substring(0,10)
//            binding.boardWritePostContentTitle.text = item.post_name
//            binding.boardWritePostContentText.text = item.post_content
//            binding.boardWritePostGoodNum.text = item.post_recommend.toString()
//            binding.boardWritePostViewNum.text = item.post_view.toString()
//            binding.boardWritePostCommentsNum.text = item.post_comment.toString()
//            if(item.post_image == ""){
//                binding.boardWritePostContentImg.visibility = View.GONE
//            }else{
//                Glide.with(context).load(item.post_image).into(binding.boardWritePostContentImg)
//            }
//            binding.boardWritePostLabel.text = item.post_category
//            binding.boardWritePostProfileNickname.text = item.post_anonymity
        }
    }


}
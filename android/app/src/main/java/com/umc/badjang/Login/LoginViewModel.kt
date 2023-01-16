package com.umc.badjang.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class LoginViewModel() : ViewModel() {

    private var _loginStateLiveData = MutableLiveData<LoginState>(LoginState.UnInitialized)
    val loginStateLiveData: LiveData<LoginState> = _loginStateLiveData

    fun fetchData(tokenId: String?): Job = viewModelScope.launch {
        setState(LoginState.Loading)
        tokenId?.let {
            setState(
                LoginState.Login(it)
            )
        } ?: kotlin.run {
            setState(
                LoginState.Success.NotRegistered
            )
        }
    }

    /* 로그인 성공 result 받았을 떄 호출 */
    fun saveToken(idToken: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            fetchData(idToken)
        }
    }

    /* 로그인 성공 후 정보 설정 */
    fun setUserInfo(firebaseUser: FirebaseUser?) = viewModelScope.launch {
        firebaseUser?.let { user ->
            setState(
                LoginState.Success.Registered(
                    user.displayName ?: "익명",
                    user.photoUrl!!,
                )
            )
        } ?: kotlin.run {
            setState(LoginState.Success.NotRegistered)
        }
    }

    /* 로그아웃 버튼 클릭 시 호출 */
    fun signOut() = viewModelScope.launch {
        fetchData(null)
    }

    private fun setState(state: LoginState) {
        _loginStateLiveData.postValue(state)
    }

}
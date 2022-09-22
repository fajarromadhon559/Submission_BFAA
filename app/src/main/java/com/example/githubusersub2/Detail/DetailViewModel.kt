package com.example.githubusersub2.Detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.API_Network.ApiConfig
import com.example.githubuserapp.Response.PersonRespons
import com.example.githubuserapp.Response.SearchRespons
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detailPerson = MutableLiveData<PersonRespons>()
    val detailPerson : LiveData<PersonRespons> = _detailPerson
    private val _detailLoading = MutableLiveData<Boolean>()
    private val _detailDataFailed = MutableLiveData<Boolean>()
    private var viewModelJob = Job()

    suspend fun getDetailPerson(context: Context, username : String){
        _detailLoading.value = true
        val client = ApiConfig.getApiService(context).getPersonDetail(username)

        client.enqueue(object : retrofit2.Callback<PersonRespons> {
            override fun onResponse(call: Call<PersonRespons>, response: Response<PersonRespons>) {
                if (response.isSuccessful){
                    _detailLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null){
                        if (responseBody.login != null){
                            _detailPerson.postValue(responseBody!!)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<PersonRespons>, t: Throwable){
                _detailLoading.value = false
                _detailDataFailed.value = true
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }
    companion object{
        private const val TAG = "MainViewModel"
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
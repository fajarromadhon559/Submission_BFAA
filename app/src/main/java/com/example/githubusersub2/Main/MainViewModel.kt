package com.example.githubusersub2.Main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersub2.API.ApiConfig
import com.example.githubusersub2.Response.PersonRespons
import com.example.githubusersub2.Response.SearchRespons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private var _person = MutableLiveData<ArrayList<PersonRespons>?>()
    var person: LiveData<ArrayList<PersonRespons>?> = _person

    private var _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    private var _error = MutableLiveData<Boolean>()
    var error: LiveData<Boolean> = _error

    fun getSearchPerson(context: Context, person: String) {
        _loading.value = true
        val client = ApiConfig.getApiService(context).getSearchPerson(person)
        client.enqueue(object : Callback<SearchRespons> {
            override fun onResponse(call: Call<SearchRespons>, response: Response<SearchRespons>) {
                if (response.isSuccessful) {
                    _loading.value = false
                    val responseBody = response.body()?.items
                    if (responseBody != null) {
                        _person.postValue(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<SearchRespons>, t: Throwable) {
                _loading.value = false
                _error.value = true
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }

        })
    }

    fun getPersonList(context: Context) {
        _loading.value = true
        val client = ApiConfig.getApiService(context).getPersonList()
        client.enqueue(object : Callback<List<PersonRespons>> {
            override fun onResponse(
                call: Call<List<PersonRespons>>,
                response: Response<List<PersonRespons>>
            ) {
                _loading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _person.postValue(responseBody as ArrayList<PersonRespons>?)
                    }
                }
            }

            override fun onFailure(call: Call<List<PersonRespons>>, t: Throwable) {
                _loading.value = false
                _error.value = true
            }
        })
    }

    fun getPersonFollowers(context: Context, username : String) {
        _loading.value = true
        val client = ApiConfig.getApiService(context).getPersonFollowers(username)
        client.enqueue(object : Callback<List<PersonRespons>> {
            override fun onResponse(
                call: Call<List<PersonRespons>>,
                response: Response<List<PersonRespons>>
            ) {
                _loading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _person.postValue(responseBody as ArrayList<PersonRespons>?)
                    }
                }
            }

            override fun onFailure(call: Call<List<PersonRespons>>, t: Throwable) {
                _loading.value = false
                _error.value = true
            }
        })
    }
    fun getPersonFollowing(context: Context,  login : String, item : String) {
        _loading.value = true
        val client = ApiConfig.getApiService(context).getPersonFollowing(login, item)
        client.enqueue(object : Callback<List<PersonRespons>> {
            override fun onResponse(
                call: Call<List<PersonRespons>>,
                response: Response<List<PersonRespons>>
            ) {
                _loading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _person.postValue(responseBody as ArrayList<PersonRespons>?)
                    }
                }
            }

            override fun onFailure(call: Call<List<PersonRespons>>, t: Throwable) {
                _loading.value = false
                _error.value = true
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}


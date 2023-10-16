package com.bilalberek.movish.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bilalberek.movish.ApiService.RetrofitInstance
import com.bilalberek.movish.Model.MovieListResponse
import com.bilalberek.movish.Repository.MovishRepo
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit

class MainViewmodel(application: Application): AndroidViewModel(application) {

    private val movieResults = MutableLiveData<List<MovieListResponse.Result>>()
    val _movieResults:LiveData<List<MovieListResponse.Result>>  = movieResults


    private val errorValue = MutableLiveData<String>()
    val  _errorValue:LiveData<String>  = errorValue

    private var movishRepo = MovishRepo(application)





     fun fetchMovies(){

        viewModelScope.launch {
            try {
                var response = movishRepo.getMostRatedMovies(1)
                if (response.isSuccessful){
                    Log.d("backStack tracker", "response success")

                    val movieData = response.body()?.results
                    Log.d("backStack tracker", "${movieData?.size}")
                    movieResults.postValue(movieData)

                }else{
                    Log.d("backStack tracker", "response failure")
                    var errobody = response.errorBody()?.string() ?: "unknown error"
                    errorValue.postValue(errobody)


                }
            }catch (e: Exception){
                errorValue.postValue("Network error: ${e.message}")
            }

        }
    }










}
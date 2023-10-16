package com.bilalberek.movish.Repository

import android.content.Context
import com.bilalberek.movish.ApiService.ApiService
import com.bilalberek.movish.ApiService.RetrofitInstance
import com.bilalberek.movish.Model.MovieListResponse
import retrofit2.Response

class MovishRepo(context: Context) {

    suspend fun getMostRatedMovies(page: Int): Response<MovieListResponse>{
        return RetrofitInstance.api.getMovieTopRated(page)
    }

}
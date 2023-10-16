package com.bilalberek.movish.ApiService

import com.bilalberek.movish.Model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/top_rated")
   suspend fun getMovieTopRated(
        @Query("page")
        page: Int = 1
   ): Response<MovieListResponse>
}
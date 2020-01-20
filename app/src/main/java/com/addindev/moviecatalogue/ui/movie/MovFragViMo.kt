package com.addindev.moviecatalogue.ui.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.addindev.moviecatalogue.MovieCatalogue
import com.addindev.moviecatalogue.ui.movie.pojo.MovResponse

class MovFragViMo(application: Application) : AndroidViewModel(application) {
    private val responseMovies = MutableLiveData<MovResponse>()


    val movies: MutableLiveData<MovResponse>
        get() {
            return responseMovies
        }

    fun doRequestListMovies() {
        AndroidNetworking.get("https://api.themoviedb.org/3/discover/movie")
                .addQueryParameter("api_key", MovieCatalogue.MOVIE_DB_API_KEY)
                .addQueryParameter("language", (getApplication() as MovieCatalogue).getLanguage())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(MovResponse::class.java, object : ParsedRequestListener<MovResponse> {
                    override fun onResponse(response: MovResponse) {
                        responseMovies.postValue(response)
                    }

                    override fun onError(anError: ANError) {
                        responseMovies.value = MovResponse(anError)
                    }
                })
    }

}
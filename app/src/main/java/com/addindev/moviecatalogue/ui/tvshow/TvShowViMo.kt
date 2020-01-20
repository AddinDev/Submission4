package com.addindev.moviecatalogue.ui.tvshow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.addindev.moviecatalogue.MovieCatalogue
import com.addindev.moviecatalogue.ui.tvshow.pojo.TvShowResponse

class TvShowViMo(application: Application) :AndroidViewModel(application){

    private val responseTvShows = MutableLiveData<TvShowResponse>()

    internal val getTvShowList: MutableLiveData<TvShowResponse>
        get() {
            return responseTvShows
        }

    internal fun doRequestListTvShows() {
        AndroidNetworking.get("https://api.themoviedb.org/3/discover/tv")
                .addQueryParameter("api_key", MovieCatalogue.MOVIE_DB_API_KEY)
                .addQueryParameter("language", (getApplication() as MovieCatalogue).getLanguage())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(TvShowResponse::class.java, object : ParsedRequestListener<TvShowResponse> {

                    override fun onResponse(response: TvShowResponse) {
                        responseTvShows.postValue(response)
                    }

                    override fun onError(anError: ANError) {
                        responseTvShows.value = TvShowResponse(anError)
                    }
                })
    }
}
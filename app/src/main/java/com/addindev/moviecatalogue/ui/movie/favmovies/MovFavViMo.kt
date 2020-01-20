package com.addindev.moviecatalogue.ui.movie.favmovies


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.addindev.moviecatalogue.db.MovieDatabase
import com.addindev.moviecatalogue.ui.movie.pojo.ResultsItem
import android.os.AsyncTask

class MovFavViMo(application: Application) : AndroidViewModel(application) {
    private val favMovies = MutableLiveData<MutableList<ResultsItem>>()
    private val movieCatalogueDatabase: MovieDatabase = MovieDatabase.getDatabase(getApplication())


    val movies: MutableLiveData<MutableList<ResultsItem>>
        get() {
            return favMovies
        }

    fun fetchFavMovies(){
        AsyncTask.execute {
            favMovies.postValue(movieCatalogueDatabase.movieDao().getAllMovie())
        }
    }
}

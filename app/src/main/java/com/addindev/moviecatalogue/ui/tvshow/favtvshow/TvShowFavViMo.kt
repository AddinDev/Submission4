package com.addindev.moviecatalogue.ui.tvshow.favtvshow

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.addindev.moviecatalogue.db.MovieDatabase
import com.addindev.moviecatalogue.ui.tvshow.pojo.ResultsItem

class TvShowFavViMo(application: Application) : AndroidViewModel(application) {

    private val favTvShow = MutableLiveData<MutableList<ResultsItem>>()
    private val movieCatalogueDatabase: MovieDatabase = MovieDatabase.getDatabase(getApplication())

    internal val getTvShowList: MutableLiveData<MutableList<ResultsItem>>
        get() {
            return favTvShow
        }

    internal fun fetchFavTvShows() {
        AsyncTask.execute {
            favTvShow.postValue(movieCatalogueDatabase.tvShowDao().getAllTvShow())
        }
    }
}

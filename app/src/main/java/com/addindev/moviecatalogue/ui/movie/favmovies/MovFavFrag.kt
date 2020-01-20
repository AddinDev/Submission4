package com.addindev.moviecatalogue.ui.movie.favmovies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.addindev.moviecatalogue.R
import com.addindev.moviecatalogue.databinding.FavMoviesFragmentBinding
import com.addindev.moviecatalogue.ui.movie.MovAdapter
import com.addindev.moviecatalogue.ui.movie.detailmovie.MovDetail
import com.addindev.moviecatalogue.ui.movie.pojo.ResultsItem

class MovFavFrag : Fragment() {

    private lateinit var favMoviesFragmentBinding: FavMoviesFragmentBinding
    private lateinit var mViewModel: MovFavViMo
    private lateinit var alertDialog: AlertDialog

    private val getFavMovie = Observer<List<ResultsItem>> {

        val mAdapter = MovAdapter(it)
        if (it.size > 0) {
            favMoviesFragmentBinding.tvMessage.visibility = View.GONE
            mAdapter.SetOnItemClickListener(object : MovAdapter.OnItemClickListener {
                override fun onItemClick(view: View, model: ResultsItem) {
                    val goToDetailMovie = Intent(view.context, MovDetail::class.java)
                    goToDetailMovie.putExtra(MovDetail.SELECTED_MOVIE, model)
                    startActivity(goToDetailMovie)
                }
            })

            favMoviesFragmentBinding.recyclerView.adapter = mAdapter
        } else {
            favMoviesFragmentBinding.recyclerView.adapter = null
            favMoviesFragmentBinding.tvMessage.visibility = View.VISIBLE
        }

        favMoviesFragmentBinding.progressBar.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        favMoviesFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fav_movies_fragment, container, false)
        mViewModel = ViewModelProviders.of(this).get(MovFavViMo::class.java)
        favMoviesFragmentBinding.viewmodel = mViewModel
        return favMoviesFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog = AlertDialog.Builder(view.context).setTitle(getString(R.string.failure)).setPositiveButton("OK") { dialog, which -> }.create()

        val layoutManager = LinearLayoutManager(view.context)
        favMoviesFragmentBinding.recyclerView.layoutManager = layoutManager
        favMoviesFragmentBinding.recyclerView.setHasFixedSize(true)
        favMoviesFragmentBinding.viewmodel = mViewModel

    }

    override fun onResume() {

        super.onResume()
        mViewModel.fetchFavMovies()
        mViewModel.movies.observe(this, getFavMovie)
    }

}

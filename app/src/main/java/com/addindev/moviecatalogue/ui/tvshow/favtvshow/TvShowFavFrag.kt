package com.addindev.moviecatalogue.ui.tvshow.favtvshow

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
import com.addindev.moviecatalogue.databinding.FavTvShowFragmentBinding
import com.addindev.moviecatalogue.ui.tvshow.TvShowAdapter
import com.addindev.moviecatalogue.ui.tvshow.detailtvshows.TvShowDetail
import com.addindev.moviecatalogue.ui.tvshow.pojo.ResultsItem

class TvShowFavFrag : Fragment() {

    private lateinit var favTvShowFragmentBinding: FavTvShowFragmentBinding
    private lateinit var mViewModel: TvShowFavViMo
    private lateinit var alertDialog: AlertDialog

    val getTvShow = Observer<List<ResultsItem>>{
        val mAdapter = TvShowAdapter(it)
        if (it.size > 0) {
            favTvShowFragmentBinding.tvMessage.visibility = View.GONE
            mAdapter.SetOnItemClickListener(object : TvShowAdapter.OnItemClickListener {
                override fun onItemClick(view: View, model: ResultsItem) {
                    val goToDetailMovie = Intent(view.context, TvShowDetail::class.java)
                    goToDetailMovie.putExtra(TvShowDetail.SELECTED_TV_SHOWS, model)
                    startActivity(goToDetailMovie)
                }
            })

            favTvShowFragmentBinding.recyclerView.adapter = mAdapter
        } else {
            favTvShowFragmentBinding.recyclerView.adapter = null
            favTvShowFragmentBinding.tvMessage.visibility = View.VISIBLE
        }

        favTvShowFragmentBinding.progressBar.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        favTvShowFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fav_tv_show_fragment,container,false)
        mViewModel = ViewModelProviders.of(this).get(TvShowFavViMo::class.java)
        favTvShowFragmentBinding.viewmodel = mViewModel
        return favTvShowFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog = AlertDialog.Builder(view.context).setTitle(getString(R.string.failure)).setPositiveButton("OK") { dialog, which -> }.create()

        val layoutManager = LinearLayoutManager(view.context)
        favTvShowFragmentBinding.recyclerView.layoutManager = layoutManager
        favTvShowFragmentBinding.recyclerView.setHasFixedSize(true)
        favTvShowFragmentBinding.viewmodel = mViewModel
    }

    override fun onResume() {
        super.onResume()
        mViewModel.fetchFavTvShows()
        mViewModel.getTvShowList.observe(this, getTvShow)
    }
}

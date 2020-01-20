package com.addindev.moviecatalogue.ui.tvshow

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
import com.addindev.moviecatalogue.databinding.FragmentTvShowBinding
import com.addindev.moviecatalogue.ui.tvshow.detailtvshows.TvShowDetail
import com.addindev.moviecatalogue.ui.tvshow.pojo.TvShowResponse
import com.addindev.moviecatalogue.ui.tvshow.pojo.ResultsItem

class TvShowFrag : Fragment() {

    lateinit var alertDialog: AlertDialog
    lateinit var binding: FragmentTvShowBinding
    lateinit var mViewModel: TvShowViMo

    val getTvShows = Observer<TvShowResponse> { responseTvShows ->
        if (responseTvShows != null) {
            if (responseTvShows.anError == null) {
                val mAdapter = TvShowAdapter(responseTvShows.results!!)

                mAdapter.SetOnItemClickListener(object : TvShowAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, model: ResultsItem) {
                        val goToDetailMovie = Intent(view.context, TvShowDetail::class.java)
                        goToDetailMovie.putExtra(TvShowDetail.SELECTED_TV_SHOWS, model)
                        startActivity(goToDetailMovie)
                    }
                })

                binding.recyclerView.adapter = mAdapter
            } else {
                alertDialog.setMessage(responseTvShows.anError.message)
                alertDialog.show()
            }
        }
        binding.progressBar.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_tv_show,container,false)
        mViewModel = ViewModelProviders.of(this).get(TvShowViMo::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog = AlertDialog.Builder(view.context).setTitle(getString(R.string.failure)).setPositiveButton("OK") { dialog, which -> }.create()

        val layoutManager = LinearLayoutManager(view.context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        mViewModel.doRequestListTvShows()
        mViewModel.getTvShowList.observe(this, getTvShows)
    }
}
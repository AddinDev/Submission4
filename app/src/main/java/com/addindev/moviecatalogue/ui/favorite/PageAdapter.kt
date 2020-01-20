package com.addindev.moviecatalogue.ui.favorite.tab

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.addindev.moviecatalogue.R
import com.addindev.moviecatalogue.ui.movie.favmovies.MovFavFrag
import com.addindev.moviecatalogue.ui.tvshow.favtvshow.TvShowFavFrag

internal class PageAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_movies, R.string.tab_tv_shows)

    }

    override fun getPageTitle(position: Int): CharSequence? {

        return mContext.resources.getString(TAB_TITLES[position])

    }


    private val pages = listOf(

        MovFavFrag(), TvShowFavFrag()

    )

    override fun getItem(position: Int): Fragment {

        return pages[position]

    }

    override fun getCount(): Int {

        return pages.size

    }
}
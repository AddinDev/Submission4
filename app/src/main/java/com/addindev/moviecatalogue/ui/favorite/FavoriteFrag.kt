package com.addindev.moviecatalogue.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.addindev.moviecatalogue.R
import com.addindev.moviecatalogue.databinding.FragmentFavoriteBinding
import com.addindev.moviecatalogue.ui.favorite.tab.PageAdapter

class FavoriteFrag : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViMo

    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(

            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViMo::class.java)
        binding.viewmodel = favoriteViewModel
        binding.viewPager.adapter = PageAdapter(binding.root.context, childFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)

        return binding.root

    }

}
package com.rodrigosa.shoesshop.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.rodrigosa.shoesshop.SliderAdapter
import com.rodrigosa.shoesshop.databinding.ActivityMainBinding
import com.rodrigosa.shoesshop.model.SliderModel
import com.rodrigosa.shoesshop.viewModel.MainViewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()
    }

    private fun initBanner() {
        binding.pbProgressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer { items ->
            banners(items)
            binding.pbProgressBarBanner.visibility = View.GONE
        })
        viewModel.loadBanners()
    }

    private fun banners(images: List<SliderModel>) {
        binding.vpViewpagerSlider.adapter = SliderAdapter(images, binding.vpViewpagerSlider)
        binding.vpViewpagerSlider.clipToPadding = false
        binding.vpViewpagerSlider.clipChildren = false
        binding.vpViewpagerSlider.offscreenPageLimit = 3
        binding.vpViewpagerSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.vpViewpagerSlider.setPageTransformer(compositePageTransformer)

        if (images.size > 1) {
            binding.dotsIndicator.visibility = View.VISIBLE
            binding.dotsIndicator.attachTo(binding.vpViewpagerSlider)
        }

    }

}
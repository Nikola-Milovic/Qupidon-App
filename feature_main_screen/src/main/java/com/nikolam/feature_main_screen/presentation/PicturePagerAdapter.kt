package com.nikolam.feature_main_screen.presentation

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import timber.log.Timber


class PicturePagerAdapter(private val context: Context) : PagerAdapter() {

    private val imageUrls = arrayListOf<String>()


    fun addImages(images: List<String>){
        imageUrls.clear()
        imageUrls.addAll(images)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)

        Glide.with(context).load(imageUrls[position])
                .fitCenter()
                .centerCrop()
                .into(imageView)

        container.addView(imageView)

        return imageView;
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}
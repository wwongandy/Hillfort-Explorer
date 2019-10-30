package org.wit.hillfortexplorer.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.helpers.readImageFromPath

class ImagePagerAdapter(private val context: Context, val images: List<String>) : PagerAdapter() {

    private var layoutInflater: LayoutInflater ?= null

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.image_hillfort, null) as ImageView
        val image = v.findViewById<View>(R.id.imageIcon) as ImageView
        image.setImageBitmap(readImageFromPath(context, images[position]))

        val vp = container as ViewPager
        vp.addView(v, 0)

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }
}
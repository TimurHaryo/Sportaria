package id.train.sportaria.util.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager

class NonSwipingViewPager : ViewPager {

    constructor(context: Context) : super(context) {
        setCustomScroller()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        setCustomScroller()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?) = false
    override fun onTouchEvent(ev: MotionEvent?) = false

    private fun setCustomScroller() {
        try {
            val viewPager = ViewPager::class.java
            val scroller = viewPager.getDeclaredField("mScrolled")
            scroller.isAccessible = true
            scroller.set(this, CustomScroller(context))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class CustomScroller(context: Context) : Scroller(context, DecelerateInterpolator()) {
        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, 0)
        }
    }

}
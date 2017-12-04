package com.stepstone.apprating.ratingbar

import android.content.Context
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.stepstone.apprating.C
import com.stepstone.apprating.R

/**
 * @author Piotr Głębocki (Piotr.Glebocki@stepstone.com) on 12/04/2017.
 */
class StarButton : FrameLayout {

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    private lateinit var emptyStarImage: ImageView

    private lateinit var fullStarImage: ImageView

    private fun initialize() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.star_button_layout, this, true)
        emptyStarImage = findViewById(R.id.empty_star_image_view)
        fullStarImage = findViewById(R.id.full_star_image_view)
    }

    fun setChecked(checked: Boolean): StarButton {
        fullStarImage
                .animate()
                .alpha(if (checked) C.Animation.VISIBLE else C.Animation.INVISIBLE)
                .setDuration(C.Animation.CHECK_STAR_DURATION)
                .start()

        return this
    }

    fun setCheckedWithoutAnimation(checked: Boolean): StarButton {
        fullStarImage.alpha = if (checked) C.Animation.VISIBLE else C.Animation.INVISIBLE
        return this
    }

    fun setColor(@ColorInt color: Int): StarButton {
        emptyStarImage.setColorFilter(color)
        fullStarImage.setColorFilter(color)
        return this
    }
}

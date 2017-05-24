package com.stepstone.apprating.ratingbar

import android.content.Context
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.widget.ImageView

import com.stepstone.apprating.R

/**
 * @author Piotr Głębocki (Piotr.Glebocki@stepstone.com) on 12/04/2017.
 */
class StarButton : ImageView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    fun setChecked(checked: Boolean): StarButton {
        setImageResource(if (checked) R.drawable.ic_star_full_48dp else R.drawable.ic_star_empty_48dp)
        return this
    }

    fun setColor(@ColorInt color: Int): StarButton {
        setColorFilter(color)
        return this
    }
}

package com.stepstone.apprating.ratingbar;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.stepstone.apprating.R;

/**
 * @author Piotr Głębocki (Piotr.Glebocki@stepstone.com) on 12/04/2017.
 */
public class StarButton extends ImageView {

    public StarButton(Context context) {
        super(context);
    }

    public StarButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StarButton setChecked(boolean checked) {
        setImageResource(checked ? R.drawable.ic_star_full_48dp : R.drawable.ic_star_empty_48dp);
        return this;
    }

    public StarButton setColor(@ColorInt int color) {
        setColorFilter(color);
        return this;
    }
}

/*
Copyright 2017 StepStone Services

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.stepstone.apprating.ratingbar;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.stepstone.apprating.AppRatingDialogView;
import com.stepstone.apprating.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * This class is a custom rating bar. It handles displaying of
 * stars, tinting etc.
 */
public class CustomRatingBar extends LinearLayout {

    private LinearLayout container;

    private int numStars;
    private int max;
    private float rating;
    private boolean isIndicator;
    private float stepSize;
    private AppRatingDialogView onRatingBarChangeListener;

    public CustomRatingBar(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.component_custom_rating_bar, this);
        container = (LinearLayout) findViewById(R.id.rating_bar_container);

        drawStars();
    }

    private void drawStars() {
        StarButton starButton = new StarButton(getContext());
        starButton.setImageResource(R.drawable.ic_star_full_48dp);
        starButton.setLayoutParams(new LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        container.addView(starButton);
    }

    public float getRating() {
        return rating;
    }

    public void setNumStars(int numStars) {
        this.numStars = numStars;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setIsIndicator(boolean isIndicator) {
        this.isIndicator = isIndicator;
    }

    public void setStepSize(float stepSize) {
        this.stepSize = stepSize;
    }

    public void setOnRatingBarChangeListener(AppRatingDialogView onRatingBarChangeListener) {
        this.onRatingBarChangeListener = onRatingBarChangeListener;
    }

    private static int getThemeAccentColor(Context context) {
        int colorAttr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAttr = android.R.attr.colorAccent;
        } else {
            //Get colorAccent defined for AppCompat
            colorAttr = context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.data;
    }
}
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
import android.view.View;
import android.widget.LinearLayout;

import com.stepstone.apprating.R;
import com.stepstone.apprating.common.Preconditions;
import com.stepstone.apprating.listener.OnRatingBarChangedListener;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * This class is a custom rating bar. It handles displaying of
 * stars, tinting etc.
 */
public class CustomRatingBar extends LinearLayout {

    private ArrayList<StarButton> starList = new ArrayList<>();

    private LinearLayout container;

    private int numStars;

    private float rating;

    private boolean isIndicator;

    private OnRatingBarChangedListener onRatingBarChangedListener;

    public CustomRatingBar(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.component_custom_rating_bar, this);
        container = (LinearLayout) findViewById(R.id.rating_bar_container);
    }

    private void addStars(int numberOfAll, int numberOfChecked) {
        Preconditions.checkArgument(numberOfChecked <= numberOfAll, "wrong argument");

        starList.clear();
        container.removeAllViews();

        for (int index = 0; index < numberOfAll; index++) {
            addStar()
                    .setChecked(index < numberOfChecked)
                    .setColor(getThemeAccentColor(getContext()))
                    .setOnClickListener(new OnStarClickedHandler(index + 1));
        }
    }

    private StarButton addStar() {
        StarButton starButton = new StarButton(getContext());
        starButton.setLayoutParams(new LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        starList.add(starButton);
        container.addView(starButton);
        return starButton;
    }

    public float getRating() {
        return rating;
    }

    public void setNumStars(int numStars) {
        this.numStars = numStars;

        addStars(numStars, 0);
    }

    public void setRating(int rating) {
        this.rating = rating;

        if (rating <= starList.size()) {
            for (int index = 0; index < starList.size(); index++) {
                starList.get(index).setChecked(index < rating);
            }
        }

        onRatingBarChangedListener.onRatingChanged(rating);
    }

    public void setIsIndicator(boolean isIndicator) {
        this.isIndicator = isIndicator;
    }

    public void setOnRatingBarChangeListener(OnRatingBarChangedListener onRatingBarChangedListener) {
        this.onRatingBarChangedListener = onRatingBarChangedListener;
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

    private class OnStarClickedHandler implements OnClickListener {

        private final int number;

        public OnStarClickedHandler(int number) {
            this.number = number;
        }

        @Override
        public void onClick(View v) {
            setRating(number);
        }
    }
}
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

package com.stepstone.apprating;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.stepstone.apprating.ratingbar.CustomRatingBar;

import java.util.List;

/**
 * This class represents custom dialog view which contains
 * rating bar, edit box and labels.
 */
public class AppRatingDialogView extends LinearLayout implements RatingBar.OnRatingBarChangeListener {

    private CustomRatingBar ratingBar;

    private EditText editText;

    private TextView titleText;

    private TextView contentText;

    private TextView noteDescriptionText;

    private List<String> noteDescriptions;

    public AppRatingDialogView(Context context) {
        super(context);
        setup(context);
    }

    /**
     * This method returns current rating.
     *
     * @return number of current selected stars
     */
    public float getRateNumber() {
        return ratingBar.getRating();
    }

    /**
     * This method returns rating comment.
     *
     * @return comment text from edit box
     */
    public String getComment() {
        return editText.getText().toString();
    }

    /**
     * This method sets maximum numbers of start which are visible.
     *
     * @param numberOfStars maximum number of stars
     */
    public void setNumberOfStars(int numberOfStars) {
        ratingBar.setNumStars(numberOfStars);
        ratingBar.setMax(numberOfStars);
    }

    /**
     * This method sets note descriptions for each rating value.
     *
     * @param noteDescriptions list of note descriptions
     */
    public void setNoteDescriptions(List<String> noteDescriptions) {
        int numberOfStars = noteDescriptions.size();
        setNumberOfStars(numberOfStars);
        this.noteDescriptions = noteDescriptions;
    }

    /**
     * This method sets default number of stars.
     *
     * @param defaultRating number of stars
     */
    public void setDefaultRating(float defaultRating) {
        ratingBar.setRating(defaultRating);
    }

    /**
     * This method sets dialog's title.
     *
     * @param title dialog's title text
     */
    public void setTitleText(String title) {
        titleText.setText(title);
        titleText.setVisibility(VISIBLE);
    }

    /**
     * This method sets dialog's content text.
     *
     * @param content dialog's content text
     */
    public void setContentText(String content) {
        contentText.setText(content);
        contentText.setVisibility(VISIBLE);
    }

    /**
     * This method sets color of dialog's title.
     *
     * @param color resource id of title label color
     */
    public void setTitleColor(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            titleText.setTextColor(getResources().getColor(color, getTheme()));
        } else {
            titleText.setTextColor(getResources().getColor(color));

        }
    }

    /**
     * This method sets color of dialog's content.
     *
     * @param color resource id of content label color
     */
    public void setContentColor(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            contentText.setTextColor(getResources().getColor(color, getTheme()));
        } else {
            contentText.setTextColor(getResources().getColor(color));

        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        updateNoteDescriptionText(((int) rating) - 1);
    }

    private void updateNoteDescriptionText(int rating) {
        if (noteDescriptions == null || noteDescriptions.isEmpty()) {
            noteDescriptionText.setVisibility(GONE);
            return;
        }

        String text = noteDescriptions.get(rating);
        noteDescriptionText.setText(text);
        noteDescriptionText.setVisibility(VISIBLE);
    }

    private void setup(Context context) {
        LayoutInflater.from(context).inflate(R.layout.component_app_rate_dialog, this, true);
        ratingBar = (CustomRatingBar) findViewById(R.id.app_rate_dialog_rating_bar);
        editText = (EditText) findViewById(R.id.app_rate_dialog_edit_text);
        titleText = (TextView) findViewById(R.id.app_rate_dialog_title_text);
        contentText = (TextView) findViewById(R.id.app_rate_dialog_content_text);
        noteDescriptionText = (TextView) findViewById(R.id.app_rate_dialog_note_description);
        ratingBar.setIsIndicator(false);
        ratingBar.setStepSize(1.0f);
        ratingBar.setOnRatingBarChangeListener(this);
    }

    private Resources.Theme getTheme() {
        return getContext().getTheme();
    }
}

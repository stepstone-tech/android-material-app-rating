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

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.stepstone.apprating.common.Preconditions;
import com.stepstone.apprating.listener.OnNegativeButtonClickedListener;
import com.stepstone.apprating.listener.OnPositiveButtonClickedListener;

import java.util.List;


/**
 * This class represents dialog object produced by {@link Builder}.
 * Dialog can be fully configurable.
 *
 * @see Builder
 */
public class AppRatingDialog {

    final private FragmentActivity activity;

    final private Builder.Data data;

    protected AppRatingDialog(@NonNull FragmentActivity activity, Builder.Data data) {
        this.activity = activity;
        this.data = data;
    }

    /**
     * This method shows rating dialog.
     */
    public void show() {
        AppRatingDialogFragment fragment = AppRatingDialogFragment.newInstance(data);
        fragment.show(activity.getSupportFragmentManager(), "");
    }

    /**
     * This class allows to setup rating dialog
     * using builder pattern.
     */
    public static class Builder {

        class Data {

            int numberOfStars = C.InitialValues.MAX_RATING;

            int defaultRating = C.InitialValues.DEFAULT_RATING;

            String positiveButtonText;

            String negativeButtonText;

            String title;

            String content;

            @StringRes
            int positiveButtonTextResId;

            @StringRes
            int negativeButtonTextResId;

            @StringRes
            int titleResId;

            @StringRes
            int contentResId;

            @ColorRes
            int titleColorResId;

            @ColorRes
            int contentColorResId;

            List<String> noteDescriptions;

            OnPositiveButtonClickedListener positiveButtonClickedListener = OnPositiveButtonClickedListener.NULL;

            OnNegativeButtonClickedListener negativeButtonClickedListener = OnNegativeButtonClickedListener.NULL;
        }

        private Data data = new Data();

        /**
         * This method creates {@link AppRatingDialog} object. It should be called
         * after setup.
         *
         * @param activity an activity where dialog belongs to
         * @return instance of dialog
         */
        public AppRatingDialog create(@NonNull FragmentActivity activity) {
            Preconditions.checkNotNull(activity, "FragmentActivity cannot be null");
            return new AppRatingDialog(activity, data);
        }

        /**
         * This method sets maximum number of start which will be visible in the dialog.
         * Default value is 6. If want to add some note descriptions below rating bar
         * then need to use {@link #setNoteDescriptions(List)} method instead of this one.
         *
         * @param maxRating maximum number of stars
         * @return Builder for chaining
         */
        public Builder setNumberOfStars(int maxRating) {
            Preconditions.checkArgument(maxRating > 0 && maxRating <= C.InitialValues.MAX_RATING,
                    "max rating value should be between 1 and " + C.InitialValues.MAX_RATING);
            data.numberOfStars = maxRating;
            return this;
        }

        /**
         * This method sets note description for appropriate rating numbers.
         * If note descriptions were set by this then {@link #setNumberOfStars(int)}
         * method will be ignored.
         *
         * @param noteDescriptions list of note descriptions
         * @return Builder for chaining
         */
        public Builder setNoteDescriptions(List<String> noteDescriptions) {
            Preconditions.checkNotNull(noteDescriptions, "list cannot be null");
            Preconditions.checkArgument(!noteDescriptions.isEmpty(), "list cannot be empty");
            Preconditions.checkArgument(noteDescriptions.size() <= C.InitialValues.MAX_RATING,
                    "size of the list can be maximally " + C.InitialValues.MAX_RATING);
            data.noteDescriptions = noteDescriptions;
            return this;
        }

        /**
         * This method sets number of stars which are selected by default
         * when dialog is opened.
         *
         * @param defaultRating number of stars which should be selected
         * @return Builder for chaining
         */
        public Builder setDefaultRating(int defaultRating) {
            Preconditions.checkArgument(defaultRating >= 0 && defaultRating <= data.numberOfStars,
                    "default rating value should be between 0 and " + data.numberOfStars);
            data.defaultRating = defaultRating;
            return this;
        }

        /**
         * This method sets dialog title.
         * The title is optional.
         *
         * @param title dialog's title text
         * @return Builder for chaining
         * @see#setTitle(int)
         */
        public Builder setTitle(String title) {
            Preconditions.checkArgument(!TextUtils.isEmpty(title), "title cannot be empty");
            data.title = title;
            data.titleResId = 0;
            return this;
        }

        /**
         * This method sets dialog title.
         * The title is optional.
         *
         * @param resId resource id of dialog's title
         * @return Builder for chaining
         * @see#setTitle(String)
         */
        public Builder setTitle(@StringRes int resId) {
            data.titleResId = resId;
            data.title = null;
            return this;
        }

        /**
         * This method sets dialog content description text, which is visible below title.
         * The description content is optional.
         *
         * @param content dialog's content text
         * @return Builder for chaining
         * @see#setContent(int)
         */
        public Builder setContent(String content) {
            Preconditions.checkArgument(!TextUtils.isEmpty(content), "content cannot be empty");
            data.content = content;
            data.contentResId = 0;
            return this;
        }

        /**
         * This method sets dialog content description text, which is visible below title.
         * The description content is optional.
         *
         * @param resId resource id of dialog's content text
         * @return Builder for chaining
         * @see#setContent(String)
         */
        public Builder setContent(@StringRes int resId) {
            data.contentResId = resId;
            data.content = null;
            return this;
        }

        /**
         * This method sets text of dialog's positive button.
         *
         * @param positiveButtonText text for positive button
         * @return Builder for chaining
         * @see#setPositiveButtonText(int)
         */
        public Builder setPositiveButtonText(String positiveButtonText) {
            Preconditions.checkArgument(!TextUtils.isEmpty(positiveButtonText), "text cannot be empty");
            data.positiveButtonText = positiveButtonText;
            data.positiveButtonTextResId = 0;
            return this;
        }

        /**
         * This method sets text of dialog's positive button.
         *
         * @param resId resource id of positive button text
         * @return Builder for chaining
         * @see#setPositiveButtonText(String)
         */
        public Builder setPositiveButtonText(@StringRes int resId) {
            data.positiveButtonTextResId = resId;
            data.positiveButtonText = null;
            return this;
        }

        /**
         * This method sets text of dialog's negative button.
         *
         * @param negativeButtonText text for negative button
         * @return Builder for chaining
         * @see#setNegativeButtonText(int)
         */
        public Builder setNegativeButtonText(String negativeButtonText) {
            Preconditions.checkArgument(!TextUtils.isEmpty(negativeButtonText), "text cannot be empty");
            data.negativeButtonText = negativeButtonText;
            data.negativeButtonTextResId = 0;
            return this;
        }

        /**
         * This method sets text of dialog's negative button.
         *
         * @param resId resource id of negative button text
         * @return Builder for chaining
         * @see#setNegativeButtonText(String)
         */
        public Builder setNegativeButtonText(@StringRes int resId) {
            data.negativeButtonTextResId = resId;
            data.negativeButtonText = null;
            return this;
        }

        /**
         * This method sets title's color resource.
         * If not set then if uses default primary text color
         * defined in theme.
         *
         * @param colorResId color resource id for title label
         * @return Builder for chaining
         */
        public Builder setTitleColor(@ColorRes int colorResId) {
            data.titleColorResId = colorResId;
            return this;
        }

        /**
         * This method sets content's color resource.
         * If not set then if uses default primary text color
         * defined in theme.
         *
         * @param colorResId color resource id for content label
         * @return Builder for chaining
         */
        public Builder setContentColor(@ColorRes int colorResId) {
            data.contentColorResId = colorResId;
            return this;
        }

        /**
         * This method registers listener which notify when
         * positive button was clicked.
         *
         * @param positiveButtonClickedListener click listener for positive button action
         * @return Builder for chaining
         */
        public Builder setPositiveButtonClickedListener(@NonNull OnPositiveButtonClickedListener positiveButtonClickedListener) {
            Preconditions.checkNotNull(positiveButtonClickedListener, "listener cannot be null");
            data.positiveButtonClickedListener = positiveButtonClickedListener;
            return this;
        }

        /**
         * This method registers listener which notify when
         * negative button was clicked.
         *
         * @param negativeButtonClickedListener click listener for negative button action
         * @return Builder for chaining
         */
        public Builder setNegativeButtonClickedListener(@NonNull OnNegativeButtonClickedListener negativeButtonClickedListener) {
            Preconditions.checkNotNull(negativeButtonClickedListener, "listener cannot be null");
            data.negativeButtonClickedListener = negativeButtonClickedListener;
            return this;
        }
    }
}

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

package com.stepstone.apprating

import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.annotation.StyleRes
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import com.stepstone.apprating.AppRatingDialog.Builder
import com.stepstone.apprating.common.Preconditions
import com.stepstone.apprating.listener.OnNegativeButtonClickedListener
import com.stepstone.apprating.listener.OnPositiveButtonClickedListener
import java.io.Serializable


/**
 * This class represents dialog object produced by [Builder].
 * Dialog can be fully configurable.

 * @see Builder
 */
class AppRatingDialog protected constructor(private val activity: FragmentActivity, private val data: Builder.Data) {

    /**
     * This method shows rating dialog.
     */
    fun show() {
        val fragment = AppRatingDialogFragment.newInstance(data)
        fragment.show(activity.supportFragmentManager, "")
    }

    /**
     * This class allows to setup rating dialog
     * using builder pattern.
     */
    class Builder {

        inner class Data : Serializable {

            var numberOfStars = C.InitialValues.MAX_RATING

            var defaultRating = C.InitialValues.DEFAULT_RATING

            var positiveButtonText: String? = null

            var negativeButtonText: String? = null

            var title: String? = null

            var content: String? = null

            var positiveButtonTextResId: Int = 0

            var negativeButtonTextResId: Int = 0

            var titleResId: Int = 0

            var contentResId: Int = 0

            var titleTextColorResId: Int = 0

            var descriptionTextColorResId: Int = 0

            var commentTextColorResId: Int = 0

            var commentBackgroundColorResId: Int = 0

            var windowAnimationResId: Int = 0

            var noteDescriptions: List<String>? = null

            var positiveButtonClickedListener = OnPositiveButtonClickedListener.NULL

            var negativeButtonClickedListener = OnNegativeButtonClickedListener.NULL
        }

        val data = Data()

        /**
         * This method creates [AppRatingDialog] object. It should be called
         * after setup.

         * @param activity an activity where dialog belongs to
         * *
         * @return instance of dialog
         */
        fun create(activity: FragmentActivity): AppRatingDialog {
            Preconditions.checkNotNull(activity, "FragmentActivity cannot be null")
            return AppRatingDialog(activity, data)
        }

        /**
         * This method sets maximum number of start which will be visible in the dialog.
         * Default value is 6. If want to add some note descriptions below rating bar
         * then need to use [.setNoteDescriptions] method instead of this one.

         * @param maxRating maximum number of stars
         * *
         * @return Builder for chaining
         */
        fun setNumberOfStars(maxRating: Int): Builder {
            Preconditions.checkArgument(maxRating > 0 && maxRating <= C.InitialValues.MAX_RATING,
                    "max rating value should be between 1 and " + C.InitialValues.MAX_RATING)
            data.numberOfStars = maxRating
            return this
        }

        /**
         * This method sets note description for appropriate rating numbers.
         * If note descriptions were set by this then [.setNumberOfStars]
         * method will be ignored.

         * @param noteDescriptions list of note descriptions
         * *
         * @return Builder for chaining
         */
        fun setNoteDescriptions(noteDescriptions: List<String>): Builder {
            Preconditions.checkNotNull(noteDescriptions, "list cannot be null")
            Preconditions.checkArgument(!noteDescriptions.isEmpty(), "list cannot be empty")
            Preconditions.checkArgument(noteDescriptions.size <= C.InitialValues.MAX_RATING,
                    "size of the list can be maximally " + C.InitialValues.MAX_RATING)
            data.noteDescriptions = noteDescriptions
            return this
        }

        /**
         * This method sets number of stars which are selected by default
         * when dialog is opened.

         * @param defaultRating number of stars which should be selected
         * *
         * @return Builder for chaining
         */
        fun setDefaultRating(defaultRating: Int): Builder {
            Preconditions.checkArgument(defaultRating >= 0 && defaultRating <= data.numberOfStars,
                    "default rating value should be between 0 and " + data.numberOfStars)
            data.defaultRating = defaultRating
            return this
        }

        /**
         * This method sets dialog title.
         * The title is optional.

         * @param title dialog's title text
         * *
         * @return Builder for chaining
         * *
         * @see#setTitle(int)
         */
        fun setTitle(title: String): Builder {
            Preconditions.checkArgument(!TextUtils.isEmpty(title), "title cannot be empty")
            data.title = title
            data.titleResId = 0
            return this
        }

        /**
         * This method sets dialog title.
         * The title is optional.

         * @param resId resource id of dialog's title
         * *
         * @return Builder for chaining
         * *
         * @see#setTitle(String)
         */
        fun setTitle(@StringRes resId: Int): Builder {
            data.titleResId = resId
            data.title = null
            return this
        }

        /**
         * This method sets dialog content description text, which is visible below title.
         * The description content is optional.

         * @param content dialog's content text
         * *
         * @return Builder for chaining
         * *
         * @see#setContent(int)
         */
        fun setContent(content: String): Builder {
            Preconditions.checkArgument(!TextUtils.isEmpty(content), "content cannot be empty")
            data.content = content
            data.contentResId = 0
            return this
        }

        /**
         * This method sets dialog content description text, which is visible below title.
         * The description content is optional.

         * @param resId resource id of dialog's content text
         * *
         * @return Builder for chaining
         * *
         * @see#setContent(String)
         */
        fun setContent(@StringRes resId: Int): Builder {
            data.contentResId = resId
            data.content = null
            return this
        }

        /**
         * This method sets text of dialog's positive button.

         * @param positiveButtonText text for positive button
         * *
         * @return Builder for chaining
         * *
         * @see#setPositiveButtonText(int)
         */
        fun setPositiveButtonText(positiveButtonText: String): Builder {
            Preconditions.checkArgument(!TextUtils.isEmpty(positiveButtonText), "text cannot be empty")
            data.positiveButtonText = positiveButtonText
            data.positiveButtonTextResId = 0
            return this
        }

        /**
         * This method sets text of dialog's positive button.

         * @param resId resource id of positive button text
         * *
         * @return Builder for chaining
         * *
         * @see#setPositiveButtonText(String)
         */
        fun setPositiveButtonText(@StringRes resId: Int): Builder {
            data.positiveButtonTextResId = resId
            data.positiveButtonText = null
            return this
        }

        /**
         * This method sets text of dialog's negative button.

         * @param negativeButtonText text for negative button
         * *
         * @return Builder for chaining
         * *
         * @see#setNegativeButtonText(int)
         */
        fun setNegativeButtonText(negativeButtonText: String): Builder {
            Preconditions.checkArgument(!TextUtils.isEmpty(negativeButtonText), "text cannot be empty")
            data.negativeButtonText = negativeButtonText
            data.negativeButtonTextResId = 0
            return this
        }

        /**
         * This method sets text of dialog's negative button.

         * @param resId resource id of negative button text
         * *
         * @return Builder for chaining
         * *
         * @see#setNegativeButtonText(String)
         */
        fun setNegativeButtonText(@StringRes resId: Int): Builder {
            data.negativeButtonTextResId = resId
            data.negativeButtonText = null
            return this
        }

        /**
         * This method sets title's text color resource.
         * If not set then if uses default primary text color
         * defined in theme.

         * @param colorResId color resource id for title label
         * @return Builder for chaining
         */
        fun setTitleTextColor(@ColorRes colorResId: Int): Builder {
            data.titleTextColorResId = colorResId
            return this
        }

        /**
         * This method sets description's text color resource.
         * If not set then if uses default primary text color
         * defined in theme.

         * @param colorResId color resource id for description label
         * *
         * @return Builder for chaining
         */
        fun setDescriptionTextColor(@ColorRes colorResId: Int): Builder {
            data.descriptionTextColorResId = colorResId
            return this
        }

        /**
         * This method sets comment's color resource.
         * If not set then if uses default primary text color
         * defined in theme.

         * @param colorResId color resource id for comment edit text
         * *
         * @return Builder for chaining
         */
        fun setCommentTextColor(@ColorRes colorResId: Int): Builder {
            data.commentTextColorResId = colorResId
            return this
        }

        /**
         * This method sets comments edit text's background color resource.
         * If not set then if uses default white color will be used.

         * @param colorResId color resource id for edit text background
         * *
         * @return Builder for chaining
         */
        fun setCommentBackgroundColor(@ColorRes colorResId: Int): Builder {
            data.commentBackgroundColorResId = colorResId
            return this
        }

        /**
         * This method sets window's animation resource.
         *
         * @param animationResId resource if of animation
         * @return Builder for chaining
         */
        fun setWindowAnimation(@StyleRes animationResId: Int): Builder {
            data.windowAnimationResId = animationResId
            return this
        }

        /**
         * This method registers listener which notify when
         * positive button was clicked.

         * @param positiveButtonClickedListener click listener for positive button action
         * *
         * @return Builder for chaining
         */
        fun setPositiveButtonClickedListener(positiveButtonClickedListener: OnPositiveButtonClickedListener): Builder {
            Preconditions.checkNotNull(positiveButtonClickedListener, "listener cannot be null")
            data.positiveButtonClickedListener = positiveButtonClickedListener
            return this
        }

        /**
         * This method registers listener which notify when
         * negative button was clicked.

         * @param negativeButtonClickedListener click listener for negative button action
         * *
         * @return Builder for chaining
         */
        fun setNegativeButtonClickedListener(negativeButtonClickedListener: OnNegativeButtonClickedListener): Builder {
            Preconditions.checkNotNull(negativeButtonClickedListener, "listener cannot be null")
            data.negativeButtonClickedListener = negativeButtonClickedListener
            return this
        }
    }
}

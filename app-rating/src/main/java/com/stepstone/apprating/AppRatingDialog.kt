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
import java.io.Serializable


/**
 * This class represents dialog object produced by [Builder].
 * Dialog can be fully configurable.
 *
 * @see Builder
 */
class AppRatingDialog private constructor(private val activity: FragmentActivity, private val data: Builder.Data) {

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
    class Builder : Serializable {

        inner class Data : Serializable {

            var numberOfStars = C.InitialValues.MAX_RATING

            var defaultRating = C.InitialValues.DEFAULT_RATING

            var positiveButtonText: String? = null

            var negativeButtonText: String? = null

            var neutralButtonText: String? = null

            var title: String? = null

            var description: String? = null

            var defaultComment: String? = null

            var hint: String? = null

            var positiveButtonTextResId: Int = 0

            var negativeButtonTextResId: Int = 0

            var neutralButtonTextResId: Int = 0

            var titleResId: Int = 0

            var descriptionResId: Int = 0

            var defaultCommentResId: Int = 0

            var hintResId: Int = 0

            var starColorResId: Int = 0

            var noteDescriptionTextColor: Int = 0

            var titleTextColorResId: Int = 0

            var descriptionTextColorResId: Int = 0

            var hintTextColorResId: Int = 0

            var commentTextColorResId: Int = 0

            var commentBackgroundColorResId: Int = 0

            var windowAnimationResId: Int = 0

            var noteDescriptions: ArrayList<String>? = null
        }

        val data = Data()

        /**
         * This method creates [AppRatingDialog] object. It should be called
         * after setup.
         *
         * @param activity an activity where dialog belongs to
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
         *
         * @param maxRating maximum number of stars
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
         *
         * @param noteDescriptions list of note descriptions
         * @return Builder for chaining
         */
        fun setNoteDescriptions(noteDescriptions: List<String>): Builder {
            Preconditions.checkNotNull(noteDescriptions, "list cannot be null")
            Preconditions.checkArgument(!noteDescriptions.isEmpty(), "list cannot be empty")
            Preconditions.checkArgument(noteDescriptions.size <= C.InitialValues.MAX_RATING,
                    "size of the list can be maximally " + C.InitialValues.MAX_RATING)
            data.noteDescriptions = ArrayList(noteDescriptions)
            return this
        }

        /**
         * This method sets number of stars which are selected by default
         * when dialog is opened.
         *
         * @param defaultRating number of stars which should be selected
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
         *
         * @param title dialog's title text
         * @return Builder for chaining
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
         *
         * @param resId resource id of dialog's title
         * @return Builder for chaining
         * @see#setTitle(String)
         */
        fun setTitle(@StringRes resId: Int): Builder {
            data.titleResId = resId
            data.title = null
            return this
        }

        /**
         * This method sets dialog description description text, which is visible below title.
         * The description is optional.
         *
         * @param content dialog's description text
         * @return Builder for chaining
         * @see#setDescription(int)
         */
        fun setDescription(content: String): Builder {
            Preconditions.checkArgument(!TextUtils.isEmpty(content), "description cannot be empty")
            data.description = content
            data.descriptionResId = 0
            return this
        }

        /**
         * This method sets dialog description description text, which is visible below title.
         * The description description is optional.
         *
         * @param resId resource id of dialog's description text
         * @return Builder for chaining
         * @see#setDescription(String)
         */
        fun setDescription(@StringRes resId: Int): Builder {
            data.descriptionResId = resId
            data.description = null
            return this
        }

        /**
         * This method sets dialog default comment text.
         * The comment is optional.
         *
         * @param comment dialog's comment text
         * @return Builder for chaining
         * @see#setDefaultComment(int)
         */
        fun setDefaultComment(comment: String): Builder {
            Preconditions.checkArgument(!TextUtils.isEmpty(comment), "comment cannot be empty")
            data.defaultComment = comment
            data.defaultCommentResId = 0
            return this
        }

        /**
         * This method sets dialog default comment text.
         * The comment is optional.
         *
         * @param resId resource id of dialog's comment
         * @return Builder for chaining
         * @see#setDefaultComment(String)
         */
        fun setDefaultComment(@StringRes resId: Int): Builder {
            data.defaultCommentResId = resId
            data.defaultComment = null
            return this
        }

        /**
         * This method sets hint text.
         * The hint is optional.
         *
         * @param hint a hint text
         * @return Builder for chaining
         * @see#setHint(int)
         */
        fun setHint(hint: String): Builder {
            Preconditions.checkArgument(!TextUtils.isEmpty(hint), "hint cannot be empty")
            data.hint = hint
            data.hintResId = 0
            return this
        }

        /**
         * This method sets hint text.
         * The hint is optional.
         *
         * @param resId resource id of hint text
         * @return Builder for chaining
         * @see#setHint(String)
         */
        fun setHint(@StringRes resId: Int): Builder {
            data.hintResId = resId
            data.hint = null
            return this
        }

        /**
         * This method sets text of dialog's positive button.
         *
         * @param positiveButtonText text for positive button
         * @return Builder for chaining
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
         *
         * @param resId resource id of positive button text
         * @return Builder for chaining
         * @see#setPositiveButtonText(String)
         */
        fun setPositiveButtonText(@StringRes resId: Int): Builder {
            data.positiveButtonTextResId = resId
            data.positiveButtonText = null
            return this
        }

        /**
         * This method sets text of dialog's negative button.
         *
         * @param negativeButtonText text for negative button
         * @return Builder for chaining
         * @see#setNegativeButtonText(int)
         */
        fun setNegativeButtonText(negativeButtonText: String): Builder {
            Preconditions.checkArgument(!TextUtils.isEmpty(negativeButtonText), "text cannot be empty")
            data.negativeButtonText = negativeButtonText
            data.negativeButtonTextResId = 0
            return this
        }

        /**
         * This method sets text of dialog's neutral button.
         *
         * @param neutralButtonText text for neutral button
         * @return Builder for chaining
         * @see#setNeutralButtonText(int)
         */
        fun setNeutralButtonText(neutralButtonText: String): Builder {
            Preconditions.checkArgument(!TextUtils.isEmpty(neutralButtonText), "text cannot be empty")
            data.neutralButtonText = neutralButtonText
            data.neutralButtonTextResId = 0
            return this
        }

        /**
         * This method sets text of dialog's negative button.
         *
         * @param resId resource id of negative button text
         * @return Builder for chaining
         * @see#setNegativeButtonText(String)
         */
        fun setNegativeButtonText(@StringRes resId: Int): Builder {
            data.negativeButtonTextResId = resId
            data.negativeButtonText = null
            return this
        }

        /**
         * This method sets text of dialog's neutral button.
         *
         * @param resId resource id of neutral button text
         * @return Builder for chaining
         * @see#setNeutralButtonText(String)
         */
        fun setNeutralButtonText(@StringRes resId: Int): Builder {
            data.neutralButtonTextResId = resId
            data.neutralButtonText = null
            return this
        }

        /**
         * This method sets stars's color resource.
         * If not set then it uses accent color
         * defined in theme.
         *
         * @param colorResId color resource id for stars
         * @return Builder for chaining
         */
        fun setStarColor(@ColorRes colorResId: Int): Builder {
            data.starColorResId = colorResId
            return this
        }

        /**
         * This method sets note description's color resource.
         * If not set then it uses accent color
         * defined in theme.
         *
         * @param colorResId color resource id for note descriptions
         * @return Builder for chaining
         */
        fun setNoteDescriptionTextColor(@ColorRes colorResId: Int): Builder {
            data.noteDescriptionTextColor = colorResId
            return this
        }

        /**
         * This method sets title's text color resource.
         * If not set then it uses default primary text color
         * defined in theme.
         *
         * @param colorResId color resource id for title label
         * @return Builder for chaining
         */
        fun setTitleTextColor(@ColorRes colorResId: Int): Builder {
            data.titleTextColorResId = colorResId
            return this
        }

        /**
         * This method sets description's text color resource.
         * If not set then it uses default primary text color
         * defined in theme.
         *
         * @param colorResId color resource id for description label
         * @return Builder for chaining
         */
        fun setDescriptionTextColor(@ColorRes colorResId: Int): Builder {
            data.descriptionTextColorResId = colorResId
            return this
        }

        /**
         * This method sets hint's text color resource.
         * If not set then it uses default hint text color
         * defined in theme.
         *
         * @param colorResId color resource id for hint
         * @return Builder for chaining
         */
        fun setHintTextColor(@ColorRes colorResId: Int): Builder {
            data.hintTextColorResId = colorResId
            return this
        }

        /**
         * This method sets comment's color resource.
         * If not set then it uses default primary text color
         * defined in theme.
         *
         * @param colorResId color resource id for comment edit text
         * @return Builder for chaining
         */
        fun setCommentTextColor(@ColorRes colorResId: Int): Builder {
            data.commentTextColorResId = colorResId
            return this
        }

        /**
         * This method sets comments edit text's background color resource.
         * If not set then it uses default white color will be used.
         *
         * @param colorResId color resource id for edit text background
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
    }
}

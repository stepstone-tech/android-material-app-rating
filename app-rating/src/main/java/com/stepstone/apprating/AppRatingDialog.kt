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

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.stepstone.apprating.AppRatingDialog.Builder
import com.stepstone.apprating.PreferenceHelper.*
import com.stepstone.apprating.common.Preconditions
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

/**
 * This class represents dialog object produced by [Builder].
 * Dialog can be fully configurable.
 *
 * @see Builder
 */
class AppRatingDialog private constructor(
        private val fragmentActivity: FragmentActivity,
        private val data: Builder.Data,
        private val context: Context
) {

    private var fragment: Fragment? = null
    private var requestCode: Int = 0

    /**
     * Optional target for this fragment. This may be used, for example,
     * if this fragment is being started by another,
     * and when done wants to give a result back to the first
     *
     * @param fragment The fragment that is the target of this one.
     * @param requestCode Optional request code, for convenience if you
     * are going to call back with {@link #onActivityResult(int, int, Intent)}
     */
    fun setTargetFragment(fragment: Fragment, requestCode: Int): AppRatingDialog {
        this.fragment = fragment
        this.requestCode = requestCode
        return this
    }

    /**
     * This method shows rating dialog.
     */
    fun show() {
        AppRatingDialogFragment.newInstance(data).apply {
            fragment?.let {
                setTargetFragment(it, requestCode)
            }
            show(fragmentActivity.supportFragmentManager, "")
        }
    }

    fun showRateDialogIfMeetsConditions(): Boolean {
        val isMeetsConditions = shouldShowRateDialog()
        if (isMeetsConditions) {
            show()
        }
        return isMeetsConditions
    }

    fun shouldShowRateDialog(): Boolean {
        return getIsAgreeShowDialog(context) &&
                isOverLaunchTimes() &&
                isOverInstallDate() &&
                isOverRemindDate()
    }

    private fun isOverLaunchTimes(): Boolean {
        return getLaunchTimes(context) >= data.numberOfLaunches
    }

    private fun isOverInstallDate(): Boolean {
        return isOverDate(getInstallDate(context), data.afterInstallDay)
    }

    private fun isOverRemindDate(): Boolean {
        return isOverDate(getRemindInterval(context), data.remindInterval)
    }

    private fun isOverDate(targetDate: Long, threshold: Int): Boolean {
        return Date().time - targetDate >= threshold * 24 * 60 * 60 * 1000
    }

    fun monitor() {
        if (isFirstLaunch(context)) {
            Toast.makeText(context, "first", Toast.LENGTH_SHORT).show()
            setInstallDate(context)
        }
        setLaunchTimes(context, getLaunchTimes(context) + 1)
    }

    fun clearAgreeShowDialog(): AppRatingDialog {
        PreferenceHelper.setAgreeShowDialog(context, true)
        return this
    }

    fun clearSettingsParam(): AppRatingDialog {
        PreferenceHelper.setAgreeShowDialog(context, true)
        PreferenceHelper.clearSharedPreferences(context)
        return this
    }

    fun setAgreeShowDialog(clear: Boolean): AppRatingDialog {
        PreferenceHelper.setAgreeShowDialog(context, clear)
        return this
    }

    /**
     * This class allows to setup rating dialog
     * using builder pattern.
     */
    class Builder : Serializable {

        data class Data(
                var numberOfStars: Int = MAX_RATING,
                var defaultRating: Int = DEFAULT_RATING,
                var defaultThreshold: Int = DEFAULT_THRESHOLD,
                var afterInstallDay: Int = DEFAULT_AFTER_INSTALL_DAY,   // number of days after Installation day
                var numberOfLaunches: Int = DEFAULT_NUMBER_OF_LAUNCH,   // number of launch after last show rate dialog
                var remindInterval: Int = DEFAULT_REMIND_INTERVAL,      // number of days after click on remind me later
                val positiveButtonText: StringValue = StringValue(),
                val negativeButtonText: StringValue = StringValue(),
                val neutralButtonText: StringValue = StringValue(),
                val title: StringValue = StringValue(),
                val description: StringValue = StringValue(),
                val defaultComment: StringValue = StringValue(),
                val hint: StringValue = StringValue(),
                var commentInputEnabled: Boolean = true,
                var starColorResId: Int = 0,
                var noteDescriptionTextColor: Int = 0,
                var titleTextColorResId: Int = 0,
                var descriptionTextColorResId: Int = 0,
                var hintTextColorResId: Int = 0,
                var commentTextColorResId: Int = 0,
                var commentBackgroundColorResId: Int = 0,
                var windowAnimationResId: Int = 0,
                var noteDescriptions: ArrayList<String>? = null,
                var cancelable: Boolean? = null,
                var canceledOnTouchOutside: Boolean? = null
        ) : Serializable

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
            return AppRatingDialog(activity, data, activity.applicationContext)
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
            Preconditions.checkArgument(
                    maxRating in 1..MAX_RATING,
                    "max rating value should be between 1 and $MAX_RATING"
            )
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
            Preconditions.checkArgument(
                    noteDescriptions.size <= MAX_RATING,
                    "size of the list can be maximally $MAX_RATING"
            )
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
            Preconditions.checkArgument(
                    defaultRating >= 0 && defaultRating <= data.numberOfStars,
                    "default rating value should be between 0 and " + data.numberOfStars
            )
            data.defaultRating = defaultRating
            return this
        }

        /**
         * This method sets threshold (number of stars) which the user can send a comment for them.
         * And for the rest, no comment is needed
         *
         * @param defaultThreshold number of stars which the user can send a comment for them
         * @return Builder for chaining
         */
        fun setDefaultThreshold(defaultThreshold: Int): Builder {
            Preconditions.checkArgument(
                    defaultThreshold >= 0,
                    "default threshold value should be more than 0"
            )
            data.defaultThreshold = defaultThreshold
            return this
        }

        /**
         * This method sets afterInstallDay, number of days after Installation day for show rate dialog
         *
         * @param afterInstallDay number of days after Installation day for show rate dialog
         * @return Builder for chaining
         */
        fun setAfterInstallDay(afterInstallDay: Int): Builder {
            Preconditions.checkArgument(
                    afterInstallDay >= 0,
                    "AfterInstallDay value should be more than 0"
            )
            data.afterInstallDay = afterInstallDay
            return this
        }

        /**
         * This method sets numberOfLaunches, number of launch after last show rate dialog
         *
         * @param numberOfLaunches number of launch after last show rate dialog
         * @return Builder for chaining
         */
        fun setNumberOfLaunches(numberOfLaunches: Int): Builder {
            Preconditions.checkArgument(
                    numberOfLaunches >= 0,
                    "NumberOfLaunches value should be more than 0"
            )
            data.numberOfLaunches = numberOfLaunches
            return this
        }

        /**
         * This method sets remindInterval, number of days after click on remind me later
         *
         * @param remindInterval number of days after click on remind me later
         * @return Builder for chaining
         */
        fun setRemindInterval(remindInterval: Int): Builder {
            Preconditions.checkArgument(
                    remindInterval >= 0,
                    "RemindInterval value should be more than 0"
            )
            data.remindInterval = remindInterval
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
            data.title.text = title
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
            data.title.textResId = resId
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
            data.description.text = content
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
            data.description.textResId = resId
            return this
        }

        /**
         * This method sets comment edit text to be enabled/disabled.
         * By default it is always enabled.
         *
         * @param enabled if set to false then comment input will be not visible
         * @return Builder for chaining
         */
        fun setCommentInputEnabled(enabled: Boolean): Builder {
            data.commentInputEnabled = enabled
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
            data.defaultComment.text = comment
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
            data.defaultComment.textResId = resId
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
            data.hint.text = hint
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
            data.hint.textResId = resId
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
            Preconditions.checkArgument(
                    !TextUtils.isEmpty(positiveButtonText),
                    "text cannot be empty"
            )
            data.positiveButtonText.text = positiveButtonText
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
            data.positiveButtonText.textResId = resId
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
            Preconditions.checkArgument(
                    !TextUtils.isEmpty(negativeButtonText),
                    "text cannot be empty"
            )
            data.negativeButtonText.text = negativeButtonText
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
            Preconditions.checkArgument(
                    !TextUtils.isEmpty(neutralButtonText),
                    "text cannot be empty"
            )
            data.neutralButtonText.text = neutralButtonText
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
            data.negativeButtonText.textResId = resId
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
            data.neutralButtonText.textResId = resId
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

        /**
         * Sets whether this dialog is cancelable with the BACK key.
         *
         * @param cancelable if false when dialog cannot be canceled
         * @return Builder for chaining
         */
        fun setCancelable(cancelable: Boolean): Builder {
            data.cancelable = cancelable
            return this
        }

        /**
         * Sets whether this dialog is canceled when touched outside the window's bounds.
         * If setting to true, the dialog is set to be cancelable if not already set.
         *
         * @param cancel whether the dialog should be canceled when touched outside the window.
         * @return Builder for chaining
         */
        fun setCanceledOnTouchOutside(cancel: Boolean): Builder {
            data.canceledOnTouchOutside = cancel
            return this
        }
    }
}

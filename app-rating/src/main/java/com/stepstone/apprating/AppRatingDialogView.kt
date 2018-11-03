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

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.widget.TextViewCompat
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.stepstone.apprating.listener.OnRatingBarChangedListener
import kotlinx.android.synthetic.main.component_app_rate_dialog.view.*

/**
 * This class represents custom dialog view which contains
 * rating bar, edit box and labels.
 */
class AppRatingDialogView(context: Context) : LinearLayout(context), OnRatingBarChangedListener {

    private var noteDescriptions: List<String>? = null
    private var _hideCommentOnPositiveReview: Boolean = false
    private var _commentEditTextVisibility: Boolean = true
    private var _positiveReviewStarsNumber: Int = -1
    private lateinit var _positiveButtonTextOnPositiveReview: String
    private var _positiveButtonToChangeTextOn: Button? = null
    private lateinit var _positiveButtonText: String


    init {
        setup(context)
    }

    /**
     * This method returns current rating.
     *
     * @return number of current selected stars
     */
    val rateNumber: Float
        get() = ratingBar.rating

    /**
     * This method returns rating comment.
     *
     * @return comment text from edit box
     */
    val comment: String
        get() = commentEditText.text.toString()

    /**
     * This method sets maximum numbers of start which are visible.
     *
     * @param numberOfStars maximum number of stars
     */
    fun setNumberOfStars(numberOfStars: Int) {
        ratingBar.setNumStars(numberOfStars)
    }

    /**
     * This method sets color for stars.
     */
    fun setStarColor(@ColorRes colorResId: Int) {
        ratingBar.setStarColor(colorResId)
    }

    /**
     * This method sets color for note descriptions.
     */
    fun setNoteDescriptionTextColor(@ColorRes colorResId: Int) {
        noteDescriptionText.setTextColor(getColorFromRes(colorResId))
    }

    /**
     * This method sets note descriptions for each rating value.
     *
     * @param noteDescriptions list of note descriptions
     */
    fun setNoteDescriptions(noteDescriptions: List<String>) {
        val numberOfStars = noteDescriptions.size
        setNumberOfStars(numberOfStars)
        this.noteDescriptions = noteDescriptions
    }

    /**
     * This method sets default number of stars.
     *
     * @param defaultRating number of stars
     */
    fun setDefaultRating(defaultRating: Int) {
        ratingBar.setRating(defaultRating)
    }

    /**
     * This method sets dialog's title.
     *
     * @param title dialog's title text
     */
    fun setTitleText(title: String) {
        titleText.text = title
        titleText.visibility = View.VISIBLE
    }

    /**
     * This method sets dialog's description text.
     *
     * @param content dialog's description text
     */
    fun setDescriptionText(content: String) {
        descriptionText.text = content
        descriptionText.visibility = View.VISIBLE
    }

    /**
     * This method enable/disable comment edit text.
     *
     * @param enabled if set to false then comment input will be not visible
     */
    fun setCommentInputEnabled(enabled: Boolean) {
        _commentEditTextVisibility = enabled
        commentEditText.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    /**
     * This method sets dialog's default comment text.
     *
     * @param comment dialog's comment text
     */
    fun setDefaultComment(comment: String) {
        commentEditText.setText(comment)
    }

    /**
     * This method sets color of dialog's title.
     *
     * @param color resource id of title label color
     */
    fun setTitleTextColor(@ColorRes color: Int) {
        titleText.setTextColor(getColorFromRes(color))
    }

    /**
     * This method sets color of dialog's description.
     *
     * @param color resource id of description label color
     */
    fun setDescriptionTextColor(@ColorRes color: Int) {
        descriptionText.setTextColor(getColorFromRes(color))
    }

    /**
     * This method sets color of dialog's comment.
     *
     * @param color resource id of comment text color
     */
    fun setEditTextColor(@ColorRes color: Int) {
        commentEditText.setTextColor(getColorFromRes(color))
    }

    /**
     * This method sets color of dialog's edit text.
     *
     * @param color resource id of edit text
     */
    fun setEditBackgroundColor(@ColorRes color: Int) {
        val drawable = commentEditText.background
        drawable.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
    }

    /**
     * This method sets hint for comment edit text.
     *
     * @param hint a hint to be displayed
     */
    fun setHint(hint: String) {
        commentEditText.hint = hint
    }

    /**
     * This method setup modificators for positive review.
     * How many stars counts as possible review, should we hide comment on positive review and
     * text that should be shown on positive button if review in positive.
     *
     * It could be usefull if e.g. you want to navigate to google play in case of positive review
     * and collect feedback otherwise.
     *
     * @param positiveReviewStarsNumber how many stars counts as positive
     * @param hideCommentOnPositiveReview should we hide comments field in case of positive review
     * @param positiveButtonTextOnPositiveReview text on positive button in case of positive review
     * @param positiveButton positive button to apply changes to
     */
    fun setPositiveReviewModificators(positiveReviewStarsNumber: Int,
                                      hideCommentOnPositiveReview: Boolean,
                                      positiveButtonText: String?,
                                      positiveButtonTextOnPositiveReview: String?,
                                      positiveButton: Button) {
        _positiveReviewStarsNumber = positiveReviewStarsNumber
        _hideCommentOnPositiveReview = hideCommentOnPositiveReview
        _positiveButtonTextOnPositiveReview = positiveButtonTextOnPositiveReview?.let { it } ?: let { positiveButtonText!! }
        _positiveButtonToChangeTextOn = positiveButton
        _positiveButtonText = positiveButtonText!!
        applyPositiveRatingModifications(rateNumber.toInt())
    }

    /**
     * This method sets color of dialog's hint.
     *
     * @param color resource id of hint text color
     */
    fun setHintColor(@ColorRes color: Int) {
        commentEditText.setHintTextColor(getColorFromRes(color))
    }

    override fun onRatingChanged(rating: Int) {
        updateNoteDescriptionText(rating - 1)
        applyPositiveRatingModifications(rating)
    }

    private fun updateNoteDescriptionText(rating: Int) {
        if (noteDescriptions == null || (noteDescriptions?.isEmpty() ?: true)) {
            noteDescriptionText.visibility = View.GONE
            return
        }

        val text = noteDescriptions!![rating]
        noteDescriptionText.text = text
        noteDescriptionText.visibility = View.VISIBLE
    }

    private fun applyPositiveRatingModifications(rating: Int) {
        if (rating >= _positiveReviewStarsNumber) {
            if (_hideCommentOnPositiveReview) {
                commentEditText.visibility = View.GONE
            } else if (_commentEditTextVisibility) {
                commentEditText.visibility = View.VISIBLE
            }
            _positiveButtonToChangeTextOn?.text = _positiveButtonTextOnPositiveReview
        } else {
            if (_commentEditTextVisibility) {
                commentEditText.visibility = View.VISIBLE
            }
            _positiveButtonToChangeTextOn?.text = _positiveButtonText
        }
        _positiveButtonToChangeTextOn?.invalidate()
    }

    @SuppressLint("ResourceType")
    private fun setup(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.component_app_rate_dialog, this, true)
        ratingBar.setIsIndicator(false)
        ratingBar.setOnRatingBarChangeListener(this)

        TextViewCompat.setTextAppearance(
                titleText,
                fetchAttributeValue(R.attr.appRatingDialogTitleStyle)
        )
        TextViewCompat.setTextAppearance(
                descriptionText,
                fetchAttributeValue(R.attr.appRatingDialogDescriptionStyle)
        )
        TextViewCompat.setTextAppearance(
                noteDescriptionText,
                fetchAttributeValue(R.attr.appRatingDialogNoteDescriptionStyle)
        )
        TextViewCompat.setTextAppearance(
                commentEditText,
                fetchAttributeValue(R.attr.appRatingDialogCommentStyle)
        )
    }

    private val theme: Resources.Theme
        get() = context.theme

    private fun getColorFromRes(@ColorRes colorResId: Int): Int {
        return ResourcesCompat.getColor(context.resources, colorResId, theme)
    }

    private fun fetchAttributeValue(attr: Int): Int {
        val outValue = TypedValue()
        context.theme.resolveAttribute(attr, outValue, true)
        return outValue.data
    }
}

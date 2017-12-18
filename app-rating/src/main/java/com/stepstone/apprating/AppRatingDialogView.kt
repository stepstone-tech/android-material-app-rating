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
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.stepstone.apprating.listener.OnRatingBarChangedListener
import com.stepstone.apprating.ratingbar.CustomRatingBar


/**
 * This class represents custom dialog view which contains
 * rating bar, edit box and labels.
 */
class AppRatingDialogView(context: Context) : LinearLayout(context), OnRatingBarChangedListener {

    private lateinit var ratingBar: CustomRatingBar

    private lateinit var commentEditText: EditText

    private lateinit var titleText: TextView

    private lateinit var descriptionText: TextView

    private lateinit var noteDescriptionText: TextView

    private var noteDescriptions: List<String>? = null

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
        val numberOfStars = noteDescriptions?.size
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
     * This method sets color of dialog's hint.
     *
     * @param color resource id of hint text color
     */
    fun setHintColor(@ColorRes color: Int) {
        commentEditText.setHintTextColor(getColorFromRes(color))
    }

    override fun onRatingChanged(rating: Int) {
        updateNoteDescriptionText(rating - 1)
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

    @SuppressLint("ResourceType")
    private fun setup(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.component_app_rate_dialog, this, true)
        ratingBar = findViewById(R.id.app_rate_dialog_rating_bar)
        commentEditText = findViewById(R.id.app_rate_dialog_comment_edit_text)
        titleText = findViewById(R.id.app_rate_dialog_title_text)
        descriptionText = findViewById(R.id.app_rate_dialog_description_text)
        noteDescriptionText = findViewById(R.id.app_rate_dialog_note_description)
        ratingBar.setIsIndicator(false)
        ratingBar.setOnRatingBarChangeListener(this)

        TextViewCompat.setTextAppearance(titleText, fetchAttributeValue(R.attr.appRatingDialogTitleStyle))
        TextViewCompat.setTextAppearance(descriptionText, fetchAttributeValue(R.attr.appRatingDialogDescriptionStyle))
        TextViewCompat.setTextAppearance(noteDescriptionText, fetchAttributeValue(R.attr.appRatingDialogNoteDescriptionStyle))
        TextViewCompat.setTextAppearance(commentEditText, fetchAttributeValue(R.attr.appRatingDialogCommentStyle))
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

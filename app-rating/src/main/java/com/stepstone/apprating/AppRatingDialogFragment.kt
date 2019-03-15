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
import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.stepstone.apprating.databinding.ComponentAppRateDialogBinding
import com.stepstone.apprating.extensions.applyIfNotZero
import com.stepstone.apprating.listener.RatingDialogListener
import kotlinx.android.synthetic.main.component_app_rate_dialog.*

/**
 * This class represents rating dialog created by [com.stepstone.apprating.AppRatingDialog.Builder].

 * @see AppRatingDialog
 */
class AppRatingDialogFragment : DialogFragment() {

    private var listener: RatingDialogListener? = null
        get() {
            if (host is RatingDialogListener) {
                return host as RatingDialogListener
            }
            return targetFragment as RatingDialogListener?
        }

    private lateinit var data: AppRatingDialog.Builder.Data
    private lateinit var alertDialog: AlertDialog

    private val title by lazy { data.title.resolveText(resources) }
    private val description by lazy { data.description.resolveText(resources) }
    private val defaultComment by lazy { data.defaultComment.resolveText(resources) }
    private val hint by lazy { data.hint.resolveText(resources) }
    private val positiveButtonText by lazy { data.positiveButtonText.resolveText(resources) }
    private val neutralButtonText by lazy { data.neutralButtonText.resolveText(resources) }
    private val negativeButtonText by lazy { data.negativeButtonText.resolveText(resources) }

    private val viewModel: AppRatingViewModel by lazy {
        ViewModelProviders.of(this).get(AppRatingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<ComponentAppRateDialogBinding>(
            inflater,
            R.layout.component_app_rate_dialog, container,
            false
        ).also {
            it.viewModel = viewModel
            it.setLifecycleOwner(this)
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        data = arguments?.getSerializable(DIALOG_DATA) as AppRatingDialog.Builder.Data
        viewModel.setup(data)
    }

    private fun setupAlertDialog(context: Context) {

        ratingBar.setIsIndicator(false)
        //ratingBar.setOnRatingBarChangeListener(this)

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


//        setupPositiveButton(dialogView, builder)
//        setupNegativeButton(builder)
//        setupNeutralButton(builder)
//        setupTitleAndContentMessages(dialogView)
//        setupHint(dialogView)
//        setupColors(dialogView)
//        setupInputBox()
//        setupRatingBar()

//        builder.setView(dialogView)
        setupAnimation()
        setupCancelable()

    }

//    private fun setupRatingBar() {
//        setNumberOfStars(data.numberOfStars)
//
//        val isEmpty = data.noteDescriptions?.isEmpty() ?: true
//        if (!isEmpty) {
//            setNoteDescriptions(data.noteDescriptions!!)
//        }
//
//        setDefaultRating(data.defaultRating)
//    }

//    private fun setupInputBox() {
//        setCommentInputEnabled(data.commentInputEnabled)
//    }

    private fun setupCancelable() {
        data.cancelable?.let { isCancelable = it }
        data.canceledOnTouchOutside?.let { alertDialog.setCanceledOnTouchOutside(it) }
    }

    private fun setupAnimation() {
        if (data.windowAnimationResId != 0) {
            alertDialog.window.attributes.windowAnimations = data.windowAnimationResId
        }
    }

    private fun setupColors() {
        data.titleTextColorResId.applyIfNotZero {
            setTitleTextColor(this)
        }
        data.descriptionTextColorResId.applyIfNotZero {
            setDescriptionTextColor(this)
        }
        data.commentTextColorResId.applyIfNotZero {
            setEditTextColor(this)
        }
        data.commentBackgroundColorResId.applyIfNotZero {
            setEditBackgroundColor(this)
        }
        data.hintTextColorResId.applyIfNotZero {
            setHintColor(this)
        }
        data.starColorResId.applyIfNotZero {
            setStarColor(this)
        }
        data.noteDescriptionTextColor.applyIfNotZero {
            setNoteDescriptionTextColor(this)
        }
    }

    private fun setupTitleAndContentMessages() {
        if (!title.isNullOrEmpty()) {
            setTitleText(title!!)
        }
        if (!description.isNullOrEmpty()) {
            setDescriptionText(description!!)
        }
        if (!defaultComment.isNullOrEmpty()) {
            setDefaultComment(defaultComment!!)
        }
    }

//    private fun setupHint(dialogView: AppRatingDialogView) {
//        if (!TextUtils.isEmpty(hint)) {
//            setHint(hint!!)
//        }
//    }
//
//    private fun setupNegativeButton(builder: AlertDialog.Builder) {
//        if (!TextUtils.isEmpty(negativeButtonText)) {
//            builder.setNegativeButton(negativeButtonText) { _, _ ->
//                listener?.onNegativeButtonClicked()
//            }
//        }
//    }
//
//    private fun setupPositiveButton(dialogView: AppRatingDialogView, builder: AlertDialog.Builder) {
//        if (!TextUtils.isEmpty(positiveButtonText)) {
//            builder.setPositiveButton(positiveButtonText) { _, _ ->
//                val rateNumber = rateNumber.toInt()
//                val comment = comment
//                listener?.onPositiveButtonClicked(rateNumber, comment)
//            }
//        }
//    }

    private fun setupNeutralButton(builder: AlertDialog.Builder) {
        if (!TextUtils.isEmpty(neutralButtonText)) {
            builder.setNeutralButton(neutralButtonText) { _, _ ->
                listener?.onNeutralButtonClicked()
            }
        }
    }

    companion object {

        fun newInstance(data: AppRatingDialog.Builder.Data): AppRatingDialogFragment {
            val fragment = AppRatingDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable(DIALOG_DATA, data)
            fragment.arguments = bundle
            return fragment
        }
    }


    private var noteDescriptions: List<String>? = null

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
//        drawable.colorFilter =
//            PorterDuffColorFilter(ContextCompat.getColor(context!!, color), PorterDuff.Mode.SRC_IN)
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

    fun onRatingChanged(rating: Int) {
        updateNoteDescriptionText(rating - 1)
    }

    private fun updateNoteDescriptionText(rating: Int) {
        if (noteDescriptions == null || (noteDescriptions?.isEmpty() ?: true)) {
            noteDescriptionText.visibility = View.GONE
            return
        }

        if (rating >= 0) {
            val text = noteDescriptions!![rating]
            noteDescriptionText.text = text
            noteDescriptionText.visibility = View.VISIBLE
        }
    }

    @SuppressLint("ResourceType")
    private fun setup(context: Context) {

    }

    private val theme: Resources.Theme
        get() = context!!.theme

    private fun getColorFromRes(@ColorRes colorResId: Int): Int {
        return ResourcesCompat.getColor(context!!.resources, colorResId, theme)
    }

    private fun fetchAttributeValue(attr: Int): Int {
        val outValue = TypedValue()
        context!!.theme.resolveAttribute(attr, outValue, true)
        return outValue.data
    }
}

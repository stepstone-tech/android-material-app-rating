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

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import android.text.TextUtils
import com.stepstone.apprating.PreferenceHelper.setAgreeShowDialog
import com.stepstone.apprating.PreferenceHelper.setRemindInterval
import com.stepstone.apprating.extensions.applyIfNotZero
import com.stepstone.apprating.listener.RatingDialogListener

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
    private lateinit var dialogView: AppRatingDialogView

    private val title by lazy { data.title.resolveText(resources) }
    private val description by lazy { data.description.resolveText(resources) }
    private val defaultComment by lazy { data.defaultComment.resolveText(resources) }
    private val hint by lazy { data.hint.resolveText(resources) }
    private val positiveButtonText by lazy { data.positiveButtonText.resolveText(resources) }
    private val neutralButtonText by lazy { data.neutralButtonText.resolveText(resources) }
    private val negativeButtonText by lazy { data.negativeButtonText.resolveText(resources) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return setupAlertDialog(activity!!)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putFloat(CURRENT_RATE_NUMBER, dialogView.rateNumber)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val rateNumber: Float? = savedInstanceState?.getFloat(CURRENT_RATE_NUMBER)
        if (rateNumber != null) {
            dialogView.setDefaultRating(rateNumber.toInt())
        }
    }

    private fun setupAlertDialog(context: Context): AlertDialog {
        dialogView = AppRatingDialogView(context)
        val builder = AlertDialog.Builder(activity!!)
        data = arguments?.getSerializable(DIALOG_DATA) as AppRatingDialog.Builder.Data

        setupPositiveButton(dialogView, builder)
        setupNegativeButton(builder)
        setupNeutralButton(builder)
        setupTitleAndContentMessages(dialogView)
        setupHint(dialogView)
        setupColors(dialogView)
        setupInputBox()
        setupRatingBar()

        builder.setView(dialogView)
        alertDialog = builder.create()
        if (data.dialogBackgroundColorResId != 0) {
            alertDialog.window?.setBackgroundDrawableResource(data.dialogBackgroundColorResId)
        }
        setupAnimation()
        setupCancelable()
        return alertDialog
    }

    private fun setupRatingBar() {
        dialogView.setNumberOfStars(data.numberOfStars)

        val isEmpty = data.noteDescriptions?.isEmpty() ?: true
        if (!isEmpty) {
            dialogView.setNoteDescriptions(data.noteDescriptions!!)
        }

        dialogView.setDefaultRating(data.defaultRating)
    }

    private fun setupInputBox() {
        dialogView.setCommentInputEnabled(data.commentInputEnabled)
        dialogView.setThreshold(data.threshold)
    }

    private fun setupCancelable() {
        data.cancelable?.let { isCancelable = it }
        data.canceledOnTouchOutside?.let { alertDialog.setCanceledOnTouchOutside(it) }
    }

    private fun setupAnimation() {
        if (data.windowAnimationResId != 0) {
            alertDialog.window.attributes.windowAnimations = data.windowAnimationResId
        }
    }

    private fun setupColors(dialogView: AppRatingDialogView) {
        data.titleTextColorResId.applyIfNotZero {
            dialogView.setTitleTextColor(this)
        }
        data.descriptionTextColorResId.applyIfNotZero {
            dialogView.setDescriptionTextColor(this)
        }
        data.commentTextColorResId.applyIfNotZero {
            dialogView.setEditTextColor(this)
        }
        data.commentBackgroundColorResId.applyIfNotZero {
            dialogView.setEditBackgroundColor(this)
        }
        data.hintTextColorResId.applyIfNotZero {
            dialogView.setHintColor(this)
        }
        data.starColorResId.applyIfNotZero {
            dialogView.setStarColor(this)
        }
        data.noteDescriptionTextColor.applyIfNotZero {
            dialogView.setNoteDescriptionTextColor(this)
        }
    }

    private fun setupTitleAndContentMessages(dialogView: AppRatingDialogView) {
        if (!title.isNullOrEmpty()) {
            dialogView.setTitleText(title!!)
        }
        if (!description.isNullOrEmpty()) {
            dialogView.setDescriptionText(description!!)
        }
        if (!defaultComment.isNullOrEmpty()) {
            dialogView.setDefaultComment(defaultComment!!)
        }
    }

    private fun setupHint(dialogView: AppRatingDialogView) {
        if (!TextUtils.isEmpty(hint)) {
            dialogView.setHint(hint!!)
        }
    }

    private fun setupNegativeButton(builder: AlertDialog.Builder) {
        if (!TextUtils.isEmpty(negativeButtonText)) {
            builder.setNegativeButton(negativeButtonText) { _, _ ->
                setAgreeShowDialog(context, false)
                listener?.onNegativeButtonClicked()
            }
        }
    }

    private fun setupPositiveButton(dialogView: AppRatingDialogView, builder: AlertDialog.Builder) {
        if (!TextUtils.isEmpty(positiveButtonText)) {
            builder.setPositiveButton(positiveButtonText) { _, _ ->
                val rateNumber = dialogView.rateNumber.toInt()
                setAgreeShowDialog(context, false)
                if (rateNumber <= data.threshold) {
                    val comment = dialogView.comment
                    listener?.onPositiveButtonClickedWithComment(rateNumber, comment)
                } else { //rating>threshold
                    listener?.onPositiveButtonClickedWithoutComment(rateNumber)
                }
            }
        }
    }

    private fun setupNeutralButton(builder: AlertDialog.Builder) {
        if (!TextUtils.isEmpty(neutralButtonText)) {
            builder.setNeutralButton(neutralButtonText) { _, _ ->
                setRemindInterval(context)
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
}

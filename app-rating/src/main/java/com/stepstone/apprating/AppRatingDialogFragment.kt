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
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import com.stepstone.apprating.listener.RatingDialogListener

/**
 * This class represents rating dialog created by [com.stepstone.apprating.AppRatingDialog.Builder].

 * @see AppRatingDialog
 */
class AppRatingDialogFragment : DialogFragment() {

    private lateinit var listener: RatingDialogListener

    private lateinit var data: AppRatingDialog.Builder.Data

    private lateinit var alertDialog: AlertDialog

    private lateinit var dialogView: AppRatingDialogView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return setupAlertDialog(activity)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (dialogView != null) {
            outState?.putFloat(C.ExtraKeys.CURRENT_RATE_NUMBER, dialogView.rateNumber)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val rateNumber: Float? = savedInstanceState?.getFloat(C.ExtraKeys.CURRENT_RATE_NUMBER)
        if (rateNumber != null) {
            dialogView?.setDefaultRating(rateNumber.toInt())
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (host is RatingDialogListener) {
            listener = host as RatingDialogListener
        } else {
            throw AssertionError("Host does not implement RatingDialogListener!")
        }
    }

    private fun setupAlertDialog(context: Context): AlertDialog {
        dialogView = AppRatingDialogView(context)
        val builder = AlertDialog.Builder(activity)
        data = arguments.getSerializable(C.ExtraKeys.DATA) as AppRatingDialog.Builder.Data

        setupPositiveButton(dialogView, builder)
        setupNegativeButton(builder)
        setupNeutralButton(builder)
        setupTitleAndContentMessages(dialogView)
        setupHint(dialogView)
        setupColors(dialogView)

        dialogView.setNumberOfStars(data.numberOfStars)

        val isEmpty = data.noteDescriptions?.isEmpty() ?: true
        if (!isEmpty) {
            dialogView.setNoteDescriptions(data.noteDescriptions!!)
        }

        dialogView.setDefaultRating(data.defaultRating)
        builder.setView(dialogView)
        alertDialog = builder.create()

        if (data.windowAnimationResId != 0) {
            alertDialog.window.attributes.windowAnimations = data.windowAnimationResId
        }

        return alertDialog
    }

    private fun setupColors(dialogView: AppRatingDialogView) {
        if (data.titleTextColorResId != 0) {
            dialogView.setTitleTextColor(data.titleTextColorResId)
        }
        if (data.descriptionTextColorResId != 0) {
            dialogView.setDescriptionTextColor(data.descriptionTextColorResId)
        }
        if (data.commentTextColorResId != 0) {
            dialogView.setEditTextColor(data.commentTextColorResId)
        }
        if (data.commentBackgroundColorResId != 0) {
            dialogView.setEditBackgroundColor(data.commentBackgroundColorResId)
        }
        if (data.hintTextColorResId != 0) {
            dialogView.setHintColor(data.hintTextColorResId)
        }
        if (data.starColorResId != 0) {
            dialogView.setStarColor(data.starColorResId)
        }
        if (data.noteDescriptionTextColor != 0) {
            dialogView.setNoteDescriptionTextColor(data.noteDescriptionTextColor)
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
            builder.setNegativeButton(negativeButtonText) { dialog, which ->
                listener?.onNegativeButtonClicked()
            }
        }
    }

    private fun setupPositiveButton(dialogView: AppRatingDialogView, builder: AlertDialog.Builder) {
        if (!TextUtils.isEmpty(positiveButtonText)) {
            builder.setPositiveButton(positiveButtonText) { dialog, which ->
                val rateNumber = dialogView.rateNumber.toInt()
                val comment = dialogView.comment
                listener?.onPositiveButtonClicked(rateNumber, comment)
            }
        }
    }

    private fun setupNeutralButton(builder: AlertDialog.Builder) {
        if (!TextUtils.isEmpty(neutralButtonText)) {
            builder.setNeutralButton(neutralButtonText) { dialog, which ->
                listener?.onNeutralButtonClicked()
            }
        }
    }

    private val title: String?
        get() {
            if (TextUtils.isEmpty(data.title)) {
                if (data.titleResId == 0) {
                    return null
                }
                return getString(data.titleResId)
            }
            return data.title
        }

    private val description: String?
        get() {
            if (TextUtils.isEmpty(data.description)) {
                if (data.descriptionResId == 0) {
                    return null
                }
                return getString(data.descriptionResId)
            }
            return data.description
        }

    private val defaultComment: String?
        get() {
            if (TextUtils.isEmpty(data.defaultComment)) {
                if (data.defaultCommentResId == 0) {
                    return null
                }
                return getString(data.defaultCommentResId)
            }
            return data.defaultComment
        }

    private val hint: String?
        get() {
            if (TextUtils.isEmpty(data.hint)) {
                if (data.hintResId == 0) {
                    return null
                }
                return getString(data.hintResId)
            }
            return data.hint
        }

    private val positiveButtonText: String?
        get() {
            if (TextUtils.isEmpty(data.positiveButtonText)) {
                if (data.positiveButtonTextResId == 0) {
                    return null
                }
                return getString(data.positiveButtonTextResId)
            }
            return data.positiveButtonText
        }

    private val neutralButtonText: String?
        get() {
            if (TextUtils.isEmpty(data.neutralButtonText)) {
                if (data.neutralButtonTextResId == 0) {
                    return null
                }
                return getString(data.neutralButtonTextResId)
            }
            return data.neutralButtonText
        }

    private val negativeButtonText: String?
        get() {
            if (TextUtils.isEmpty(data.negativeButtonText)) {
                if (data.negativeButtonTextResId == 0) {
                    return null
                }
                return getString(data.negativeButtonTextResId)
            }
            return data.negativeButtonText
        }

    companion object {

        fun newInstance(data: AppRatingDialog.Builder.Data): AppRatingDialogFragment {
            val fragment = AppRatingDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable(C.ExtraKeys.DATA, data)
            fragment.arguments = bundle
            return fragment
        }
    }
}

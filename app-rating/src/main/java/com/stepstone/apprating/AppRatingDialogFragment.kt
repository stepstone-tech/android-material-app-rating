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

/**
 * This class represents rating dialog created by [com.stepstone.apprating.AppRatingDialog.Builder].

 * @see AppRatingDialog
 */
class AppRatingDialogFragment : DialogFragment() {

    private var data: AppRatingDialog.Builder.Data? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return setupAlertDialog(activity)
    }

    private fun setupAlertDialog(context: Context): AlertDialog {
        val dialogView = AppRatingDialogView(context)

        val builder = AlertDialog.Builder(activity)

        setupPositiveButton(dialogView, builder)
        setupNegativeButton(builder)
        setupTitleAndContentMessages(dialogView)
        setupColors(dialogView)

        dialogView.setNumberOfStars(data!!.numberOfStars)

        if (data!!.noteDescriptions != null && !data!!.noteDescriptions!!.isEmpty()) {
            dialogView.setNoteDescriptions(data!!.noteDescriptions!!)
        }

        dialogView.setDefaultRating(data!!.defaultRating)
        builder.setView(dialogView)
        return builder.create()
    }

    private fun setupColors(dialogView: AppRatingDialogView) {
        if (data!!.titleColorResId != 0) {
            dialogView.setTitleColor(data!!.titleColorResId)
        }
        if (data!!.contentColorResId != 0) {
            dialogView.setContentColor(data!!.contentColorResId)
        }
    }

    private fun setupTitleAndContentMessages(dialogView: AppRatingDialogView) {
        if (!TextUtils.isEmpty(title)) {
            dialogView.setTitleText(title!!)
        }
        if (!TextUtils.isEmpty(content)) {
            dialogView.setContentText(content!!)
        }
    }

    private fun setupNegativeButton(builder: AlertDialog.Builder) {
        if (!TextUtils.isEmpty(negativeButtonText)) {
            builder.setNegativeButton(negativeButtonText) { dialog, which -> data!!.negativeButtonClickedListener.onClicked() }
        }
    }

    private fun setupPositiveButton(dialogView: AppRatingDialogView, builder: AlertDialog.Builder) {
        if (!TextUtils.isEmpty(positiveButtonText)) {
            builder.setPositiveButton(positiveButtonText) { dialog, which ->
                val rateNumber = dialogView.rateNumber.toInt()
                val comment = dialogView.comment
                data!!.positiveButtonClickedListener.onClicked(rateNumber, comment)
            }
        }
    }

    private val title: String?
        get() {
            if (TextUtils.isEmpty(data!!.title)) {
                if (data!!.titleResId == 0) {
                    return null
                }
                return getString(data!!.titleResId)
            }
            return data!!.title
        }

    private val content: String?
        get() {
            if (TextUtils.isEmpty(data!!.content)) {
                if (data!!.contentResId == 0) {
                    return null
                }
                return getString(data!!.contentResId)
            }
            return data!!.content
        }

    private val positiveButtonText: String?
        get() {
            if (TextUtils.isEmpty(data!!.positiveButtonText)) {
                if (data!!.positiveButtonTextResId == 0) {
                    return null
                }
                return getString(data!!.positiveButtonTextResId)
            }
            return data!!.positiveButtonText
        }

    private val negativeButtonText: String?
        get() {
            if (TextUtils.isEmpty(data!!.negativeButtonText)) {
                if (data!!.negativeButtonTextResId == 0) {
                    return null
                }
                return getString(data!!.negativeButtonTextResId)
            }
            return data!!.negativeButtonText
        }

    fun setData(data: AppRatingDialog.Builder.Data) {
        this.data = data
    }

    companion object {

        fun newInstance(data: AppRatingDialog.Builder.Data): AppRatingDialogFragment {
            val fragment = AppRatingDialogFragment()
            fragment.setData(data)
            return fragment
        }
    }
}

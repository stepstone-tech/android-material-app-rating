package com.stepstone.apprating.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.stepstone.apprating.AppRatingDialog
import com.stepstone.apprating.listener.RatingDialogListener
import kotlinx.android.synthetic.main.fragment_samples.*

class SamplesFragment : Fragment(), RatingDialogListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_samples, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDialogButton_1.setOnClickListener { showRatingDialog() }
    }

    private fun showRatingDialog() {
        AppRatingDialog.Builder()
            .setPositiveButtonText("Submit")
            .setNegativeButtonText("Cancel")
            .setNeutralButtonText("Later")
            .setDefaultRating(2)
            .setTitle("Rate this application")
            .setDescription("Please select some stars and give your feedback")
            .setStarColor(R.color.starColor)
            .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
            .setTitleTextColor(R.color.titleTextColor)
            .setDescriptionTextColor(R.color.descriptionTextColor)
            .setCommentTextColor(R.color.commentTextColor)
            .setCommentBackgroundColor(R.color.colorPrimaryDark)
            .setWindowAnimation(R.style.MyDialogSlideHorizontalAnimation)
            .setHint("Please write your comment here ...")
            .setHintTextColor(R.color.hintTextColor)
            .setCancelable(false)
            .setCanceledOnTouchOutside(false)
            .create(activity!!)
            .setTargetFragment(this, 0)
            .show()
    }

    override fun onPositiveButtonClicked(rate: Int, comment: String) {
        Toast.makeText(activity, "Rate : $rate\nComment : $comment", Toast.LENGTH_LONG).show()
    }

    override fun onNegativeButtonClicked() {
        Toast.makeText(activity, "Negative button clicked", Toast.LENGTH_LONG).show()
    }

    override fun onNeutralButtonClicked() {
        Toast.makeText(activity, "Neutral button clicked", Toast.LENGTH_LONG).show()
    }
}
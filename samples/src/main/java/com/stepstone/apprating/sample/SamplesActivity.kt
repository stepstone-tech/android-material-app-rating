package com.stepstone.apprating.sample

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import com.stepstone.apprating.AppRatingDialog
import com.stepstone.apprating.listener.RatingDialogListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Arrays

class SamplesActivity : FragmentActivity(), RatingDialogListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showDialogButton_1.setOnClickListener { showRatingDialog_example1() }
        showDialogButton_2.setOnClickListener { showRatingDialog_example2() }
        showDialogButton_3.setOnClickListener { showRatingDialog_example3() }
        showDialogButton_4.setOnClickListener { showRatingDialog_example4() }
    }

    private fun showRatingDialog_example1() {
        AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNeutralButtonText("Later")
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
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
                .create(this@SamplesActivity)
                .show()
    }

    private fun showRatingDialog_example2() {
        AppRatingDialog.Builder()
                .setPositiveButtonText("Send feedback")
                .setNumberOfStars(6)
                .setDefaultRating(4)
                .setTitle("Rate this application")
                .setTitleTextColor(R.color.titleTextColor)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.hintTextColor)
                .create(this@SamplesActivity)
                .show()
    }

    private fun showRatingDialog_example3() {
        AppRatingDialog.Builder()
                .setPositiveButtonText(R.string.send_review)
                .setNegativeButtonText(R.string.cancel)
                .setNeutralButtonText(R.string.later)
                .setNumberOfStars(5)
                .setDefaultRating(3)
                .setTitle(R.string.title)
                .setTitleTextColor(R.color.titleTextColor)
                .setDescription(R.string.description)
                .setDefaultComment(R.string.defaultComment)
                .setDescriptionTextColor(R.color.descriptionTextColor)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.MyDialogSlideVerticalAnimation)
                .create(this@SamplesActivity)
                .show()
    }

    private fun showRatingDialog_example4() {
        AppRatingDialog.Builder()
                .setPositiveButtonText("Send feedback")
                .setNumberOfStars(6)
                .setDefaultRating(4)
                .setTitle("Rate this application")
                .setTitleTextColor(R.color.titleTextColor)
                .setCommentInputEnabled(false)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.hintTextColor)
                .create(this@SamplesActivity)
                .show()
    }

    override fun onPositiveButtonClicked(rate: Int, comment: String) {
        Toast.makeText(this@SamplesActivity, "Rate : $rate\nComment : $comment", Toast.LENGTH_LONG).show()
    }

    override fun onNegativeButtonClicked() {
        Toast.makeText(this@SamplesActivity, "Negative button clicked", Toast.LENGTH_LONG).show()
    }

    override fun onNeutralButtonClicked() {
        Toast.makeText(this@SamplesActivity, "Neutral button clicked", Toast.LENGTH_LONG).show()
    }
}

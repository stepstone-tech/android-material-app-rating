package com.stepstone.apprating.sample

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.Button
import android.widget.Toast
import com.stepstone.apprating.AppRatingDialog
import com.stepstone.apprating.listener.RatingDialogListener
import java.util.*

class SampleActivity : FragmentActivity(), RatingDialogListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById(R.id.show_dialog_button1) as Button
        button1.setOnClickListener { showRatingDialog_example1() }

        val button2 = findViewById(R.id.show_dialog_button2) as Button
        button2.setOnClickListener { showRatingDialog_example2() }

        val button3 = findViewById(R.id.show_dialog_button3) as Button
        button3.setOnClickListener { showRatingDialog_example3() }
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
                .setTitleTextColor(R.color.titleTextColor)
                .setDescriptionTextColor(R.color.descriptionTextColor)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.MyDialogSlideHorizontalAnimation)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.hintTextColor)
                .create(this@SampleActivity)
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
                .create(this@SampleActivity)
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
                .setDescriptionTextColor(R.color.descriptionTextColor)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.MyDialogSlideVerticalAnimation)
                .create(this@SampleActivity)
                .show()
    }

    override fun onPositiveButtonClicked(rate: Int, comment: String) {
        Toast.makeText(this@SampleActivity, "Rate : $rate\nComment : $comment", Toast.LENGTH_LONG).show()
    }

    override fun onNegativeButtonClicked() {
    }

    override fun onNeutralButtonClicked() {
    }
}

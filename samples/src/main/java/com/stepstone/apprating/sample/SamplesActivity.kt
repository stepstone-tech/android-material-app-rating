package com.stepstone.apprating.sample

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.stepstone.apprating.AppRatingDialog
import com.stepstone.apprating.listener.RatingDialogListener
import kotlinx.android.synthetic.main.activity_samples.*

class SamplesActivity : FragmentActivity(), RatingDialogListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_samples)

        showDialogButton_1.setOnClickListener { showRatingDialog_example1() }
        showDialogButton_2.setOnClickListener { showRatingDialog_example2() }
        showDialogButton_3.setOnClickListener { showRatingDialog_example3() }
        showDialogButton_4.setOnClickListener { showRatingDialog_example4() }

        buildRatingDialog().monitor()
        buildRatingDialog().showRateDialogIfMeetsConditions()
    }

    private fun buildRatingDialog(): AppRatingDialog {
        return AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNeutralButtonText("Later")
                .setNoteDescriptions(listOf("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                .setDefaultRating(2)
                .setDefaultThreshold(4)
                .setAfterInstallDay(0)
                .setNumberOfLaunches(3)
                .setRemindInterval(2)
                .setTitle("Rate this application")
                .setDescription("Please select some stars and give your feedback")
                .setStarColor(R.color.starColor)
                .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
                .setTitleTextColor(R.color.titleTextColor)
                .setDescriptionTextColor(R.color.descriptionTextColor)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setDialogBackgroundColor(R.color.rateAppDialogBackgroundColor)
                .setWindowAnimation(R.style.MyDialogSlideHorizontalAnimation)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.hintTextColor)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(this@SamplesActivity)
    }

    private fun showRatingDialog_example1() {
        AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNeutralButtonText("Later")
                .setNoteDescriptions(listOf("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                .setDefaultRating(2)
                .setDefaultThreshold(4)
                .setTitle("Rate this application")
                .setDescription("Please select some stars and give your feedback")
                .setStarColor(R.color.starColor)
                .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
                .setTitleTextColor(R.color.titleTextColor)
                .setDescriptionTextColor(R.color.descriptionTextColor)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setDialogBackgroundColor(R.color.rateAppDialogBackgroundColor)
                .setWindowAnimation(R.style.MyDialogSlideHorizontalAnimation)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.hintTextColor)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(this@SamplesActivity)
                .show()
    }

    private fun showRatingDialog_example2() {
        AppRatingDialog.Builder()
                .setPositiveButtonText("Send feedback")
                .setNumberOfStars(6)
                .setDefaultRating(0)
                .setTitle("Rate this application")
                .setDefaultComment(R.string.defaultComment)
                .setTitleTextColor(R.color.titleTextColor)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setDialogBackgroundColor(R.color.rateAppDialogBackgroundColor)
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
                .setNoteDescriptions(listOf("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                .setNumberOfStars(5)
                .setDefaultRating(3)
                .setTitle(R.string.title)
                .setDescription(R.string.description)
                .setHint(R.string.defaultHint)
                .setStarColor(R.color.starColor2)
                .setTitleTextColor(R.color.titleTextColor2)
                .setDescriptionTextColor(R.color.descriptionTextColor2)
                .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor2)
                .setCommentTextColor(R.color.commentTextColor2)
                .setCommentBackgroundColor(R.color.commentBackgroundColor2)
                .setHintTextColor(R.color.hintTextColor2)
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
                .setTitleTextColor(R.color.titleTextColor2)
                .setCommentInputEnabled(false)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.hintTextColor2)
                .setCommentTextColor(R.color.commentTextColor2)
                .setCommentBackgroundColor(R.color.commentBackgroundColor2)
                .create(this@SamplesActivity)
                .show()
    }

    override fun onPositiveButtonClickedWithComment(rate: Int, comment: String) {
            Toast.makeText(this@SamplesActivity, "Rate : $rate\nComment : $comment", Toast.LENGTH_LONG).show()
    }

    override fun onPositiveButtonClickedWithoutComment(rate: Int) {
        Toast.makeText(this@SamplesActivity, "Rate : $rate", Toast.LENGTH_LONG).show()
    }

    override fun onNegativeButtonClicked() {
        Toast.makeText(this@SamplesActivity, "Negative button clicked", Toast.LENGTH_LONG).show()
    }

    override fun onNeutralButtonClicked() {
        Toast.makeText(this@SamplesActivity, "Neutral button clicked", Toast.LENGTH_LONG).show()
    }
}

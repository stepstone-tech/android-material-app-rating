package com.stepstone.apprating.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.OnPositiveButtonClickedListener;

import java.util.Arrays;

public class SampleActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.show_dialog_button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog_example1();
            }
        });

        Button button2 = (Button) findViewById(R.id.show_dialog_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog_example2();
            }
        });

        Button button3 = (Button) findViewById(R.id.show_dialog_button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog_example3();
            }
        });
    }

    private void showRatingDialog_example1() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                .setDefaultRating(2)
                .setTitle("Rate this application")
                .setContent("Please select some stars and give your feedback")
                .setTitleColor(R.color.titleTextColor)
                .setContentColor(R.color.contentTextColor)
                .setPositiveButtonClickedListener(listener)
                .create(SampleActivity.this)
                .show();
    }

    private void showRatingDialog_example2() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Send feedback")
                .setNumberOfStars(6)
                .setDefaultRating(4)
                .setTitle("Rate this application")
                .setPositiveButtonClickedListener(listener)
                .create(SampleActivity.this)
                .show();
    }

    private void showRatingDialog_example3() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText(R.string.send_review)
                .setNegativeButtonText(R.string.cancel)
                .setNumberOfStars(5)
                .setDefaultRating(3)
                .setTitle(R.string.title)
                .setTitleColor(R.color.titleTextColor)
                .setContent(R.string.content)
                .setContentColor(R.color.contentTextColor)
                .setPositiveButtonClickedListener(listener)
                .create(SampleActivity.this)
                .show();
    }

    private OnPositiveButtonClickedListener listener = new OnPositiveButtonClickedListener() {
        @Override
        public void onClicked(int rate, String comment) {
            Toast.makeText(SampleActivity.this, "Rate : " + rate + "\nComment : " + comment, Toast.LENGTH_LONG).show();
        }
    };
}

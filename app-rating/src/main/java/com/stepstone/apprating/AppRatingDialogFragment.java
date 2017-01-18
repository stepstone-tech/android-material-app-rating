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

package com.stepstone.apprating;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * This class represents rating dialog created by {@link com.stepstone.apprating.AppRatingDialog.Builder}.
 *
 * @see AppRatingDialog
 */
public class AppRatingDialogFragment extends DialogFragment {

    public static AppRatingDialogFragment newInstance(AppRatingDialog.Builder.Data data) {
        AppRatingDialogFragment fragment = new AppRatingDialogFragment();
        fragment.setData(data);
        return fragment;
    }

    private AppRatingDialog.Builder.Data data;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return setupAlertDialog(getActivity());
    }

    private AlertDialog setupAlertDialog(Context context) {
        final AppRatingDialogView dialogView = new AppRatingDialogView(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        setupPositiveButton(dialogView, builder);
        setupNegativeButton(builder);
        setupTitleAndContentMessages(dialogView);
        setupColors(dialogView);

        dialogView.setNumberOfStars(data.numberOfStars);

        if (data.noteDescriptions != null && !data.noteDescriptions.isEmpty()) {
            dialogView.setNoteDescriptions(data.noteDescriptions);
        }

        dialogView.setDefaultRating(data.defaultRating);
        builder.setView(dialogView);
        return builder.create();
    }

    private void setupColors(AppRatingDialogView dialogView) {
        if (data.titleColorResId != 0) {
            dialogView.setTitleColor(data.titleColorResId);
        }
        if (data.contentColorResId != 0) {
            dialogView.setContentColor(data.contentColorResId);
        }
    }

    private void setupTitleAndContentMessages(AppRatingDialogView dialogView) {
        if (!TextUtils.isEmpty(getTitle())) {
            dialogView.setTitleText(getTitle());
        }
        if (!TextUtils.isEmpty(getContent())) {
            dialogView.setContentText(getContent());
        }
    }

    private void setupNegativeButton(AlertDialog.Builder builder) {
        if (!TextUtils.isEmpty(getNegativeButtonText())) {
            builder.setNegativeButton(getNegativeButtonText(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    data.negativeButtonClickedListener.onClicked();
                }
            });
        }
    }

    private void setupPositiveButton(final AppRatingDialogView dialogView, AlertDialog.Builder builder) {
        if (!TextUtils.isEmpty(getPositiveButtonText())) {
            builder.setPositiveButton(getPositiveButtonText(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final int rateNumber = (int) dialogView.getRateNumber();
                    final String comment = dialogView.getComment();
                    data.positiveButtonClickedListener.onClicked(rateNumber, comment);
                }
            });
        }
    }

    private String getTitle() {
        if (TextUtils.isEmpty(data.title)) {
            if (data.titleResId == 0) {
                return null;
            }
            return getString(data.titleResId);
        }
        return data.title;
    }

    private String getContent() {
        if (TextUtils.isEmpty(data.content)) {
            if (data.contentResId == 0) {
                return null;
            }
            return getString(data.contentResId);
        }
        return data.content;
    }

    private String getPositiveButtonText() {
        if (TextUtils.isEmpty(data.positiveButtonText)) {
            if (data.positiveButtonTextResId == 0) {
                return null;
            }
            return getString(data.positiveButtonTextResId);
        }
        return data.positiveButtonText;
    }

    private String getNegativeButtonText() {
        if (TextUtils.isEmpty(data.negativeButtonText)) {
            if (data.negativeButtonTextResId == 0) {
                return null;
            }
            return getString(data.negativeButtonTextResId);
        }
        return data.negativeButtonText;
    }

    public void setData(AppRatingDialog.Builder.Data data) {
        this.data = data;
    }
}

package com.stepstone.apprating.listener;

/**
 * @author Piotr Głębocki (Piotr.Glebocki@stepstone.com) on 08/05/2017.
 */

public interface OnRatingBarChangedListener {

    void onRatingChanged(int rating);

    OnRatingBarChangedListener NULL = new OnRatingBarChangedListener() {

        @Override
        public void onRatingChanged(int rating) {

        }
    };
}

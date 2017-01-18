package com.stepstone.apprating.common;

import android.support.annotation.Nullable;

/**
 * @author Piotr Głębocki (Piotr.Glebocki@stepstone.com) on 06/01/2017.
 */
public class Preconditions {

    /**
     * Verifies if given expression it correct.
     * It throws {@link IllegalArgumentException} it expression is false.
     *
     * @param expression an expression to be checked
     * @param errorMessage error message which will be thrown with exception
     */
    public static void checkArgument(boolean expression, @Nullable Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    /**
     * Verifies if given object reference is not null.
     * It throws {@link NullPointerException} if object is null.
     *
     * @param obj object reference to che checked
     * @param errorMessage error message which will be thrown with exception
     * @param <T> type of object
     * @return object reference which was passed to method
     */
    public static <T> T checkNotNull(T obj, @Nullable Object errorMessage) {
        if (obj == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return obj;
    }
}

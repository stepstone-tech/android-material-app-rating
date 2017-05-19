package com.stepstone.apprating.common

/**
 * @author Piotr Głębocki (Piotr.Glebocki@stepstone.com) on 06/01/2017.
 */
object Preconditions {

    /**
     * Verifies if given expression it correct.
     * It throws [IllegalArgumentException] it expression is false.

     * @param expression an expression to be checked
     * *
     * @param errorMessage error message which will be thrown with exception
     */
    fun checkArgument(expression: Boolean, errorMessage: Any?) {
        if (!expression) {
            throw IllegalArgumentException(errorMessage.toString())
        }
    }

    /**
     * Verifies if given object reference is not null.
     * It throws [NullPointerException] if object is null.

     * @param obj object reference to che checked
     * *
     * @param errorMessage error message which will be thrown with exception
     * *
     * @param <T> type of object
     * *
     * @return object reference which was passed to method
    </T> */
    fun <T> checkNotNull(obj: T?, errorMessage: Any?): T {
        if (obj == null) {
            throw NullPointerException(errorMessage.toString())
        }
        return obj
    }
}

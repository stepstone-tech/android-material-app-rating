package com.stepstone.apprating.common

/*
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.*

/**
 * Simple static methods to be called at the start of your own methods to verify
 * correct arguments and state. This allows constructs such as
 * <pre>
 * if (count <= 0) {
 * throw new IllegalArgumentException("must be positive: " + count);
 * }</pre>

 * to be replaced with the more compact
 * <pre>
 * checkArgument(count > 0, "must be positive: %s", count);</pre>

 * Note that the sense of the expression is inverted; with `Preconditions`
 * you declare what you expect to be *true*, just as you do with an
 * [
   * `assert`](http://java.sun.com/j2se/1.5.0/docs/guide/language/assert.html) or a JUnit `assertTrue` call.

 *
 * **Warning:** only the `"%s"` specifier is recognized as a
 * placeholder in these messages, not the full range of [ ][String.format] specifiers.

 *
 * Take care not to confuse precondition checking with other similar types
 * of checks! Precondition exceptions -- including those provided here, but also
 * [IndexOutOfBoundsException], [NoSuchElementException], [ ] and others -- are used to signal that the
 * *calling method* has made an error. This tells the caller that it should
 * not have invoked the method when it did, with the arguments it did, or
 * perhaps ever. Postcondition or other invariant failures should not throw
 * these types of exceptions.

 * @author Kevin Bourrillion
 */
object Preconditions {

    /**
     * Ensures the truth of an expression involving one or more parameters to the
     * calling method.

     * @param expression a boolean expression
     * *
     * @throws IllegalArgumentException if `expression` is false
     */
    fun checkArgument(expression: Boolean) {
        if (!expression) {
            throw IllegalArgumentException()
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the
     * calling method.

     * @param expression a boolean expression
     * *
     * @param errorMessage the exception message to use if the check fails; will
     * *     be converted to a string using [String.valueOf]
     * *
     * @throws IllegalArgumentException if `expression` is false
     */
    fun checkArgument(expression: Boolean, errorMessage: Any) {
        if (!expression) {
            throw IllegalArgumentException(errorMessage.toString())
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the
     * calling method.

     * @param expression a boolean expression
     * *
     * @param errorMessageTemplate a template for the exception message should the
     * *     check fail. The message is formed by replacing each `%s`
     * *     placeholder in the template with an argument. These are matched by
     * *     position - the first `%s` gets `errorMessageArgs[0]`, etc.
     * *     Unmatched arguments will be appended to the formatted message in square
     * *     braces. Unmatched placeholders will be left as-is.
     * *
     * @param errorMessageArgs the arguments to be substituted into the message
     * *     template. Arguments are converted to strings using
     * *     [String.valueOf].
     * *
     * @throws IllegalArgumentException if `expression` is false
     * *
     * @throws NullPointerException if the check fails and either `errorMessageTemplate` or `errorMessageArgs` is null (don't let
     * *     this happen)
     */
    fun checkArgument(expression: Boolean,
                      errorMessageTemplate: String, vararg errorMessageArgs: Any) {
        if (!expression) {
            throw IllegalArgumentException(
                    format(errorMessageTemplate, *errorMessageArgs))
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.

     * @param expression a boolean expression
     * *
     * @throws IllegalStateException if `expression` is false
     */
    fun checkState(expression: Boolean) {
        if (!expression) {
            throw IllegalStateException()
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.

     * @param expression a boolean expression
     * *
     * @param errorMessage the exception message to use if the check fails; will
     * *     be converted to a string using [String.valueOf]
     * *
     * @throws IllegalStateException if `expression` is false
     */
    fun checkState(expression: Boolean, errorMessage: Any) {
        if (!expression) {
            throw IllegalStateException(errorMessage.toString())
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.

     * @param expression a boolean expression
     * *
     * @param errorMessageTemplate a template for the exception message should the
     * *     check fail. The message is formed by replacing each `%s`
     * *     placeholder in the template with an argument. These are matched by
     * *     position - the first `%s` gets `errorMessageArgs[0]`, etc.
     * *     Unmatched arguments will be appended to the formatted message in square
     * *     braces. Unmatched placeholders will be left as-is.
     * *
     * @param errorMessageArgs the arguments to be substituted into the message
     * *     template. Arguments are converted to strings using
     * *     [String.valueOf].
     * *
     * @throws IllegalStateException if `expression` is false
     * *
     * @throws NullPointerException if the check fails and either `errorMessageTemplate` or `errorMessageArgs` is null (don't let
     * *     this happen)
     */
    fun checkState(expression: Boolean,
                   errorMessageTemplate: String, vararg errorMessageArgs: Any) {
        if (!expression) {
            throw IllegalStateException(
                    format(errorMessageTemplate, *errorMessageArgs))
        }
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.

     * @param reference an object reference
     * *
     * @return the non-null reference that was validated
     * *
     * @throws NullPointerException if `reference` is null
     */
    fun <T> checkNotNull(reference: T?): T {
        if (reference == null) {
            throw NullPointerException()
        }
        return reference
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.

     * @param reference an object reference
     * *
     * @param errorMessage the exception message to use if the check fails; will
     * *     be converted to a string using [String.valueOf]
     * *
     * @return the non-null reference that was validated
     * *
     * @throws NullPointerException if `reference` is null
     */
    fun <T> checkNotNull(reference: T?, errorMessage: Any): T {
        if (reference == null) {
            throw NullPointerException(errorMessage.toString())
        }
        return reference
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.

     * @param reference an object reference
     * *
     * @param errorMessageTemplate a template for the exception message should the
     * *     check fail. The message is formed by replacing each `%s`
     * *     placeholder in the template with an argument. These are matched by
     * *     position - the first `%s` gets `errorMessageArgs[0]`, etc.
     * *     Unmatched arguments will be appended to the formatted message in square
     * *     braces. Unmatched placeholders will be left as-is.
     * *
     * @param errorMessageArgs the arguments to be substituted into the message
     * *     template. Arguments are converted to strings using
     * *     [String.valueOf].
     * *
     * @return the non-null reference that was validated
     * *
     * @throws NullPointerException if `reference` is null
     */
    fun <T> checkNotNull(reference: T?, errorMessageTemplate: String,
                         vararg errorMessageArgs: Any): T {
        if (reference == null) {
            // If either of these parameters is null, the right thing happens anyway
            throw NullPointerException(
                    format(errorMessageTemplate, *errorMessageArgs))
        }
        return reference
    }

    /**
     * Ensures that `index` specifies a valid *element* in an array,
     * list or string of size `size`. An element index may range from zero,
     * inclusive, to `size`, exclusive.

     * @param index a user-supplied index identifying an element of an array, list
     * *     or string
     * *
     * @param size the size of that array, list or string
     * *
     * @param desc the text to use to describe this index in an error message
     * *
     * @return the value of `index`
     * *
     * @throws IndexOutOfBoundsException if `index` is negative or is not
     * *     less than `size`
     * *
     * @throws IllegalArgumentException if `size` is negative
     */
    @JvmOverloads
    fun checkElementIndex(index: Int, size: Int, desc: String = "index"): Int {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException(badElementIndex(index, size, desc))
        }
        return index
    }

    private fun badElementIndex(index: Int, size: Int, desc: String): String {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, index)
        } else if (size < 0) {
            throw IllegalArgumentException("negative size: " + size)
        } else { // index >= size
            return format("%s (%s) must be less than size (%s)", desc, index, size)
        }
    }

    /**
     * Ensures that `index` specifies a valid *position* in an array,
     * list or string of size `size`. A position index may range from zero
     * to `size`, inclusive.

     * @param index a user-supplied index identifying a position in an array, list
     * *     or string
     * *
     * @param size the size of that array, list or string
     * *
     * @param desc the text to use to describe this index in an error message
     * *
     * @return the value of `index`
     * *
     * @throws IndexOutOfBoundsException if `index` is negative or is
     * *     greater than `size`
     * *
     * @throws IllegalArgumentException if `size` is negative
     */
    @JvmOverloads
    fun checkPositionIndex(index: Int, size: Int, desc: String = "index"): Int {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (index < 0 || index > size) {
            throw IndexOutOfBoundsException(badPositionIndex(index, size, desc))
        }
        return index
    }

    private fun badPositionIndex(index: Int, size: Int, desc: String): String {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, index)
        } else if (size < 0) {
            throw IllegalArgumentException("negative size: " + size)
        } else { // index > size
            return format("%s (%s) must not be greater than size (%s)",
                    desc, index, size)
        }
    }

    /**
     * Ensures that `start` and `end` specify a valid *positions*
     * in an array, list or string of size `size`, and are in order. A
     * position index may range from zero to `size`, inclusive.

     * @param start a user-supplied index identifying a starting position in an
     * *     array, list or string
     * *
     * @param end a user-supplied index identifying a ending position in an array,
     * *     list or string
     * *
     * @param size the size of that array, list or string
     * *
     * @throws IndexOutOfBoundsException if either index is negative or is
     * *     greater than `size`, or if `end` is less than `start`
     * *
     * @throws IllegalArgumentException if `size` is negative
     */
    fun checkPositionIndexes(start: Int, end: Int, size: Int) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (start < 0 || end < start || end > size) {
            throw IndexOutOfBoundsException(badPositionIndexes(start, end, size))
        }
    }

    private fun badPositionIndexes(start: Int, end: Int, size: Int): String {
        if (start < 0 || start > size) {
            return badPositionIndex(start, size, "start index")
        }
        if (end < 0 || end > size) {
            return badPositionIndex(end, size, "end index")
        }
        // end < start
        return format("end index (%s) must not be less than start index (%s)",
                end, start)
    }

    /**
     * Substitutes each `%s` in `template` with an argument. These
     * are matched by position - the first `%s` gets `args[0]`, etc.
     * If there are more arguments than placeholders, the unmatched arguments will
     * be appended to the end of the formatted message in square braces.

     * @param template a non-null string containing 0 or more `%s`
     * *     placeholders.
     * *
     * @param args the arguments to be substituted into the message
     * *     template. Arguments are converted to strings using
     * *     [String.valueOf]. Arguments can be null.
     */
    internal fun format(template: String, vararg args: Any): String {
        // start substituting the arguments into the '%s' placeholders
        val builder = StringBuilder(
                template.length + 16 * args.size)
        var templateStart = 0
        var i = 0
        while (i < args.size) {
            val placeholderStart = template.indexOf("%s", templateStart)
            if (placeholderStart == -1) {
                break
            }
            builder.append(template.substring(templateStart, placeholderStart))
            builder.append(args[i++])
            templateStart = placeholderStart + 2
        }
        builder.append(template.substring(templateStart))

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.size) {
            builder.append(" [")
            builder.append(args[i++])
            while (i < args.size) {
                builder.append(", ")
                builder.append(args[i++])
            }
            builder.append("]")
        }

        return builder.toString()
    }
}
/**
 * Ensures that `index` specifies a valid *element* in an array,
 * list or string of size `size`. An element index may range from zero,
 * inclusive, to `size`, exclusive.

 * @param index a user-supplied index identifying an element of an array, list
 * *     or string
 * *
 * @param size the size of that array, list or string
 * *
 * @return the value of `index`
 * *
 * @throws IndexOutOfBoundsException if `index` is negative or is not
 * *     less than `size`
 * *
 * @throws IllegalArgumentException if `size` is negative
 */
/**
 * Ensures that `index` specifies a valid *position* in an array,
 * list or string of size `size`. A position index may range from zero
 * to `size`, inclusive.

 * @param index a user-supplied index identifying a position in an array, list
 * *     or string
 * *
 * @param size the size of that array, list or string
 * *
 * @return the value of `index`
 * *
 * @throws IndexOutOfBoundsException if `index` is negative or is
 * *     greater than `size`
 * *
 * @throws IllegalArgumentException if `size` is negative
 */

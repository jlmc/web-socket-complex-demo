package org.connect4.utils.functions;

import java.util.NoSuchElementException;

/**
 * Simple static methods to be called at the start of your own methods to verify
 * correct arguments and state. This allows constructs such as
 *
 * <pre>
 * if (count &lt;= 0) {
 *     throw new IllegalArgumentException(&quot;must be positive: &quot; + count);
 * }
 * </pre>
 *
 * to be replaced with the more compact
 *
 * <pre>
 * checkArgument(count &gt; 0, &quot;must be positive: %s&quot;, count);
 * </pre>
 *
 * Note that the sense of the expression is inverted; with {@code Preconditions} you declare what you expect to be
 * <i>true</i>, just as you do with an
 * <a href="http://java.sun.com/j2se/1.5.0/docs/guide/language/assert.html"> {@code assert}</a> or a JUnit
 * {@code assertTrue} call.
 * <p>
 * <b>Warning:</b> only the {@code "%s"} specifier is recognized as a placeholder in these messages, not the full range
 * of {@link String#format(String, Object[])} specifiers.
 * <p>
 * Take care not to confuse precondition checking with other similar types of checks! Precondition exceptions --
 * including those provided here, but also {@link IndexOutOfBoundsException}, {@link NoSuchElementException},
 * {@link UnsupportedOperationException} and others -- are used to signal that the <i>calling method</i> has made an
 * error. This tells the caller that it should not have invoked the method when it did, with the arguments it did, or
 * perhaps ever. Postcondition or other invariant failures should not throw these types of exceptions.
 * <p>
 * See the Guava User Guide on <a href= "http://code.google.com/p/guava-libraries/wiki/PreconditionsExplained"> using
 * {@code Preconditions}</a>.
 * @author Kevin Bourrillion
 * @author Joao Costa
 * @since 2.0 (imported from Google Collections Library)
 */
public final class Preconditions {

    /**
     * The Constructor.
     */
    private Preconditions() {
        // nothing to do
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the
     * calling method.
     * @param expression
     *            a boolean expression
     */
    public static void checkArgument(final boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(final boolean expression, final IllegalArgumentException e) {
        if (!expression) {
            throw e;
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the
     * calling method.
     * @param expression
     *            a boolean expression
     * @param errorMessage
     *            the exception message to use if the check fails; will
     *            be converted to a string using {@link String#valueOf(Object)}
     */
    public static void checkArgument(final boolean expression, final Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the
     * calling method.
     * @param expression
     *            a boolean expression
     * @param errorMessageTemplate
     *            a template for the exception message should the
     *            check fail. The message is formed by replacing each {@code %s} placeholder in the template with an
     *            argument. These are matched by
     *            position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
     *            Unmatched arguments will be appended to the formatted message in square
     *            braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs
     *            the arguments to be substituted into the message
     *            template. Arguments are converted to strings using {@link String#valueOf(Object)}.
     */
    public static void checkArgument(final boolean expression, final String errorMessageTemplate,
            final Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     * @param expression
     *            a boolean expression
     */
    public static void checkState(final boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     * @param expression
     *            a boolean expression
     * @param errorMessage
     *            the exception message to use if the check fails; will
     *            be converted to a string using {@link String#valueOf(Object)}
     */
    public static void checkState(final boolean expression, final Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     * @param expression
     *            a boolean expression
     * @param errorMessageTemplate
     *            a template for the exception message should the
     *            check fail. The message is formed by replacing each {@code %s} placeholder in the template with an
     *            argument. These are matched by
     *            position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
     *            Unmatched arguments will be appended to the formatted message in square
     *            braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs
     *            the arguments to be substituted into the message
     *            template. Arguments are converted to strings using {@link String#valueOf(Object)}.
     */
    public static void checkState(final boolean expression, final String errorMessageTemplate,
            final Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     * @param <T>
     *            the generic type
     * @param reference
     *            an object reference
     * @return the non-null reference that was validated
     */
    public static <T> T checkNotNull(final T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     * @param <T>
     *            the generic type
     * @param reference
     *            an object reference
     * @param errorMessage
     *            the exception message to use if the check fails; will
     *            be converted to a string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     */
    public static <T> T checkNotNull(final T reference, final Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     * @param <T>
     *            the generic type
     * @param reference
     *            an object reference
     * @param errorMessageTemplate
     *            a template for the exception message should the
     *            check fail. The message is formed by replacing each {@code %s} placeholder in the template with an
     *            argument. These are matched by
     *            position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
     *            Unmatched arguments will be appended to the formatted message in square
     *            braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs
     *            the arguments to be substituted into the message
     *            template. Arguments are converted to strings using {@link String#valueOf(Object)}.
     * @return the non-null reference that was validated
     */
    public static <T> T checkNotNull(final T reference, final String errorMessageTemplate,
            final Object... errorMessageArgs) {
        if (reference == null) {
            // If either of these parameters is null, the right thing happens anyway
            throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
        }
        return reference;
    }

    /*
     * All recent hotspots (as of 2009) *really* like to have the natural code
     * if (guardExpression) {
     * throw new BadException(messageExpression);
     * }
     * refactored so that messageExpression is moved to a separate
     * String-returning method.
     * if (guardExpression) {
     * throw new BadException(badMsg(...));
     * }
     * The alternative natural refactorings into void or Exception-returning
     * methods are much slower. This is a big deal - we're talking factors of
     * 2-8 in microbenchmarks, not just 10-20%. (This is a hotspot optimizer
     * bug, which should be fixed, but that's a separate, big project).
     * The coding pattern above is heavily used in java.util, e.g. in ArrayList.
     * There is a RangeCheckMicroBenchmark in the JDK that was used to test this.
     * But the methods in this class want to throw different exceptions,
     * depending on the args, so it appears that this pattern is not directly
     * applicable. But we can use the ridiculous, devious trick of throwing an
     * exception in the middle of the construction of another exception.
     * Hotspot is fine with that.
     */

    /**
     * Ensures that {@code index} specifies a valid <i>element</i> in an array,
     * list or string of size {@code size}. An element index may range from zero,
     * inclusive, to {@code size}, exclusive.
     * @param index
     *            a user-supplied index identifying an element of an array, list
     *            or string
     * @param size
     *            the size of that array, list or string
     * @return the value of {@code index}
     */
    public static int checkElementIndex(final int index, final int size) {
        return checkElementIndex(index, size, "index");
    }

    /**
     * Ensures that {@code index} specifies a valid <i>element</i> in an array,
     * list or string of size {@code size}. An element index may range from zero,
     * inclusive, to {@code size}, exclusive.
     * @param index
     *            a user-supplied index identifying an element of an array, list
     *            or string
     * @param size
     *            the size of that array, list or string
     * @param desc
     *            the text to use to describe this index in an error message
     * @return the value of {@code index}
     */
    public static int checkElementIndex(final int index, final int size, final String desc) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
        }
        return index;
    }

    /**
     * Bad element index.
     * @param index
     *            the index
     * @param size
     *            the size
     * @param desc
     *            the desc
     * @return the string
     */
    private static String badElementIndex(final int index, final int size, final String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, Integer.valueOf(index));
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else { // index >= size
            return format("%s (%s) must be less than size (%s)", desc, Integer.valueOf(index), Integer.valueOf(size));
        }
    }

    /**
     * Ensures that {@code index} specifies a valid <i>position</i> in an array,
     * list or string of size {@code size}. A position index may range from zero
     * to {@code size}, inclusive.
     * @param index
     *            a user-supplied index identifying a position in an array, list
     *            or string
     * @param size
     *            the size of that array, list or string
     * @return the value of {@code index}
     */
    public static int checkPositionIndex(final int index, final int size) {
        return checkPositionIndex(index, size, "index");
    }

    /**
     * Ensures that {@code index} specifies a valid <i>position</i> in an array,
     * list or string of size {@code size}. A position index may range from zero
     * to {@code size}, inclusive.
     * @param index
     *            a user-supplied index identifying a position in an array, list
     *            or string
     * @param size
     *            the size of that array, list or string
     * @param desc
     *            the text to use to describe this index in an error message
     * @return the value of {@code index}
     */
    public static int checkPositionIndex(final int index, final int size, final String desc) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(badPositionIndex(index, size, desc));
        }
        return index;
    }

    /**
     * Bad position index.
     * @param index
     *            the index
     * @param size
     *            the size
     * @param desc
     *            the desc
     * @return the string
     */
    private static String badPositionIndex(final int index, final int size, final String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, Integer.valueOf(index));
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else { // index > size
            return format("%s (%s) must not be greater than size (%s)", desc, Integer.valueOf(index),
                    Integer.valueOf(size));
        }
    }

    /**
     * Ensures that {@code start} and {@code end} specify a valid <i>positions</i>
     * in an array, list or string of size {@code size}, and are in order. A
     * position index may range from zero to {@code size}, inclusive.
     * @param start
     *            a user-supplied index identifying a starting position in an
     *            array, list or string
     * @param end
     *            a user-supplied index identifying a ending position in an array,
     *            list or string
     * @param size
     *            the size of that array, list or string
     */
    public static void checkPositionIndexes(final int start, final int end, final int size) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (start < 0 || end < start || end > size) {
            throw new IndexOutOfBoundsException(badPositionIndexes(start, end, size));
        }
    }

    /**
     * Bad position indexes.
     * @param start
     *            the start
     * @param end
     *            the end
     * @param size
     *            the size
     * @return the string
     */
    private static String badPositionIndexes(final int start, final int end, final int size) {
        if (start < 0 || start > size) {
            return badPositionIndex(start, size, "start index");
        }
        if (end < 0 || end > size) {
            return badPositionIndex(end, size, "end index");
        }
        // end < start
        return format("end index (%s) must not be less than start index (%s)", Integer.valueOf(end),
                Integer.valueOf(start));
    }

    /**
     * Substitutes each {@code %s} in {@code template} with an argument. These
     * are matched by position - the first {@code %s} gets {@code args[0]}, etc.
     * If there are more arguments than placeholders, the unmatched arguments will
     * be appended to the end of the formatted message in square braces.
     * @param template
     *            a non-null string containing 0 or more {@code %s} placeholders.
     * @param args
     *            the arguments to be substituted into the message
     *            template. Arguments are converted to strings using {@link String#valueOf(Object)}. Arguments can be
     *            null.
     * @return the string
     */
    static String format(final String template, final Object... args) {
        final String temp = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        final StringBuilder builder = new StringBuilder(temp.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            final int placeholderStart = temp.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(temp.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(temp.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }
}

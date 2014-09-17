package org.connect4.utils.functions;

/**
 * Determines a true or false value for a given input.
 * <p>
 * See the Guava User Guide article on <a href= "http://code.google.com/p/guava-libraries/wiki/FunctionalExplained">the use of {@code Predicate}</a>.
 * @author Joao Costa
 * @param <T>
 *            the generic type
 * @since 2.0 (imported from Google Collections Library)
 */
public interface Predicate<T> {

    /**
     * Returns the result of applying this predicate to {@code input}. This method is <i>generally
     * expected</i>, but not absolutely required, to have the following properties:
     * <ul>
     * <li>Its execution does not cause any observable side effects.
     * <li>The computation is <i>consistent with equals</i>; that is, Objects.equal {@code (a, b)} implies that {@code predicate.apply(a) == predicate.apply(b))}.
     * </ul>
     * @param input
     *            the input
     * @return true, if apply
     */
    boolean apply(T input);

    /**
     * Indicates whether another object is equal to this predicate.
     * <p>
     * Most implementations will have no reason to override the behavior of {@link Object#equals}. However, an implementation may also choose to return {@code true} whenever {@code object} is a {@link Predicate} that it considers <i>interchangeable</i> with this one. "Interchangeable" <i>typically</i> means that {@code this.apply(t) == that.apply(t)} for all {@code t} of type {@code T}). Note that a {@code false} result from this method does not imply that the predicates are known <i>not</i> to be interchangeable.
     * @param object
     *            the object
     * @return true, if equals
     */
    @Override
    boolean equals(Object object);
}

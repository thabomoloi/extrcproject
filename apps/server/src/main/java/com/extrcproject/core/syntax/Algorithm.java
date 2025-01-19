package com.extrcproject.core.syntax;

/**
 * Represents list of entailment algorithms.
 *
 * @author Thabo Vincent Moloi.
 */
public class Algorithm {

    private final Name name;
    private final Type type;

    /**
     * Create new algorithm with no name and type;
     */
    public Algorithm() {
        this(Name.NO_NAME, Type.NO_TYPE);
    }

    /**
     * Create new entailment algorithm.
     *
     * @param name Name of the algorithm.
     * @param type Type of the algorithm.
     */
    public Algorithm(Name name, Type type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Create copy of entailment algorithm.
     *
     * @param other
     */
    public Algorithm(Algorithm other) {
        this.name = other.name;
        this.type = other.type;
    }

    /**
     * Get algorithm name.
     *
     * @return Algorithm name.
     */
    public Name getName() {
        return name;
    }

    /**
     * Get algorithm type.
     *
     * @return Algorithm type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Represents name of entailment algorithm.
     */
    public enum Name {
        RATIONAL_CLOSURE,
        LEXICOGRAPHIC_CLOSURE,
        NO_NAME
    }

    /**
     * Represents type of entailment algorithm.
     */
    public enum Type {
        NAIVE,
        CACHED_NAIVE,
        BINARY,
        CACHED_BINARY,
        TERNARY,
        CACHED_TERNARY,
        NO_TYPE
    }

}

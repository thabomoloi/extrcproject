package com.extrcproject.core.util;

/**
 * Defines symbols for propositional logic.
 *
 * @author Thabo Vincent Moloi
 */
public class Symbol {

    /**
     * Gets symbol for implication (if then).
     *
     * @return Symbol for implication.
     */
    public static String IMPLICATION() {
        return "=>";
    }

    /**
     * Gets symbol for disjuntion (OR).
     *
     * @return Symbol for disjunction.
     */
    public static String DISJUNCTION() {
        return "||";
    }

    /**
     * Gets symbol for conjunction (AND).
     *
     * @return Symbol for conjunction.
     */
    public static String CONJUNCTION() {
        return "&&";
    }

    /**
     * Gets symbol for equivalence (if and only if).
     *
     * @return Symbol for equivalence.
     */
    public static String EQUIVALENCE() {
        return "<=>";
    }

    /**
     * Gets symbol for negation.
     *
     * @return Symbol for negation.
     */
    public static String NEGATION() {
        return "!";
    }

    /**
     * Gets symbol for defeasible implication.
     *
     * @return Symbol for defeasible implication.
     */
    public static String DEFEASIBLE_IMPLICATION() {
        return "~>";
    }
}

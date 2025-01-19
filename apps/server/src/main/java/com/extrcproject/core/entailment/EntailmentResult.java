package com.extrcproject.core.entailment;

import com.extrcproject.core.syntax.Ranking;

/**
 * Represents results from entailment checking algorithm.
 *
 * @author Thabo Vincent Moloi
 */
public abstract class EntailmentResult {

    protected final boolean entailed;
    protected final Ranking removedRanking;
    protected Algorithm algorithm;

    private long timeTaken;

    /**
     * Create new entailment result.
     *
     * @param entailed Result (boolean) from entailment checking.
     * @param removedRanking Removed ranks.
     */
    public EntailmentResult(boolean entailed, Ranking removedRanking) {
        this.entailed = entailed;
        this.removedRanking = removedRanking;
        this.timeTaken = 0;
        this.algorithm = new Algorithm();
    }

    /**
     * Create a copy of entailment result
     *
     * @param entailmentResult Entailment result to copy.
     */
    public EntailmentResult(EntailmentResult entailmentResult) {
        this(entailmentResult.entailed, new Ranking(entailmentResult.removedRanking));
        this.timeTaken = entailmentResult.timeTaken;
        this.algorithm = entailmentResult.algorithm;
    }

    /**
     * Get the result (boolean) from entailment checking.
     *
     * @return true if the formula is entailed, otherwise false.
     */
    public boolean getEntailed() {
        return entailed;
    }

    /**
     * Get removed ranking.
     *
     * @return Removed ranks.
     */
    public Ranking getRemovedRanking() {
        return removedRanking;
    }

    /**
     * Get time taken for the algorithm to complete.
     *
     * @return Time take check entailement.
     */
    public long getTimeTaken() {
        return timeTaken;
    }

    /**
     * Set time taken to check entailment.
     *
     * @param timeTaken time taken to check entailment.
     */
    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    /**
     * Get entailment algorithm.
     *
     * @return Entailment algorithm.
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }
}

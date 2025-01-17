package com.extrcproject.core.entailment.rc;

import com.extrcproject.core.entailment.Algorithm;
import com.extrcproject.core.entailment.EntailmentResult;
import com.extrcproject.core.syntax.Ranking;

/**
 * Represents entailment result from Rational Closure algorithm.
 */
public class RCEntailmentResult extends EntailmentResult {

    /**
     * Create a new rational closure entailment result.
     *
     * @param algorithmType Type of rational closure algorithm.
     * @param entailed Result (boolean) from entailment checking.
     * @param finalRanking The final ranks.
     * @param removedRanking The removed ranks.
     */
    public RCEntailmentResult(Algorithm.Type algorithmType, boolean entailed, Ranking finalRanking, Ranking removedRanking) {
        super(entailed, finalRanking, removedRanking);
        this.algorithm = new Algorithm(Algorithm.Name.RATIONAL_CLOSURE, algorithmType);
    }

}

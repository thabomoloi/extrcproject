package com.extrcproject.core.entailment;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.syntax.PlFormula;

import com.extrcproject.core.syntax.Ranking;

/**
 * Represents base reasoner for all algorithms.
 *
 * @author Thabo Vincent Moloi
 */
public abstract class Reasoner {

    protected final SatReasoner reasoner;
    protected final Algorithm algorithm;

    /**
     * Create a new reasoner.
     *
     * @param algorithm Entailment algorithm.
     * @param reasoner SAT Reasoner.
     */
    public Reasoner(Algorithm algorithm, SatReasoner reasoner) {
        this.reasoner = reasoner;
        this.algorithm = algorithm;
    }

    /**
     * Check if the formula is entailed.
     *
     * @return Results from entailment checking.
     */
    public abstract EntailmentResult query(PlFormula formula, Ranking ranking);
}

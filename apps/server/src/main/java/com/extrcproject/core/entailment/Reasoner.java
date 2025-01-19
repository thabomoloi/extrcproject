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

    /**
     * Create a new reasoner.
     *
     * @param reasoner SAT Reasoner.
     */
    public Reasoner(SatReasoner reasoner) {
        this.reasoner = reasoner;
    }

    /**
     * Check if the formula is entailed.
     *
     * @return Results from entailment checking.
     */
    public abstract EntailmentResult query(PlFormula formula, Ranking ranking);
}

package com.extrcproject.core.entailment;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;

import com.extrcproject.core.syntax.Algorithm;
import com.extrcproject.core.syntax.DefeasibleImplication;
import com.extrcproject.core.syntax.EntailmentResult;
import com.extrcproject.core.syntax.Ranking;

/**
 * Represents base reasoner for all algorithms.
 *
 * @author Thabo Vincent Moloi
 */
public abstract class Reasoner {

    protected final SatReasoner reasoner;
    protected Algorithm.Type algorithmType;

    /**
     * Create a new reasoner.
     *
     * @param reasoner SAT Reasoner.
     */
    public Reasoner(SatReasoner reasoner) {
        this.reasoner = reasoner;
        this.algorithmType = Algorithm.Type.NO_TYPE;
    }

    /**
     * Check if the formula is entailed.
     *
     * @return Results from entailment checking.
     */
    public abstract EntailmentResult query(DefeasibleImplication formula, Ranking ranking);
}

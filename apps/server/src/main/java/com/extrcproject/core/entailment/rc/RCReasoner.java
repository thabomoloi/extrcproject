package com.extrcproject.core.entailment.rc;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;

import com.extrcproject.core.entailment.Algorithm;
import com.extrcproject.core.entailment.Reasoner;

/**
 * Represents base reasoner for all algorithms using Rational Clousure.
 */
public abstract class RCReasoner extends Reasoner {

    /**
     * Create new Rational closure reasoner.
     *
     * @param algorithmType Type of rational closure.
     * @param reasoner SAT Reasoner.
     */
    public RCReasoner(Algorithm.Type algorithmType, SatReasoner reasoner) {
        super(new Algorithm(Algorithm.Name.RATIONAL_CLOSURE, algorithmType), reasoner);
    }
}

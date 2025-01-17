package com.extrcproject.core.entailment.rc;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.syntax.PlFormula;

import com.extrcproject.core.entailment.EntailmentResult;
import com.extrcproject.core.entailment.Reasoner;
import com.extrcproject.core.syntax.Ranking;

/**
 * Represents base reasoner for all algorithms using Rational Clousure.
 */
public class NaiveRCReasoner extends Reasoner {

    /**
     * Create new Rational closure reasoner.
     *
     * @param reasoner SAT Reasoner.
     */
    public NaiveRCReasoner(SatReasoner reasoner) {
        super(reasoner);
    }

    @Override
    public EntailmentResult query(PlFormula formula, Ranking ranking) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'query'");
    }
}

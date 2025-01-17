package com.extrcproject.core.entailment.rc;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.syntax.PlFormula;

import com.extrcproject.core.entailment.Algorithm;
import com.extrcproject.core.entailment.EntailmentResult;
import com.extrcproject.core.syntax.Ranking;

/**
 * Represents a reasoner using naive rational closure algorithm.
 *
 * @author Thabo Vincent Moloi
 */
public class NaiveRCReasoner extends RCReasoner {

    /**
     * Create new Rational closure reasoner.
     *
     * @param reasoner SAT Reasoner.
     */
    public NaiveRCReasoner(SatReasoner reasoner) {
        super(Algorithm.Type.NAIVE, reasoner);
    }

    @Override
    public EntailmentResult query(PlFormula formula, Ranking ranking) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'query'");
    }
}

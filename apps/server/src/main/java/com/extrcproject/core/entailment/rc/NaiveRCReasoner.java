package com.extrcproject.core.entailment.rc;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.syntax.Implication;
import org.tweetyproject.logics.pl.syntax.Negation;
import org.tweetyproject.logics.pl.syntax.PlFormula;

import com.extrcproject.core.entailment.Algorithm;
import com.extrcproject.core.entailment.EntailmentResult;
import com.extrcproject.core.entailment.Reasoner;
import com.extrcproject.core.syntax.KnowledgeBase;
import com.extrcproject.core.syntax.Ranking;

/**
 * Represents a reasoner using naive rational closure algorithm.
 *
 * @author Thabo Vincent Moloi
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
        // Negation of antecedent
        PlFormula negation = new Negation(((Implication) formula).getFirstFormula());
        // Rankings
        Ranking removedRanking = new Ranking();
        // Combine all formulas
        KnowledgeBase union = ranking.unionAll();

        int i = 0;
        while (!union.isEmpty() && reasoner.query(union, negation) && i < ranking.size() - 1) {
            // Remove exceptional formulas
            removedRanking.add(ranking.get(i));
            union.removeAll(ranking.get(i));
            i++;
        }

        // Check entailment
        boolean entailed = !union.isEmpty() && reasoner.query(union, formula);

        return new RCEntailmentResult(Algorithm.Type.NAIVE, entailed, removedRanking);

    }
}

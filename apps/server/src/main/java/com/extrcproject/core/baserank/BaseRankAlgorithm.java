package com.extrcproject.core.baserank;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.syntax.Implication;

import com.extrcproject.core.syntax.BaseRankResult;
import com.extrcproject.core.syntax.KnowledgeBase;
import com.extrcproject.core.syntax.Rank;
import com.extrcproject.core.syntax.Ranking;

/**
 * Abstract class for computing rankings using base rank algorithm.
 *
 * @author Thabo Vincent Moloi
 */
public abstract class BaseRankAlgorithm implements BaseRank {

    protected final SatReasoner reasoner;

    /**
     * Create a base rank algorithm object.
     *
     * @param reasoner SAT Reasoner
     */
    public BaseRankAlgorithm(SatReasoner reasoner) {
        this.reasoner = reasoner;
    }

    @Override
    public BaseRankResult computeBaseRank(KnowledgeBase knowledgeBase) {
        // Separate classical and defeasible statements
        KnowledgeBase[] kb = knowledgeBase.separate();
        KnowledgeBase defeasible = kb[0];
        KnowledgeBase classical = kb[1];

        // Empty ranking and exceptionality sequence
        Ranking ranking = new Ranking();
        Ranking sequence = new Ranking();

        // Current and previous exceptionality sequences
        KnowledgeBase current = new KnowledgeBase(defeasible);
        KnowledgeBase previous = new KnowledgeBase();

        // Variables to use inside the loop
        int i = 0;
        KnowledgeBase exceptionals;
        Rank rank;

        while (!previous.equals(current)) {
            // Update previous and current
            previous = current;
            current = new KnowledgeBase();

            // Get exceptional formulas
            exceptionals = getExceptionals(previous, classical);

            // Construct current rank
            rank = constructRank(previous, current, exceptionals, i);

            // Add rank to ranking if the rank is not empty
            if (!rank.isEmpty()) {
                ranking.add(rank);
            }

            // Add element to exceptionality sequence
            sequence.addRank(previous.equals(current) ? Integer.MAX_VALUE : i, previous);

            // Increment i;
            i++;
        }
        ranking.addRank(Integer.MAX_VALUE, classical.union(current));

        return new BaseRankResult(ranking, sequence);
    }

    /**
     * Get exceptional statements given defeasible and classical statements.
     *
     * @param defeasible Knowledge base containing defeasible statements
     * @param classical Knowledge base containing classical statements
     * @return Knowledge base containing exceptional statements.
     */
    protected abstract KnowledgeBase getExceptionals(KnowledgeBase defeasible, KnowledgeBase classical);

    /**
     * Construct rank with statemets that are not exceptionals.
     *
     * @param previous Previous exceptionality sequence element.
     * @param current Current exceptionality sequence element.
     * @param exceptionals tional statements
     * @return Rank of non-exceptional statements
     */
    protected Rank constructRank(KnowledgeBase previous, KnowledgeBase current, KnowledgeBase exceptionals, int rankNumber) {
        Rank rank = new Rank();
        rank.setRankNumber(rankNumber);
        previous.forEach((formula) -> {
            if (exceptionals.contains(((Implication) formula).getFirstFormula())) {
                current.add(formula);
            } else {
                rank.add(formula);
            }
        });
        return rank;
    }
}

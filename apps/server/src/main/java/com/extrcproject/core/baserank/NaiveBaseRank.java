package com.extrcproject.core.baserank;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.syntax.Implication;
import org.tweetyproject.logics.pl.syntax.Negation;

import com.extrcproject.core.syntax.KnowledgeBase;
import com.extrcproject.core.syntax.Rank;
import com.extrcproject.core.syntax.Ranking;

/**
 * Defines methods for computing ranking using the naive base rank algorithm.
 *
 * @author Thabo Vincent Moloi
 */
public class NaiveBaseRank implements BaseRank {

    private final SatReasoner reasoner;

    /**
     * Create naive base rank algorithm.
     *
     * @param reasoner SAT Reasoner
     */
    public NaiveBaseRank(SatReasoner reasoner) {
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

    private KnowledgeBase getExceptionals(KnowledgeBase defeasible, KnowledgeBase classical) {
        KnowledgeBase exceptionals = new KnowledgeBase();
        KnowledgeBase all = defeasible.union(classical);
        defeasible.antecedents().forEach((antecedent) -> {
            if (reasoner.query(all, new Negation(antecedent))) {
                exceptionals.add(antecedent);
            }
        });
        return exceptionals;
    }

    private Rank constructRank(KnowledgeBase previous, KnowledgeBase current, KnowledgeBase exceptionals, int rankNumber) {
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

package com.extrcproject.core.entailment.rc;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.syntax.Implication;
import org.tweetyproject.logics.pl.syntax.Negation;
import org.tweetyproject.logics.pl.syntax.PlFormula;

import com.extrcproject.core.syntax.Algorithm;
import com.extrcproject.core.syntax.DefeasibleImplication;
import com.extrcproject.core.syntax.EntailmentResult;
import com.extrcproject.core.syntax.KnowledgeBase;
import com.extrcproject.core.syntax.Ranking;

/**
 * Defines a reasoner using ternary search to determine the minimal rank
 * required to entail a defeasible implication using Rational Closure.
 *
 * @author Thabo Vincent Moloi
 */
public class TernaryRCReasoner extends RCReasoner {

    /**
     * Create new Rational closure reasoner using ternary search.
     *
     * @param reasoner
     */
    public TernaryRCReasoner(SatReasoner reasoner) {
        super(reasoner);
        algorithmType = Algorithm.Type.TERNARY;
    }

    @Override
    public EntailmentResult query(DefeasibleImplication formula, Ranking ranking) {
        // Final ranking
        Ranking finalRanking = ranking.subRanking(findLowestRank(formula, ranking), ranking.size());
        // Removed ranks
        Ranking removedRanking = (new Ranking(ranking));
        removedRanking.removeAll(finalRanking);

        boolean entailed = reasoner.query(finalRanking.unionAll(), formula);

        return new RCEntailmentResult(algorithmType, entailed, removedRanking);
    }

    private int findLowestRank(PlFormula formula, Ranking ranking) {
        int high = ranking.size() - 1;
        int low = 0;

        // Negation of antecedent
        PlFormula negation = new Negation(((Implication) formula).getFirstFormula());
        System.out.println("\n" + ranking);
        while (low <= high) {
            // Divide the range into three parts
            int mid1 = low + (high - low) / 3;
            int mid2 = high - (high - low) / 3;

            System.out.println(String.format("low = %s, mid1 = %s, mid2 = %s, high = %s", low, mid1, mid2, high));

            // Check if removing ranks from mid+1 to high results in consistency with the negated antecedent
            KnowledgeBase upperRankKbMid1 = ranking.subRanking(mid1, ranking.size()).unionAll();
            KnowledgeBase prevRankMid1 = mid1 > 0 ? ranking.get(mid1 - 1) : new KnowledgeBase();

            KnowledgeBase upperRankKbMid2 = ranking.subRanking(mid2, ranking.size()).unionAll();
            KnowledgeBase prevRankMid2 = mid2 > 0 ? ranking.get(mid2 - 1) : new KnowledgeBase();

            // Check if the negated antecedent is consistent
            boolean consistentMid1 = reasoner.query(upperRankKbMid1, negation);
            boolean consistentWithPrevRankMid1 = reasoner.query(upperRankKbMid1.union(prevRankMid1), negation);

            // Check if the negated antecedent is consistent
            boolean consistentMid2 = reasoner.query(upperRankKbMid2, negation);
            boolean consistentWithPrevRankMid2 = reasoner.query(upperRankKbMid1.union(prevRankMid2), negation);

            if (!consistentMid1 && consistentWithPrevRankMid1) {
                return mid1;
            } else if (!consistentMid2 && consistentWithPrevRankMid2) {
                return mid2;
            }

            if (consistentMid2) {
                low = mid2 + 1;
            } else if (consistentMid1) {
                low = mid1 + 1;
            } else {
                high = mid2 - 1;
            }

        }
        return 0;
    }
}

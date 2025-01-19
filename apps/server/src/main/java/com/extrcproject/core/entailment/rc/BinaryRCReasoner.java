package com.extrcproject.core.entailment.rc;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.syntax.Implication;
import org.tweetyproject.logics.pl.syntax.Negation;
import org.tweetyproject.logics.pl.syntax.PlFormula;

import com.extrcproject.core.syntax.Algorithm;
import com.extrcproject.core.syntax.DefeasibleImplication;
import com.extrcproject.core.syntax.KnowledgeBase;
import com.extrcproject.core.syntax.Ranking;

/**
 * Defines a reasoner using binary search to determine the minimal rank required
 * to entail a defeasible implication using Rational Closure.
 *
 * @author Thabo Vincent Moloi
 */
public class BinaryRCReasoner extends RCReasoner {

    /**
     * Create new Rational closure reasoner using Binary search.
     *
     * @param reasoner
     */
    public BinaryRCReasoner(SatReasoner reasoner) {
        super(reasoner);
        algorithmType = Algorithm.Type.BINARY;
    }

    @Override
    public RCEntailmentResult query(DefeasibleImplication formula, Ranking ranking) {
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

        while (low <= high) {
            int mid = low + (high - low) / 2;

            // Check if removing ranks from mid+1 to high results in consistency with the negated antecedent
            KnowledgeBase upperRankKb = ranking.subRanking(mid, ranking.size()).unionAll();
            KnowledgeBase prevRank = mid > 0 ? ranking.get(mid - 1) : new KnowledgeBase();

            // Check if the negated antecedent is consistent
            boolean consistent = reasoner.query(upperRankKb, negation);
            boolean consistentWithPrevRank = reasoner.query(upperRankKb.union(prevRank), negation);

            if (!consistent && consistentWithPrevRank) {
                return mid;
            } else if (consistent) {
                // Negated antecedent consistent: search upper half
                low = mid + 1;
            } else {
                // Negated antecedent consistent: search lower half
                high = mid - 1;
            }
        }
        return 0;
    }

}

package com.extrcproject.core.entailment.rc;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.syntax.Implication;
import org.tweetyproject.logics.pl.syntax.Negation;
import org.tweetyproject.logics.pl.syntax.PlFormula;

import com.extrcproject.core.entailment.Cache;
import com.extrcproject.core.syntax.Algorithm;
import com.extrcproject.core.syntax.DefeasibleImplication;
import com.extrcproject.core.syntax.EntailmentResult;
import com.extrcproject.core.syntax.KnowledgeBase;
import com.extrcproject.core.syntax.Ranking;

/**
 * Represent a cached reasoner using naive rational closure algorithm.
 *
 * @author Maqhobosheane Mohlerepe
 * @author Thabo Vincent Moloi
 */
public class CachedNaiveRCReasoner extends RCReasoner {

    protected final Cache<EntailmentResult> entailmentResultCache;
    protected final Cache<Ranking> finalRanksCache;

    /**
     * Create new cached reasoner using naive rational closure algorithm.
     *
     * @param reasoner SAT Reasoner.
     * @param queryResultCache Cache containing final results.
     * @param finalRanksCache Cache containing final ranks.
     */
    public CachedNaiveRCReasoner(SatReasoner reasoner, Cache<EntailmentResult> entailmentResultCache, Cache<Ranking> finalRanksCache) {
        super(reasoner);
        this.entailmentResultCache = entailmentResultCache;
        this.finalRanksCache = finalRanksCache;
        algorithmType = Algorithm.Type.CACHED_NAIVE;
    }

    @Override
    public EntailmentResult query(DefeasibleImplication formula, Ranking ranking) {
        // Combine all formulas
        KnowledgeBase union = ranking.unionAll();

        // Return result if in cache
        if (entailmentResultCache.contains(formula, union)) {
            return entailmentResultCache.get(formula, union);
        }

        // Negation of antecedent
        PlFormula negation = new Negation(((Implication) formula).getFirstFormula());

        Ranking removedRanking;

        if (finalRanksCache.contains(negation, union)) {
            // If final ranks are cached get and find removed ranks
            removedRanking = new Ranking(ranking);
            Ranking finalRanking = finalRanksCache.get(negation, union);
            removedRanking.removeAll(finalRanking);
            union = finalRanking.unionAll();
        } else {
            removedRanking = new Ranking();
            int i = 0;
            while (!union.isEmpty() && reasoner.query(union, negation) && i < ranking.size() - 1) {
                // Remove exceptional formulas
                removedRanking.add(ranking.get(i));
                union.removeAll(ranking.get(i));
                i++;
            }
        }

        // Check entailment
        boolean entailed = !union.isEmpty() && reasoner.query(union, formula);

        return new RCEntailmentResult(algorithmType, entailed, removedRanking);
    }

}

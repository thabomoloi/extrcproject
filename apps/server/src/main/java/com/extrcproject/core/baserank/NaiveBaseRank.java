package com.extrcproject.core.baserank;

import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.syntax.Negation;

import com.extrcproject.core.syntax.KnowledgeBase;

/**
 * Defines methods for computing ranking using the naive base rank algorithm.
 *
 * @author Thabo Vincent Moloi
 */
public class NaiveBaseRank extends BaseRankAlgorithm {

    /**
     * Create naive base rank algorithm.
     *
     * @param reasoner SAT Reasoner
     */
    public NaiveBaseRank(SatReasoner reasoner) {
        super(reasoner);
    }

    @Override
    protected KnowledgeBase getExceptionals(KnowledgeBase defeasible, KnowledgeBase classical) {
        KnowledgeBase exceptionals = new KnowledgeBase();
        KnowledgeBase all = defeasible.union(classical);
        defeasible.antecedents().forEach((antecedent) -> {
            if (reasoner.query(all, new Negation(antecedent))) {
                exceptionals.add(antecedent);
            }
        });
        return exceptionals;
    }

}

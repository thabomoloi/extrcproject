package com.extrcproject.core.syntax;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents ranking of formulas.
 * 
 * @author Thabo Vincent Moloi
 */
public class Ranking extends ArrayList<Rank> {

    /**
     * Constructs an empty ranking.
     */
    public Ranking() {
        super();
    }

    /**
     * Constructs a ranking from a collection of ranks.
     *
     * @param ranks
     */
    public Ranking(Collection<? extends Rank> ranks) {
        super(ranks);
    }

    /**
     * Create and add new rank given a rank number and knowledge base of
     * formulas.
     *
     * @param rankNumber Rank number.
     * @param knowledgeBase Knowledge base of formulas.
     */
    public void addRank(int rankNumber, KnowledgeBase knowledgeBase) {
        this.add(new Rank(rankNumber, knowledgeBase));
    }

    /**
     * Get the rank given the rank number.
     *
     * @param rankNumber Rank number.
     */
    public Rank getRank(int rankNumber) {
        if (rankNumber == Integer.MAX_VALUE) {
            return this.get(this.size() - 1);
        }
        return this.get(rankNumber);
    }

}

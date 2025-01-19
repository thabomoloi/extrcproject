package com.extrcproject.core.baserank;

import com.extrcproject.core.syntax.BaseRankResult;
import com.extrcproject.core.syntax.KnowledgeBase;

/**
 * Defines contract for computing raking based on a defeasible knowledge base.
 *
 * @author Thabo Vincent Moloi
 */
public interface BaseRank {

    /**
     * Computes rankings using base rank algorithm.
     *
     * @param knowledgeBase The knowledge base to rank
     * @return Ressult with ranked knowledge base.
     */
    BaseRankResult computeBaseRank(KnowledgeBase knowledgeBase);
}

package com.extrcproject.core.syntax;

import org.tweetyproject.logics.pl.syntax.Implication;
import org.tweetyproject.logics.pl.syntax.Negation;
import org.tweetyproject.logics.pl.syntax.PlFormula;

/**
 * Represents full results of the user's query.
 *
 * @author Thabo Vincent Moloi
 */
public class QueryResult {

    private final DefeasibleImplication formula;
    private final KnowledgeBase knowledgeBase;
    private final Negation negation;
    private final BaseRankResult baseRankResult;
    private final EntailmentResult entailmentResult;

    public QueryResult(DefeasibleImplication formula, KnowledgeBase knowledgeBase, BaseRankResult baseRankResult, EntailmentResult entailmentResult) {
        this.baseRankResult = baseRankResult;
        this.entailmentResult = entailmentResult;
        this.formula = formula;
        this.knowledgeBase = knowledgeBase;
        this.negation = new Negation(((Implication) formula).getFirstFormula());
    }

    /**
     * Retrieve query formula
     */
    public PlFormula getFormula() {
        return formula;
    }

    /**
     * Retrieve knowledge base
     */
    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    /**
     * Get the negation of the antecedent
     */
    public Negation getNegation() {
        return negation;
    }

    /**
     * Get the base rank result
     */
    public BaseRankResult getBaseRankResult() {
        return baseRankResult;
    }

    /**
     * Get the entailment result
     */
    public EntailmentResult getEntailmentResult() {
        return entailmentResult;
    }

}

package com.extrcproject.core.syntax;

/**
 * Represents result from the BaseRank Algorithm.
 *
 * @author Thabo Vincent Moloi
 */
public class BaseRankResult {

    private final Ranking sequence;
    private final Ranking ranking;
    private double timeTaken;

    /**
     * Create new {@link BaseRankResult} with ranked knowledge base,
     * exceptionalty sequence.
     *
     * @param ranking Ranked knowledge base.
     * @param sequence Exceptionality sequence.
     */
    public BaseRankResult(Ranking ranking, Ranking sequence) {
        this.ranking = ranking;
        this.sequence = sequence;
        this.timeTaken = 0;
    }

    /**
     * Create a copy of {@link BaseRankResult}.
     *
     * @param baseRankResult Result to copy.
     */
    public BaseRankResult(BaseRankResult baseRankResult) {
        this(new Ranking(baseRankResult.ranking), new Ranking(baseRankResult.sequence));
        this.timeTaken = baseRankResult.timeTaken;
    }

    /**
     * Get the ranked knowledge base.
     *
     * @return Ranked knowledge base.
     */
    public Ranking getRanking() {
        return new Ranking(ranking);
    }

    /**
     * Get the exceptionality sequence.
     *
     * @return Exceptionality sequence.
     */
    public Ranking getSequence() {
        return new Ranking(sequence);
    }

    /**
     * Get time taken to compute base rank.
     *
     * @return Time taken.
     */
    public double getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(double timeTaken) {
        this.timeTaken = timeTaken;
    }

}

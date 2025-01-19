package com.extrcproject.core.entailment.rc;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.sat.Sat4jSolver;
import org.tweetyproject.logics.pl.sat.SatSolver;

import com.extrcproject.core.baserank.BaseRank;
import com.extrcproject.core.baserank.BaseRankResult;
import com.extrcproject.core.baserank.NaiveBaseRank;
import com.extrcproject.core.entailment.Algorithm;
import com.extrcproject.core.entailment.EntailmentResult;
import com.extrcproject.core.entailment.Reasoner;
import com.extrcproject.core.syntax.KnowledgeBase;
import com.extrcproject.core.util.DefeasibleParser;

public abstract class RCReasonerTest {

    protected static final SatReasoner satReasoner = new SatReasoner();
    protected static final DefeasibleParser parser = new DefeasibleParser();
    protected static final BaseRank baseRank = new NaiveBaseRank(satReasoner);
    protected Reasoner reasoner;
    protected Algorithm.Type algorithmType;

    @BeforeAll
    public static void beforeAll() {
        SatSolver.setDefaultSolver(new Sat4jSolver());
    }

    @Test
    public void testEmptyKnowledgeBase() throws Exception {
        BaseRankResult baseRankResult = baseRank.computeBaseRank(new KnowledgeBase());
        EntailmentResult entailmentResult = reasoner.query(parser.parseFormula("p ~> q"), baseRankResult.getRanking());

        assertEquals(Algorithm.Name.RATIONAL_CLOSURE, entailmentResult.getAlgorithm().getName());
        assertEquals(algorithmType, entailmentResult.getAlgorithm().getType());
        assertFalse(entailmentResult.getEntailed());
        assertTrue(entailmentResult.getRemovedRanking().isEmpty());
    }

    @Test
    public void testNoExceptionalFormulas() throws Exception {
        EntailmentResult entailmentResult = computeEntailmentResult("b ~> !e", "test-no-exceptions.txt");

        assertTrue(entailmentResult.getEntailed());
        assertTrue(entailmentResult.getRemovedRanking().isEmpty());
    }

    @Test
    public void testClassicalOnly() throws Exception {
        EntailmentResult entailmentResult = computeEntailmentResult("a ~> d", "test-classical-only.txt");

        assertTrue(entailmentResult.getEntailed());
        assertTrue(entailmentResult.getRemovedRanking().isEmpty());
    }

    @Test
    public void testDefeasibleOnly() throws Exception {
        EntailmentResult entailmentResult = computeEntailmentResult("a ~> d", "test-defeasible-only.txt");

        assertFalse(entailmentResult.getEntailed());
        assertEquals(1, entailmentResult.getRemovedRanking().size());  // 1 Rank removed
        assertEquals(2, entailmentResult.getRemovedRanking().get(0).size()); // Rank 0 has two formulas
    }

    @Test
    public void testDefeasibleButClassical() throws Exception {
        EntailmentResult entailmentResult = computeEntailmentResult("a ~> d", "test-defeasible-but-classical.txt");

        assertTrue(entailmentResult.getEntailed());
        assertTrue(entailmentResult.getRemovedRanking().isEmpty());
    }

    @Test
    public void testDefeasibleAndClassical() throws Exception {
        EntailmentResult entailmentResult = computeEntailmentResult("p ~> !f", "test-defeasible-and-classical.txt");

        assertTrue(entailmentResult.getEntailed());
        assertEquals(1, entailmentResult.getRemovedRanking().size());  // 1 Rank removed
        assertEquals(2, entailmentResult.getRemovedRanking().get(0).size()); // Rank 0 has two formulas
    }

    private EntailmentResult computeEntailmentResult(String formula, String knowledgeBaseFile) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(knowledgeBaseFile);
        KnowledgeBase kb = parser.parseInputStream(inputStream);
        BaseRankResult baseRankResult = baseRank.computeBaseRank(kb);
        return reasoner.query(parser.parseFormula(formula), baseRankResult.getRanking());
    }
}

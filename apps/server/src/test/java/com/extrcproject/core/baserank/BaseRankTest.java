package com.extrcproject.core.baserank;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.sat.Sat4jSolver;
import org.tweetyproject.logics.pl.sat.SatSolver;

import com.extrcproject.core.syntax.BaseRankResult;
import com.extrcproject.core.syntax.KnowledgeBase;
import com.extrcproject.core.util.DefeasibleParser;

public abstract class BaseRankTest {

    protected static final SatReasoner reasoner = new SatReasoner();
    protected static final DefeasibleParser parser = new DefeasibleParser();
    protected BaseRank baseRank;

    @BeforeAll
    public static void beforeAll() {
        SatSolver.setDefaultSolver(new Sat4jSolver());
    }

    @Test
    public void testWithEmptyKnowledgeBase() {
        KnowledgeBase kb = new KnowledgeBase();
        BaseRankResult baseRankResult = baseRank.computeBaseRank(kb);

        assertEquals(0, baseRankResult.getSequence().size());
        assertTrue(baseRankResult.getRanking().getLast().isEmpty());
    }

    @Test
    public void testDefeasibleAndClassical() throws Exception {
        BaseRankResult baseRankResult = computeBaseRankResult("test-defeasible-and-classical.txt");

        assertEquals(3, baseRankResult.getSequence().size());
        assertEquals(3, baseRankResult.getSequence().get(0).size());
        assertEquals(1, baseRankResult.getSequence().get(1).size());
        assertEquals(0, baseRankResult.getSequence().get(2).size()); // inifinite element

        assertEquals(3, baseRankResult.getRanking().size());
        assertEquals(2, baseRankResult.getRanking().get(0).size());
        assertEquals(1, baseRankResult.getRanking().get(1).size());
        assertEquals(1, baseRankResult.getRanking().get(2).size()); // infinite rank
    }

    @Test
    public void testDefeasibleOnly() throws Exception {
        BaseRankResult baseRankResult = computeBaseRankResult("test-defeasible-only.txt");

        assertEquals(3, baseRankResult.getSequence().size());
        assertEquals(4, baseRankResult.getSequence().get(0).size());
        assertEquals(2, baseRankResult.getSequence().get(1).size());
        assertEquals(0, baseRankResult.getSequence().get(2).size()); // infinite element

        assertEquals(3, baseRankResult.getRanking().size());
        assertEquals(2, baseRankResult.getRanking().get(0).size());
        assertEquals(2, baseRankResult.getRanking().get(1).size());
        assertEquals(0, baseRankResult.getRanking().get(2).size()); // infinite rank
    }

    @Test
    public void testClassicalOnly() throws Exception {
        BaseRankResult baseRankResult = computeBaseRankResult("test-classical-only.txt");

        assertEquals(0, baseRankResult.getSequence().size());

        assertEquals(1, baseRankResult.getRanking().size());
        assertEquals(4, baseRankResult.getRanking().get(0).size()); // infinite rank
    }

    @Test
    public void testDefeasibleButClassical() throws Exception {
        BaseRankResult baseRankResult = computeBaseRankResult("test-defeasible-but-classical.txt");

        assertEquals(1, baseRankResult.getSequence().size());
        assertEquals(3, baseRankResult.getSequence().get(0).size()); // infinite element

        assertEquals(1, baseRankResult.getRanking().size());
        assertEquals(3, baseRankResult.getRanking().get(0).size()); // infinite rank
    }

    @Test
    public void testNoExceptionalFormulas() throws Exception {
        BaseRankResult baseRankResult = computeBaseRankResult("test-no-exceptions.txt");

        assertEquals(2, baseRankResult.getSequence().size());
        assertEquals(5, baseRankResult.getSequence().get(0).size());
        assertEquals(0, baseRankResult.getSequence().get(1).size()); // infinite element

        assertEquals(2, baseRankResult.getRanking().size());
        assertEquals(5, baseRankResult.getRanking().get(0).size());
        assertEquals(1, baseRankResult.getRanking().get(1).size()); // infinite rank
    }

    private BaseRankResult computeBaseRankResult(String knowledgeBaseFile) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(knowledgeBaseFile);
        KnowledgeBase kb = parser.parseInputStream(inputStream);
        return baseRank.computeBaseRank(kb);
    }

}

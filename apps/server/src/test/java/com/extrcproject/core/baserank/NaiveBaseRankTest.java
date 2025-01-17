package com.extrcproject.core.baserank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.sat.Sat4jSolver;
import org.tweetyproject.logics.pl.sat.SatSolver;

import com.extrcproject.core.syntax.KnowledgeBase;
import com.extrcproject.core.util.DefeasibleParser;

public class NaiveBaseRankTest {

    private static final SatReasoner reasoner = new SatReasoner();
    private static final NaiveBaseRank naiveBaseRank = new NaiveBaseRank(reasoner);
    private static final DefeasibleParser parser = new DefeasibleParser();

    @BeforeAll
    public static void beforeAll() {
        SatSolver.setDefaultSolver(new Sat4jSolver());
    }

    @Test
    public void testWithEmptyKnowledgeBase() {
        KnowledgeBase kb = new KnowledgeBase();
        var baseRank = naiveBaseRank.computeBaseRank(kb);

        assertNotNull(baseRank);
        assertEquals(0, baseRank.getSequence().size());
        assertTrue(baseRank.getRanking().getLast().isEmpty());
    }

    @Test
    public void testDefeasibleAndClassical() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-defeasible-and-classical.txt");
        KnowledgeBase kb = parser.parseInputStream(inputStream);
        BaseRankResult baseRank = naiveBaseRank.computeBaseRank(kb);

        assertNotNull(baseRank);
        assertEquals(3, baseRank.getSequence().size());
        assertEquals(3, baseRank.getSequence().get(0).size());
        assertEquals(1, baseRank.getSequence().get(1).size());
        assertEquals(0, baseRank.getSequence().get(2).size()); // inifinite element

        assertEquals(3, baseRank.getRanking().size());
        assertEquals(2, baseRank.getRanking().get(0).size());
        assertEquals(1, baseRank.getRanking().get(1).size());
        assertEquals(1, baseRank.getRanking().get(2).size()); // infinite rank
    }

    @Test
    public void testDefeasibleOnly() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-defeasible-only.txt");
        KnowledgeBase kb = parser.parseInputStream(inputStream);
        BaseRankResult baseRank = naiveBaseRank.computeBaseRank(kb);

        assertNotNull(baseRank);
        assertEquals(3, baseRank.getSequence().size());
        assertEquals(4, baseRank.getSequence().get(0).size());
        assertEquals(2, baseRank.getSequence().get(1).size());
        assertEquals(0, baseRank.getSequence().get(2).size()); // infinite element

        assertEquals(3, baseRank.getRanking().size());
        assertEquals(2, baseRank.getRanking().get(0).size());
        assertEquals(2, baseRank.getRanking().get(1).size());
        assertEquals(0, baseRank.getRanking().get(2).size()); // infinite rank
    }

    @Test
    public void testClassicalOnly() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-classical-only.txt");
        KnowledgeBase kb = parser.parseInputStream(inputStream);
        BaseRankResult baseRank = naiveBaseRank.computeBaseRank(kb);

        assertNotNull(baseRank);
        assertEquals(0, baseRank.getSequence().size());

        assertEquals(1, baseRank.getRanking().size());
        assertEquals(4, baseRank.getRanking().get(0).size()); // infinite rank
    }

    @Test
    public void testDefeasibleButClassical() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-defeasible-but-classical.txt");
        KnowledgeBase kb = parser.parseInputStream(inputStream);
        BaseRankResult baseRank = naiveBaseRank.computeBaseRank(kb);

        assertNotNull(baseRank);
        assertEquals(1, baseRank.getSequence().size());
        assertEquals(3, baseRank.getSequence().get(0).size()); // infinite element

        assertEquals(1, baseRank.getRanking().size());
        assertEquals(3, baseRank.getRanking().get(0).size()); // infinite rank
    }

}

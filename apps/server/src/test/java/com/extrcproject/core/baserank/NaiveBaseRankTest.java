package com.extrcproject.core.baserank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.sat.Sat4jSolver;
import org.tweetyproject.logics.pl.sat.SatSolver;

import com.extrcproject.core.syntax.KnowledgeBase;

public class NaiveBaseRankTest {

    private static final SatReasoner reasoner = new SatReasoner();

    private static final NaiveBaseRank naiveBaseRank = new NaiveBaseRank(reasoner);

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

}

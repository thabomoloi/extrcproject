package com.extrcproject.core.entailment.rc;

import org.junit.jupiter.api.BeforeEach;

import com.extrcproject.core.syntax.Algorithm;

public class TernaryRCReasonerTest extends RCReasonerTest {

    @BeforeEach
    public void beforeEach() {
        reasoner = new TernaryRCReasoner(satReasoner);
        algorithmType = Algorithm.Type.TERNARY;
    }
}

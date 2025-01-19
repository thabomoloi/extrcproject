package com.extrcproject.core.entailment.rc;

import org.junit.jupiter.api.BeforeEach;

import com.extrcproject.core.entailment.Algorithm;

public class NaiveRCReasonerTest extends RCReasonerTest {

    @BeforeEach
    public void beforeEach() {
        reasoner = new NaiveRCReasoner(satReasoner);
        algorithmType = Algorithm.Type.NAIVE;
    }
}

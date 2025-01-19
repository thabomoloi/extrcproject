package com.extrcproject.core.entailment.rc;

import org.junit.jupiter.api.BeforeEach;

import com.extrcproject.core.syntax.Algorithm;

public class BinaryRCReasonerTest extends RCReasonerTest {

    @BeforeEach
    public void beforeEach() {
        reasoner = new BinaryRCReasoner(satReasoner);
        algorithmType = Algorithm.Type.BINARY;
    }
}

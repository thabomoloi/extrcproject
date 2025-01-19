package com.extrcproject.core.entailment.rc;

import org.junit.jupiter.api.BeforeEach;

import com.extrcproject.core.entailment.Cache;
import com.extrcproject.core.syntax.Algorithm;

public class CachedNaiveRCReasonerTest extends RCReasonerTest {

    @BeforeEach
    public void beforeEach() {
        reasoner = new CachedNaiveRCReasoner(satReasoner, new Cache<>(), new Cache<>());
        algorithmType = Algorithm.Type.CACHED_NAIVE;
    }
}

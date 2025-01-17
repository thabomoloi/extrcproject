package com.extrcproject.core.baserank;

import org.junit.jupiter.api.BeforeEach;

public class ThreadedBaseRankTest extends BaseRankTest {

    @BeforeEach
    public void beforeEach() {
        baseRank = new ThreadedBaseRank(reasoner);
    }
}

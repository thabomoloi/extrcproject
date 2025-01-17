package com.extrcproject.core.baserank;

import org.junit.jupiter.api.BeforeEach;

public class NaiveBaseRankTest extends BaseRankTest {

    @BeforeEach
    public void beforeEach() {
        baseRank = new NaiveBaseRank(reasoner);
    }
}

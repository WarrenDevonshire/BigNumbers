package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BigNumberTest {

    BigNumber num;
    String str;

    @BeforeEach
    void setUp() {
        str = "000001002";
        num = new BigNumber(str);
    }

    @AfterEach
    void tearDown() {
    }

}
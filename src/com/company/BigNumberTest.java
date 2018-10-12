package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BigNumberTest {

    BigNumber num;
    BigNumber num2;
    String str;
    String str2;

    @BeforeEach
    void setUp() {
        str = "000001002";
        str2 = "000001002";
        num = new BigNumber(str);
        num2 = new BigNumber(str2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void add() {
        BigNumber sum = num.add(num2);
        assertEquals(sum.toString(), "2004");
    }
}
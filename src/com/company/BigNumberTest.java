package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BigNumberTest {

    BigNumber num;

    @BeforeEach
    void setUp() {
        int base = 10;
        String str = "123456789";
        num = new BigNumber(str, base);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void printBigNumber() {
        num.printBigNumber();
    }
}
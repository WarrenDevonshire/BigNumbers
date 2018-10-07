package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BigNumberTest {

    BigNumber num;

    @BeforeEach
    void setUp() {
        String str = "05";
        num = new BigNumber(str);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void printBigNumber() {
        num.printBigNumber();
    }

    @Test
    void add() {
        BigNumber m = num.add(num);
        m.printBigNumber();
    }

    @Test
    void negate() {
        BigNumber num2 = new BigNumber("05");
        BigNumber m = num.negate();
        m.add(num2).printBigNumber();
    }
}
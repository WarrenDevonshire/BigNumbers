package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BigNumberTest {

    BigNumber num;

    @BeforeEach
    void setUp() {
        String str = "000001002";
        num = new BigNumber(str);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void printBigNumber() {
        num.printBigNumber();
    }

//    @Test
//    void add() {
//
//        BigNumber m = num.add(num);
//
//        m.printBigNumber();
//    }
//
//    @Test
//    void negate() {
//        num.negate().printBigNumber();
//    }
//
//    @Test
//    void subtract() {
//        num.subtract(new BigNumber("4")).negate().printBigNumber();
//    }
}
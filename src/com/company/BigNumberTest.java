package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


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

//    @Test
//    void printBigNumber() {
//        str = str.replaceFirst("^0+(?!$)", "");
//        str = str.replaceFirst("^/-+(?!$)", "");
//        num.printBigNumber();
//        assertEquals(num.toString(), str);
//    }

//    @Test
//    void add() {
//
//        BigNumber m = num.add(num);
//
//        m.printBigNumber();
//    }
//
    @Test
    void negate() {
        num.negate(num).printBigNumber();
    }
//
//    @Test
//    void subtract() {
//        num.subtract(new BigNumber("4")).negate().printBigNumber();
//    }
}
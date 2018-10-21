package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BigNumberTest {
    private BigNumber[] bigNumbers1;
    private BigNumber[] bigNumbers2;
    private BigInteger[] bigIntegers1;
    private BigInteger[] bigIntegers2;
    private String[] bigNumberResults;
    private String[] bigIntegerResults;
    private int size = 100;
    private int base = 10;
    private int numberSize = 50;

    @BeforeEach
    void setUp() {
        bigNumbers1 = new BigNumber[size];
        bigNumbers2 = new BigNumber[size];
        bigNumberResults = new String[size];
        bigIntegers1 = new BigInteger[size];
        bigIntegers2 = new BigInteger[size];
        bigIntegerResults = new String[size];

        Random r = new Random();
        StringBuilder temp = new StringBuilder(numberSize);
        String str;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < numberSize; j++) {
                temp.append(r.nextInt(base));
            }
            str = temp.toString();
            temp.setLength(0);
            bigNumbers1[i] = new BigNumber(str);
            bigNumbers2[i] = new BigNumber(str);
            bigIntegers1[i] = new BigInteger(str);
            bigIntegers2[i] = new BigInteger(str);
        }
    }

    @AfterEach
    void tearDown() {
        for (int i = 0; i < size; i++) {
            System.out.print(bigIntegerResults[i]);
            System.out.print(" : ");
            System.out.println(bigNumberResults[i]);
            assertEquals(bigIntegerResults[i], bigNumberResults[i]);
        }
    }

    @Test
    void addPositives() {
        for (int i = 0; i < size; i++) {
            bigNumberResults[i] = bigNumbers1[i].add(bigNumbers2[size - 1 - i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].add(bigIntegers2[size - 1 - i]).toString(base);
        }
    }

    @Test
    void addNegatives() {
        for (int i = 0; i < size; i++) {
            bigNumberResults[i] = bigNumbers1[i].negate().add(bigNumbers2[size - 1 - i].negate()).toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().add(bigIntegers2[size - 1 - i].negate()).toString(base);
        }
    }

    @Test
    void addOpposites() {
        for (int i = 0; i < size; i++) {
            bigNumberResults[i] = bigNumbers1[i].negate().add(bigNumbers2[size - 1 - i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().add(bigIntegers2[size - 1 - i]).toString(base);
        }
    }

    /**
     * Tests BigNumber class against BigInteger class for multiplying a negative number by a positive number.
     *
     * @author Daniel Haluszka
     */
    @Test
    void multNegatives() {

        for (int i = 0; i < size; i++) {
            bigNumberResults[i] = bigNumbers1[i].negate().multiply(bigNumbers2[size - 1 - i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().multiply(bigIntegers2[size - 1 - i]).toString(base);
        }

    }

    /**
     * Tests BigNumber class against BigInteger class for multiplying two positive numbers.
     *
     * @author Daniel Haluszka
     */
    @Test
    void multPositives() {

        for (int i = 0; i < size; i++) {
            bigNumberResults[i] = bigNumbers1[i].multiply(bigNumbers2[size - 1 - i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].multiply(bigIntegers2[size - 1 - i]).toString(base);
        }

    }

    /**
     * Tests BigNumber class against BigInteger class for dividing a negative number by a positive number.
     *
     * @author Daniel Haluszka
     */
    @Test
    void divNegatives() {

        for (int i = 0; i < size; i++) {
            bigNumberResults[i] = bigNumbers1[i].negate().divide(bigNumbers2[size - 1 - i]).getFirst().toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().divide(bigIntegers2[size - 1 - i]).toString(base);
        }

    }

    /**
     * Tests BigNumber class against BigInteger class for dividing two positive numbers.
     *
     * @author Daniel Haluszka
     */
    @Test
    void divPositives() {

        for (int i = 0; i < size; i++) {
            bigNumberResults[i] = bigNumbers1[i].divide(bigNumbers2[size - 1 - i]).getFirst().toString();
            bigIntegerResults[i] = bigIntegers1[i].divide(bigIntegers2[size - 1 - i]).toString(base);
        }

    }

    @Test
    void subtractPositives() {
        for (int i = 0; i < size; i++) {
            bigNumberResults[i] = bigNumbers1[i].subtract(bigNumbers2[size - 1 - i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].subtract(bigIntegers2[size - 1 - i]).toString(base);
        }
    }

    @Test
    void subtractNegatives() {
        for (int i = 0; i < size; i++) {
            bigNumberResults[i] = bigNumbers1[i].negate().subtract(bigNumbers2[size - 1 - i].negate()).toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().subtract(bigIntegers2[size - 1 - i].negate()).toString(base);
        }
    }

    @Test
    void subtractOpposites() {
        for (int i = 0; i < size; i++) {
            bigNumberResults[i] = bigNumbers1[i].negate().subtract(bigNumbers2[size - 1 - i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().subtract(bigIntegers2[size - 1 - i]).toString(base);
        }
    }
}
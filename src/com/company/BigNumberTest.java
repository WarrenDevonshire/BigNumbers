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
    private int numberOfTests = 100;
    private int base = 10;
    private int numberSize = 20;

    /**
     * @author Warren Devonshire
     */
    @BeforeEach
    void setUp() {
        bigNumbers1 = new BigNumber[numberOfTests];
        bigNumbers2 = new BigNumber[numberOfTests];
        bigNumberResults = new String[numberOfTests];
        bigIntegers1 = new BigInteger[numberOfTests];
        bigIntegers2 = new BigInteger[numberOfTests];
        bigIntegerResults = new String[numberOfTests];

        int nSize1;
        int nSize2;
        Random r = new Random();
        nSize1 = r.nextInt(numberSize) + 1;
        nSize2 = r.nextInt(numberSize) + 1;
        StringBuilder temp1 = new StringBuilder(numberSize);
        StringBuilder temp2 = new StringBuilder(numberSize);
        String str1, str2;
        for(int i = 0; i < numberOfTests; i++) {
            for(int j = 0; j < nSize1; j++) {
                temp1.append(r.nextInt(base));
            }
            for(int k = 0; k < nSize2; k++){
                temp2.append(r.nextInt(base));
            }
            str1 = temp1.toString();
            str2 = temp2.toString();
            temp1.setLength(0);
            temp2.setLength(0);
            bigNumbers1[i] = new BigNumber(str1);
            bigNumbers2[i] = new BigNumber(str2);
            bigIntegers1[i] = new BigInteger(str1);
            bigIntegers2[i] = new BigInteger(str2);
        }
    }

    /**
     * @author Warren Devonshire
     */
    @AfterEach
    void tearDown() {
        for (int i = 0; i < numberOfTests; i++) {
            System.out.print(bigIntegerResults[i]);
            System.out.print(" : ");
            System.out.println(bigNumberResults[i]);
            assertEquals(bigIntegerResults[i], bigNumberResults[i]);
        }
    }

    /**
     * @author Warren Devonshire
     */
    @Test
    void addPositives() {
        for (int i = 0; i < numberOfTests; i++) {
            bigNumberResults[i] = bigNumbers1[i].add(bigNumbers2[numberOfTests - 1 - i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].add(bigIntegers2[numberOfTests - 1 - i]).toString(base);
        }
    }

    /**
     * @author Warren Devonshire
     */
    @Test
    void addNegatives() {
        for (int i = 0; i < numberOfTests; i++) {
            bigNumberResults[i] = bigNumbers1[i].negate().add(bigNumbers2[numberOfTests - 1 - i].negate()).toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().add(bigIntegers2[numberOfTests - 1 - i].negate()).toString(base);
        }
    }

    /**
     * @author Warren Devonshire
     */
    @Test
    void addOpposites() {
        for (int i = 0; i < numberOfTests; i++) {
            bigNumberResults[i] = bigNumbers1[i].negate().add(bigNumbers2[numberOfTests - 1 - i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().add(bigIntegers2[numberOfTests - 1 - i]).toString(base);
        }
    }

    /**
     * @author Warren Devonshire
     */
    @Test
    void subtractPositives() {
        for (int i = 0; i < numberOfTests; i++) {
            bigNumberResults[i] = bigNumbers1[i].subtract(bigNumbers2[numberOfTests - 1 - i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].subtract(bigIntegers2[numberOfTests - 1 - i]).toString(base);
        }
    }

    /**
     * @author Warren Devonshire
     */
    @Test
    void subtractNegatives() {
        for (int i = 0; i < numberOfTests; i++) {
            bigNumberResults[i] = bigNumbers1[i].negate().subtract(bigNumbers2[numberOfTests - 1 - i].negate()).toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().subtract(bigIntegers2[numberOfTests - 1 - i].negate()).toString(base);
        }
    }

    /**
     * @author Warren Devonshire
     */
    @Test
    void subtractOpposites() {
        for (int i = 0; i < numberOfTests; i++) {
            bigNumberResults[i] = bigNumbers1[i].negate().subtract(bigNumbers2[numberOfTests - 1 - i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().subtract(bigIntegers2[numberOfTests - 1 - i]).toString(base);
        }
    }

    /**
     * Tests BigNumber class against BigInteger class for multiplying a negative number by a positive number.
     *
     * @author Daniel Haluszka
     */
    @Test
    void multNegatives() {

        for (int i = 0; i < numberOfTests; i++) {
            bigNumberResults[i] = bigNumbers1[i].negate().multiply(bigNumbers2[numberOfTests - 1 - i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().multiply(bigIntegers2[numberOfTests - 1 - i]).toString(base);
        }

    }

    /**
     * Tests BigNumber class against BigInteger class for multiplying two positive numbers.
     *
     * @author Daniel Haluszka
     */
    @Test
    void multPositives() {

        for (int i = 0; i < numberOfTests; i++) {
            bigNumberResults[i] = bigNumbers1[i].multiply(bigNumbers2[numberOfTests - 1 - i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].multiply(bigIntegers2[numberOfTests - 1 - i]).toString(base);
        }

    }

    /**
     * Tests BigNumber class against BigInteger class for dividing a negative number by a positive number.
     *
     * @author Daniel Haluszka
     */
    @Test
    void divNegatives() {

        for (int i = 0; i < numberOfTests; i++) {
            bigNumberResults[i] = bigNumbers1[i].negate().divide(bigNumbers2[numberOfTests - 1 - i]).getFirst().toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().divide(bigIntegers2[numberOfTests - 1 - i]).toString(base);
        }

    }

    /**
     * Tests BigNumber class against BigInteger class for dividing two positive numbers.
     *
     * @author Daniel Haluszka
     */
    @Test
    void divPositives() {

        for (int i = 0; i < numberOfTests; i++) {
            bigNumberResults[i] = bigNumbers1[i].divide(bigNumbers2[numberOfTests - 1 - i]).getFirst().toString();
            bigIntegerResults[i] = bigIntegers1[i].divide(bigIntegers2[numberOfTests - 1 - i]).toString(base);
        }

    }
}
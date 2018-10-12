package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BigNumberTest {
    BigNumber[] bigNumbers1;
    BigNumber[] bigNumbers2;
    BigInteger[] bigIntegers1;
    BigInteger[] bigIntegers2;
    String[] bigNumberResults;
    String[] bigIntegerResults;
    int size = 100;
    int base = 10;

    @BeforeEach
    void setUp() {
        bigNumbers1 = new BigNumber[size];
        bigNumbers2 = new BigNumber[size];
        bigNumberResults = new String[size];
        bigIntegers1 = new BigInteger[size];
        bigIntegers2 = new BigInteger[size];
        bigIntegerResults = new String[size];

        Random r = new Random();
        StringBuilder temp = new StringBuilder(2);
        String str;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < 2; j++){
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
        for(int i = 0; i < size; i++){
            System.out.print(bigIntegerResults[i]);
            System.out.print(" : ");
            System.out.println(bigNumberResults[i]);
            assertEquals(bigIntegerResults[i], bigNumberResults[i]);
        }
    }

    @Test
    void addPositives() {
        for(int i = 0; i < size; i++){
            bigNumberResults[i] = bigNumbers1[i].add(bigNumbers2[i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].add(bigIntegers2[i]).toString(base);
        }
    }

    @Test
    void addNegatives(){
        for(int i = 0; i < size; i++){
            bigNumberResults[i] = bigNumbers1[i].negate().add(bigNumbers2[i].negate()).toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().add(bigIntegers2[i].negate()).toString(base);
        }
    }

    @Test
    void addOpposites(){
        for(int i = 0; i < size; i++){
            bigNumberResults[i] = bigNumbers1[i].negate().add(bigNumbers2[size - 1 - i]).toString();
            bigIntegerResults[i] = bigIntegers1[i].negate().add(bigIntegers2[size - 1 - i]).toString(base);
        }
    }
}
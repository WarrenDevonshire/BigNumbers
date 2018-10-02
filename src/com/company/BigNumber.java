package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class BigNumber {

    private ArrayList<Integer> bigNumber;
    int base;

    //assuming base 10 for now.
    //does not check if string is valid
    public BigNumber(String number, int base){
        this.base = base;
        this.bigNumber = new ArrayList<>(number.length());

        for(int i = number.length() - 1; i >= 0; i--){
            this.bigNumber.add(Integer.parseInt(String.valueOf(number.charAt(i)), this.base));
        }
    }

    public void printBigNumber(){
        for(Integer i : bigNumber){
            System.out.print(i);
        }
        System.out.println();
    }

    public BigNumber(int size, int base){
        bigNumber = new ArrayList<>(size);
        this.base = base;

    }
//
//    public BigNumber add(BigNumber number){
//        int j = 0; //loop index
//        int k = 0; //keeps track of carries
//
//        BigNumber result = new BigNumber();
//
//    }
//
//    public BigNumber subtract(BigNumber number){
//
//    }
//
//    public BigNumber multiply(BigNumber number){
//
//    }
//
//    public BigNumber divide(BigNumber number){
//
//    }
//
//    public BigNumber negate(){
//
//    }
//
//    public int sign(){
//
//    }

    public void normalize(){

    }


}

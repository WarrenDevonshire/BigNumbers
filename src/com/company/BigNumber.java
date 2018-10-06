package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class BigNumber {

    public int[] bigNumber;
    private int base = 10;
    private int size;

    //assuming base 10 for now.
    //does not check if string is valid
    public BigNumber(String number){
        this.size = number.length()+1;
        this.bigNumber = new int[this.size];
        for(int i = 0; i < number.length(); i++){
            this.bigNumber[number.length() - 1 - i] = Integer.parseInt(String.valueOf(number.charAt(i)), this.base);
        }
        this.bigNumber[size-1] = 0;
    }

    public BigNumber add(BigNumber num){
        int k =0; int j = 0;//need to check if sizes are equal.
        BigNumber m = new BigNumber(this.size + 1);
        for(; j < this.size; j++){
            m.bigNumber[j] = (this.bigNumber[j] + num.bigNumber[j] + k) % this.base;
            k = (this.bigNumber[j] + num.bigNumber[j]) / this.base;
        }

        if(m.bigNumber[m.size - 2] == 9){
            m.bigNumber[m.size - 1] = 9;
        }else{
            m.bigNumber[m.size - 1] = 0;
        }

        return m;

    }

    public BigNumber(int size){
        this.size = size;
        this.bigNumber = new int[this.size];
    }

    public void printBigNumber(){
        for(int i = this.bigNumber.length - 1; i >= 0; i--) {
            System.out.print(bigNumber[i]);
        }
        System.out.println();
    }

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

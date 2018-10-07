package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public BigNumber negate(){
        int b = base - 1;
        for(int i : bigNumber){
            i = b - i;
        }

        return this.add(new BigNumber("01"));

    }

    public static String convertToTensCompliment(String signedNumber){
        int[] compliment;
        if(signedNumber.charAt(0) == '-'){
            compliment = new int[signedNumber.length()];
            compliment[0] = 9;
            for(int i = 1; i < signedNumber.length(); i++){
                compliment[i] = 9 - Integer.parseInt(String.valueOf(signedNumber.charAt(i)));
            }

        }else{
            compliment = new int[signedNumber.length() + 1];
            compliment[0] = 0;
            for(int i = 0; i < signedNumber.length(); i++){
                compliment[i + 1] = Integer.parseInt(String.valueOf(signedNumber.charAt(i)));
            }
        }
        return compliment.toString();
    }

    //Compliment Arrays are little Endian
    private int[] convertStringToComplimentArray(String in) throws IllegalArgumentException{
        if(in.length() == 0) throw new IllegalArgumentException("String length cannot be zero");

        int[] compliment;
        if(in.charAt(0) == '-'){
            compliment = new int[in.length()];
            compliment[0] = 9;
            for(int i = 1; i < in.length(); i++){
                try{
                    compliment[i] = 9 - Integer.parseInt(String.valueOf(in.charAt(i)));
                }catch(Exception e){
                    throw new IllegalArgumentException();
                }
            }
        }else if(Character.isDigit(in.charAt(0))){
            compliment = new int[in.length() + 1];
            compliment[0] = 0;
            for(int i = 0; i < in.length(); i++){
                try{
                    compliment[i + 1] = 9 - Integer.parseInt(String.valueOf(in.charAt(i)));
                }catch(Exception e){
                    throw new IllegalArgumentException();
                }
            }
        }else{
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < compliment.length / 2; i++){
            int temp = compliment[i];
            compliment[i] = compliment[compliment.length - i - 1];
            compliment[compliment.length - i - 1] = temp;
        }

        return compliment;
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

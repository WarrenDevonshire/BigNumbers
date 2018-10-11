package com.company;


import java.util.Arrays;

public class BigNumber {

    private int[] bigNumber;
    private int base = 10;
    private boolean negative;

    public BigNumber(String number)throws IllegalArgumentException{
        bigNumber = convertStringToComplimentArray(number);
        normalize();
    }

    private BigNumber(int[] number){
        bigNumber = number;
        normalize();
    }

    private void normalize(){
        int counter = 0;
        for(int i = 0; i < bigNumber.length; i++){
            if(bigNumber[i] == 0){
                counter++;
            }else{
                counter = 0;
            }
        }
        int[] temp = new int[bigNumber.length - counter];
        for(int i = 0; i < temp.length; i++){
            temp[i] = bigNumber[i];
        }
        bigNumber = temp;
    }
//
//    //Used to grow a bigNumber array filling new indexes with the sign.
//    //Used in order to make sums the same length. //fix this comment.
//    private void normalize(int toSize){
//        int[] temp = new int[toSize];
//        int sign = bigNumber[bigNumber.length - 1];
//        for(int i = 0; i < bigNumber.length; i++){
//            temp[i] = bigNumber[i];
//        }
//        for(int i = temp.length - bigNumber.length + 1; i < temp.length; i++){
//            temp[i] = sign;
//        }
//        bigNumber = temp;
//    }


//    public BigNumber add(BigNumber num){
//        if(bigNumber.length > num.bigNumber.length){
//            num.normalize(bigNumber.length);
//        }else if (bigNumber.length < num.bigNumber.length){
//            normalize(num.bigNumber.length);
//        }
//
//        int k =0;//need to check if sizes are equal.
//        int[] m = new int[this.bigNumber.length + 1];
//        for(int j = 0; j < this.bigNumber.length; j++){
//            m[j] = (this.bigNumber[j] + num.bigNumber[j] + k) % this.base;
//            k = (this.bigNumber[j] + num.bigNumber[j]) / this.base;
//        }
//        if(k == 1){
//            m[m.length - 1] = 9;
//        }else{
//            m[m.length - 1] = 0;
//        }
//        return new BigNumber(m);
//    }

//    public BigNumber add(BigNumber number){
//        int k = 0; //carry value
//        int[] sum;
//        if(bigNumber.length > number.bigNumber.length){
//            sum = new int[bigNumber.length];
//            for(int i = 0; i < number.bigNumber.length; i++){
//                sum[i] = (bigNumber[i] + number.bigNumber[i] + k) % base;
//                k = (bigNumber[i] + number.bigNumber[i]) / base;
//            }
//            for(int j = bigNumber.length - number.bigNumber.length + 1; j < bigNumber.length; j++){
//                sum[j] = (bigNumber[j] + k) % base;
//                k = (bigNumber[j] + k) / base;
//            }
//            if(k == 0){
//
//            }
//        }else if(bigNumber.length < number.bigNumber.length){
//
//        }else{
//
//        }
//    }

    public void printBigNumber(){
        if(negative){
            System.out.print('-');
        }
        for(int i = this.bigNumber.length - 1; i >= 0; i--) {
            System.out.print(bigNumber[i]);
        }
        System.out.println();
    }

    public BigNumber negate(){
        BigNumber m = new BigNumber(bigNumber.clone());
        int b = base - 1;
        for(int i = 0; i < m.bigNumber.length; i++){
            m.bigNumber[i] = b - m.bigNumber[i];
        }
        return m.addOne();
    }

    //negate helper function.
    private BigNumber addOne(){
        int k =0;//need to check if sizes are equal.
        int[] m = new int[bigNumber.length];
        m[0] = (bigNumber[0] + 1) % base;
        k = (bigNumber[0] + 1) / base;
        for(int j = 1; j < bigNumber.length; j++){
            m[j] = (bigNumber[j] + k) % base;
            k = (bigNumber[j] + 1) / base;
        }
        return new BigNumber(m);
    }

    //Compliment Arrays are little Endian
    private int[] convertStringToComplimentArray(String in) throws IllegalArgumentException{
        if(in.length() == 0) throw new IllegalArgumentException("String length cannot be zero");

        int[] compliment;
        //check to see if input is negative
        if(in.charAt(0) == '-'){
            negative = true;
            compliment = new int[in.length() - 1];
            for(int i = 1; i < in.length(); i++){
                try{
                    compliment[i] = Integer.parseInt(String.valueOf(in.charAt(i)));
                }catch(Exception e){
                    throw new IllegalArgumentException();
                }
            }
        }else if(Character.isDigit(in.charAt(0))){
            compliment = new int[in.length()];
            for(int i = 0; i < in.length(); i++){
                try{
                    compliment[i] = Integer.parseInt(String.valueOf(in.charAt(i)));
                }catch(Exception e){
                    throw new IllegalArgumentException();
                }
            }
        }else{
            throw new IllegalArgumentException();
        }

        //reversing array so least significant digit is first.
        for(int i = 0; i < compliment.length / 2; i++){
            int temp = compliment[i];
            compliment[i] = compliment[compliment.length - i - 1];
            compliment[compliment.length - i - 1] = temp;
        }

        return compliment;
    }

    public int[] getBigNumber() {
        return bigNumber;
    }

    public void setBigNumber(int[] bigNumber) {
        this.bigNumber = bigNumber;
    }



//    public BigNumber subtract(BigNumber number){
//        BigNumber negatedNumber = number.negate();
//        return add(negatedNumber);
//    }


}

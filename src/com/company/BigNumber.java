package com.company;


import org.jetbrains.annotations.NotNull;

public class BigNumber {

    private int[] number;
    private static int base = 10;
    private boolean negative;//sign is true when positive, false when negative

    public BigNumber(String baseTenNumber)throws IllegalArgumentException{
        number = processInput(baseTenNumber);
        normalize();
    }

    public BigNumber(String baseTenNumber, boolean negative)throws IllegalArgumentException{
        number = processInput(baseTenNumber);
        this.negative = negative;
        normalize();
    }

    private BigNumber(int[] number, boolean negative){
        this.number = number;
        this.negative = negative;
        normalize();
    }

    //helper function for constructor.
    //takes string input and returns an array of int.
    //checks that string is a signed decimal number. throws IllegalArgumentException otherwise.
    private int[] processInput(@NotNull String input)throws IllegalArgumentException{
        if(input.length() == 0) throw new IllegalArgumentException("String length cannot be zero");
        int[] number; //array to be returned.
        //check to see if input is negative
        if(input.charAt(0) == '-'){
            negative = true; //if negative set sign to false
            number = new int[input.length() - 1];
            for(int i = 1; i < input.length(); i++){
                try{//attempt to parse input string values into number array.
                    number[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
                }catch(Exception e){
                    throw new IllegalArgumentException("String contains invalid character");
                }
            }
        }else if(Character.isDigit(input.charAt(0))){
            negative = false; //no negative sign detected so sign is set to positive.
            number = new int[input.length()];
            for(int i = 0; i < input.length(); i++){
                try{//attempt to parse input string values into number array.
                    number[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
                }catch(Exception e){
                    throw new IllegalArgumentException("String contains invalid character");
                }
            }
        }else{//Input contains invalid characters.
            throw new IllegalArgumentException("String contains invalid character");
        }

        //reversing array so least significant digit is first.
        //this allows the array to be traversed forward when adding.
        for(int i = 0; i < number.length / 2; i++){
            int temp = number[i];
            number[i] = number[number.length - i - 1];
            number[number.length - i - 1] = temp;
        }

        return number;
    }

    @Override
    public String toString(){
        StringBuilder str;
        if(negative){
            str = new StringBuilder(number.length + 1);
            str.append('-');
        }else{
            str = new StringBuilder(number.length);
        }
        for(int i = number.length - 1; i > 0; i--){
            str.append(number[i]);
        }
        return str.toString();
    }

    public BigNumber add(@NotNull BigNumber bigNumber){
        int[] operand1, operand2; //the two things being added.
        //first thing to do:
        //make arrays equal length.



    }

    //assumes operands are same length
    private int[] add(int[] operand1, int[] operand2){
        int k = 0; //k is the carry digit.
        int[] sum = new int[operand1.length + 1];

        for(int i = 0; i < operand1.length; i++){
            sum[i] = (operand1[i] + operand2[i] + k) % base;
            k = (operand1[i] + operand2[i] + k) / base;
        }
        sum[sum.length - 1] = k;

        return sum;
    }

    //assume operand1 >= operand2
    private int[] subtract(int[] operand1, int[] operand2){
        int k = 0;
        int[] sum = new int[operand1.length];

        for(int i = 0; i < operand1.length; i++){
            sum[i] = (operand1[i] - operand2[i] + k) % base;
            k = (operand1[i] - operand2[i] + k) / base;
        }

        return sum;
    }

    //if parameter 1 is less than parameter 2 return -1.
    //if equal return 0.
    //if parameter 1 is greater than parameter 2 return 1.
    //assumes numbers are same length.
    private int findGreaterNumber(int[] p1, int[] p2){
        for(int i = p1.length - 1; i >= 0; i--){
            if(p1[i] > p2[i]){
                return 1;//p1 is greater than p2
            }else if(p1[i] < p2[i]){
                return -1;//p1 is < p2
            }else{
                continue;
            }
        }
        return 0;//numbers are equal
    }

    private void normalize(){
        int counter = 0;
        for(int i = number.length - 1; i >=0; i--) {
            if (number[i] == 0) {
                counter++;
            } else {
                counter = 0;
            }
        }
        if(counter == 0){//array is already normalized.
            return;
        }else{
            int[] temp = new int[number.length - counter];
            for(int i = 0; i < temp.length; i++){//copy only relevant values from number.
                temp[i] = number[i];
            }
            number = temp;
        }
    }

    //assumes number is less than toSize
    private int[] normalize(int[] number, int toSize){
        int[] temp = new int[toSize];
        for(int i = 0; i < number.length; i++){
            temp[i] = number[i];
        }//remaining indexes in temp are zero by default.
        return temp;
    }
}

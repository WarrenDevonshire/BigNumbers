package com.company;


import org.jetbrains.annotations.NotNull;

public class BigNumber {

    private int[] number;
    private static int base = 10;
    private boolean negative;//sign is true when positive, false when negative

    public BigNumber(String baseTenNumber)throws IllegalArgumentException{
        number = processInput(baseTenNumber);
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
}

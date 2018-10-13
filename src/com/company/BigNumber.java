package com.company;

import org.jetbrains.annotations.NotNull;

public class BigNumber {

    private int[] magnitude;
    private static int base = 10;
    private boolean negative;//sign is true when positive, false when negative

    /**
     *
     * @param baseTenNumber
     * @throws IllegalArgumentException
     */
    public BigNumber(String baseTenNumber)throws IllegalArgumentException{
        magnitude = processInput(baseTenNumber);
        normalize();
    }

    /**
     *
     * @param baseTenNumber
     * @param negative
     * @throws IllegalArgumentException
     */
    public BigNumber(String baseTenNumber, boolean negative)throws IllegalArgumentException{
        magnitude = processInput(baseTenNumber);
        this.negative = negative;
        normalize();
    }

    /**
     *
     * @param magnitude
     * @param negative
     */
    private BigNumber(int[] magnitude, boolean negative){
        this.magnitude = magnitude;
        this.negative = negative;
        normalize();
    }

    //helper function for constructor.
    //takes string input and returns an array of int.
    //checks that string is a signed decimal number. throws IllegalArgumentException otherwise.
    /**
     *
     * @param input
     * @return
     * @throws IllegalArgumentException
     */
    private int[] processInput(@NotNull String input)throws IllegalArgumentException{
        if(input.length() == 0) throw new IllegalArgumentException("String length cannot be zero");
        int[] magnitude; //array to be returned.
        //check to see if input is negative
        if(input.charAt(0) == '-'){
            negative = true; //if negative set sign to false
            magnitude = new int[input.length() - 1];
            for(int i = 1; i < input.length(); i++){
                try{//attempt to parse input string values into number array.
                    magnitude[i - 1] = Integer.parseInt(String.valueOf(input.charAt(i)));
                }catch(Exception e){
                    throw new IllegalArgumentException("String contains invalid character");
                }
            }
        }else if(Character.isDigit(input.charAt(0))){
            negative = false; //no negative sign detected so sign is set to positive.
            magnitude = new int[input.length()];
            for(int i = 0; i < input.length(); i++){
                try{//attempt to parse input string values into number array.
                    magnitude[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
                }catch(Exception e){
                    throw new IllegalArgumentException("String contains invalid character");
                }
            }
        }else{//Input contains invalid characters.
            throw new IllegalArgumentException("String contains invalid character");
        }

        //reversing array so least significant digit is first.
        //this allows the array to be traversed forward when adding.
        for(int i = 0; i < magnitude.length / 2; i++){
            int temp = magnitude[i];
            magnitude[i] = magnitude[magnitude.length - i - 1];
            magnitude[magnitude.length - i - 1] = temp;
        }

        return magnitude;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        StringBuilder str;
        if(negative){
            str = new StringBuilder(magnitude.length + 1);
            str.append('-');
        }else{
            str = new StringBuilder(magnitude.length);
        }
        for(int i = magnitude.length - 1; i >= 0; i--){
            str.append(magnitude[i]);
        }
        return str.toString();
    }

    /**
     *
     * @param number
     * @return
     */
    public BigNumber add(@NotNull BigNumber number){
        int[] operand1 = this.magnitude; //renaming for clarity
        int[] operand2 = number.magnitude; //renaming for clarity
        //make arrays equal length
        if(operand1.length > operand2.length){
            operand2 = normalize(operand2, operand1.length);//pad operand2 with zeroes
        }else if(operand1.length < operand2.length){
            operand1 = normalize(operand1, operand2.length);//pad operand1 with zeroes
        }

        if(this.negative == number.negative) {//signs are equal so just add.
            return new BigNumber(add(operand1, operand2), this.negative);
        }

        //signs are different, so need to find greater number and use subtraction instead.
        int z = findGreaterNumber(operand1, operand2);//determining if operands are of equal length.
        if(z == 1){//operand1 is greater than operand2, so operand1 - operand2 = sum
            return new BigNumber(subtract(operand1,operand2), this.negative);//sum is the sign of the larger number
        }else if(z == -1){//operand1 is less than operand2 so, operand2 - operand1 = sum
            return new BigNumber(subtract(operand2,operand1), number.negative);//sum is the sign of the larger number
        }else{
            return new BigNumber("0", false);//numbers are opposite sign but equal magnitude
        }
    }

    //assumes operands are same length
    /**
     *
     * @param operand1
     * @param operand2
     * @return
     */
    private int[] add(@NotNull int[] operand1, @NotNull int[] operand2){
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
    /**
     *
     * @param operand1
     * @param operand2
     * @return
     */
    private int[] subtract(@NotNull int[] operand1,@NotNull int[] operand2){
        int k = 0;
        int[] sum = new int[operand1.length];

        for(int i = 0; i < operand1.length; i++){
            sum[i] = (operand1[i] - operand2[i] + k + base) % base;
            k = (operand1[i] - operand2[i] + k) < 0 ? -1 : 0;
        }

        return sum;
    }

    //if parameter 1 is less than parameter 2 return -1.
    //if equal return 0.
    //if parameter 1 is greater than parameter 2 return 1.
    //assumes numbers are same length.
    /**
     *
     * @param p1
     * @param p2
     * @return
     */
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

    /**
     *
     */
    private void normalize(){
        int counter = 0;
        for(int i = magnitude.length - 1; i > 0; i--) {
            if (magnitude[i] == 0) {
                counter++;
            } else {
                break;
            }
        }
        if(counter == 0){//array is already normalized.
            return;
        }else{
            int[] temp = new int[magnitude.length - counter];
            for(int i = 0; i < temp.length; i++){//copy only relevant values from number.
                temp[i] = magnitude[i];
            }
            magnitude = temp;
        }
    }

    //assumes number is less than toSize
    /**
     *
     * @param number
     * @param toSize
     * @return
     */
    private int[] normalize(int[] number, int toSize){
        int[] temp = new int[toSize];
        for(int i = 0; i < number.length; i++){
            temp[i] = number[i];
        }//remaining indexes in temp are zero by default.
        return temp;
    }

    //returns ~this
    /**
     *
     * @return
     */
    public BigNumber negate(){
        return new BigNumber(magnitude, !negative);
    }
}

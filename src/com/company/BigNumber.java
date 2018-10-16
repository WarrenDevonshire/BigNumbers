package com.company;

import org.jetbrains.annotations.NotNull;

/**
 * <h1>BigNumber</h1>
 * The BigNumber class implements arbitrary precision arithmetic.
 * <p>
 * Sign and magnitude and used as the representation of BigNumber.
 * Magnitude is stored as an int[]
 *
 * @author  Warren Devonshire
 */
public class BigNumber {

    //magnitudes have least significant digit first.
    private int[] magnitude;
    private static int base = 10; //Working in base 10
    //negative is true when sign is negative, false otherwise
    //might be changed to int sign, holding either -1, 0, or 1
    private boolean negative;

    /**
     * @author Warren Devonshire
     * @param baseTenNumber
     * @throws IllegalArgumentException Throws when string input is formatted incorrectly.
     */
    public BigNumber(String baseTenNumber)throws IllegalArgumentException{
        magnitude = processInput(baseTenNumber);
        normalize();
        if(magnitude.length == 1){//if magnitude is zero make sign positive.
            if(magnitude[0] == 0){
                this.negative = false;
            }
        }
    }

    /**
     * @author Warren Devonshire
     * @param baseTenNumber
     * @param negative
     * @throws IllegalArgumentException Throws when string input is formatted incorrectly
     */
    public BigNumber(String baseTenNumber, boolean negative)throws IllegalArgumentException{
        magnitude = processInput(baseTenNumber);
        this.negative = negative;
        normalize();
        if(magnitude.length == 1){
            if(magnitude[0] == 0){
                this.negative = false;
            }
        }
    }

    /**
     * @author Warren Devonshire
     * @param magnitude
     * @param negative
     */
    private BigNumber(int[] magnitude, boolean negative){
        this.magnitude = magnitude;
        this.negative = negative;
        normalize();
        if(magnitude.length == 1){
            if(magnitude[0] == 0){
                this.negative = false;
            }
        }
    }

    /**
     * Takes string input and returns an in[]. Checks that string is a signed decimal number.
     * @author Warren Devonshire
     * @param input This is a string of representing a number in base 10. may have a leading '-' sign.
     * @return int[] This returns a magnitude
     * @throws IllegalArgumentException Throws when string input is formatted incorrectly
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
     * Adds two BigNumbers and returns a new BigNumber
     * @author Warren Devonshire
     * @param number The BigNumber to be added to this.
     * @return BigNumber The sum
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


    /**
     * Takes two in[] and adds them.
     * @author Warren Devonshire
     * @param operand1 The first operand.
     * @param operand2 The second operand.
     * @return int[] The sum.
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


    /**
     * Subtracts operand2 from operand1. Assumes operand1 is bigger than operand2
     * @author Warren Devonshire
     * @param operand1 Operand to be subtracted from
     * @param operand2 Operand to subtract from operand1
     * @return int[] The result.
     */
    private int[] subtract(@NotNull int[] operand1,@NotNull int[] operand2){
        int k = 0; //the borrow
        int[] sum = new int[operand1.length];//sum will never be longer than operand1

        for(int i = 0; i < operand1.length; i++){
            sum[i] = (operand1[i] - operand2[i] + k + base) % base;
            k = (operand1[i] - operand2[i] + k) < 0 ? -1 : 0; //calculate borrow
        }

        return sum;
    }

    /**
     * The same as a compareTo method. Will be changed later.
     * @author Warren Devonshire
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
     * Removes leading zeroes in the magnitude
     * @author Warren Devonshire
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

    /**
     * Pads a magnitude with leading zeroes.
     * @author Warren Devonshire
     * @param number The int[] to be padded.
     * @param toSize The size padded to.
     * @return
     */
    private int[] normalize(int[] number, int toSize){
        int[] temp = new int[toSize];
        for(int i = 0; i < number.length; i++){
            temp[i] = number[i];
        }//remaining indexes in temp are zero by default.
        return temp;
    }

    /**
     * @author Warren Devonshire
     * @return Returns new negated BigNumber with same magnitude as self.
     */
    public BigNumber negate(){
        return new BigNumber(magnitude, !negative);
    }
}

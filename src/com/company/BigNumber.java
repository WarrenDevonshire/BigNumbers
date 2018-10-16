package com.company;

import java.util.Arrays;

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
    private int[] processInput(String input)throws IllegalArgumentException{
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
    public BigNumber add(BigNumber number){
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
    private int[] add( int[] operand1,  int[] operand2){
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
<<<<<<< HEAD
    private int[] subtract(int[] operand1, int[] operand2){
=======
    private int[] subtract(@NotNull int[] operand1,@NotNull int[] operand2){
>>>>>>> master
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



    /**
     * A function to check the invoker for equality against a second BigNumber.
     * @param number the BigNumber to be checked for equality against the invoker
     * @return a boolean value representing whether or not the invoker and argument are equal
     * @author Daniel Haluszka
     */
    public boolean equals(BigNumber number) {

        if (this.compareTo(number) == 0) {

            return true;

        } else {

            return false;

        }

    }

    /**
     * A function to quantitatively compare the invoker to a second BigNumber.
     * @param number the BigNumber to compare the invoker to
     * @return -1 if argument is smaller than the invoker, 0 if the argument is equal to the invoker, 1 if the argument is larger than the invoker
     * @author Daniel Haluszka
     */
    public int compareTo(BigNumber number) {

        //TODO: replace findGreaterNumber with compareTo

        //if the signs are not the same
        if (this.negative != number.negative) {

            //if the invoker is negative, then the argument must be positive, return 1
            if (this.negative == true) {

                return 1;

            } else {

                //else invoker is positive and argument must be negative, return -1
                return -1;

            }

        }

        //otherwise the signs are the same, check length of operands

        //rename for clarity
        int[] operand1 = this.magnitude;
        int[] operand2 = number.magnitude;

        if (operand1.length > operand2.length) {

            if (this.negative == true) {

                //signs are negative so operand1 must be smaller
                return 1;

            } else {

                //else signs must be positive so operand1 must be larger
                return -1;

            }

        } else if (operand1.length < operand2.length) {

            if (this.negative == true) {

                //signs are negative so operand1 must be larger
                return -1;

            } else {

                //else signs must be positive so operand1 must be smaller
                return 1;

            }

        }

        //if the signs and lengths are the same, check the digits in the magnitude
        for (int i = operand1.length - 1; i >= 0; i--) {

            if (operand1[i] > operand2[i]) {

                if (this.negative == true) {

                    //signs are negative so operand1 must be smaller
                    return 1;

                } else {

                    //sign must be positive so operand1 must be larger
                    return -1;

                }

            } else if (operand1[i] < operand2[i]) {

                if (this.negative == true) {

                    //signs are negative so operand1 must be larger
                    return -1;

                } else {

                    //sign must be positive so operand1 must be smaller
                    return 1;

                }

            }

        }

        //if signs, lengths, and digits are all the same, the invoker and argument are equal
        return 0;

    }

    /**
     * A function to return the sign of the invoker.
     * @return -1 if sign of invoker is negative, 1 if sign of invoker is positive
     * @author Daniel Haluszka
     */
    public int sign() {

        if (this.negative == true) {

            return -1;

        } else {

            return 1;

        }

    }

    /**
     * A function to multiply the invoker by a second BigNumber.
     * @param factor the BigNumber to multiply the invoker by
     * @return the product of the invoker and argument
     * @author Daniel Haluszka
     */
    public BigNumber multiply(BigNumber factor) {

        BigNumber temp = new BigNumber("0");

        //if multiplying by 0, return 0
        if (factor.equals(temp)) {

            return temp;

        }

        temp.magnitude[0] = 1;

        //if multiplying by 1, return invoker
        if (factor.equals(temp)) {

            return this;

        }


        temp.magnitude[0] = 0;

        //create new array of ints to act as iterator in loop
        int[] iter = new int[factor.magnitude.length];

        //finally, perform multiplication using given values by method of repeated addition
        //loop will end once value in iter is equal to that in magnitude of factor, meaning that the loop has run the factor of times equal to the magnitude of factor
        while (!Arrays.equals(iter, factor.magnitude)) {

            temp.add(this);

            increment(iter);

        }

        //check if negative sign is needed base on signs of operands
        if(this.negative == true && factor.negative == false || this.negative == false && factor.negative == true) {

            temp.negative = true;

        }

        return temp;

    }

    /**
     * A function to divide the invoker by a second BigNumber.
     * @param divisor the BigNumber to divide the invoker by
     * @return a pair containing the quotient of the division of the invoker and argument in the first position, and the remainder in the second position.
     * @throws IllegalArgumentException
     * @author Daniel Haluszka
     */
    public BigNumberPair divide(BigNumber divisor) throws IllegalArgumentException {

        //TODO: STILL NEED TO HANDLE DIVIDING BY A NEGATIVE NUMBER

        BigNumberPair result = new BigNumberPair();
        BigNumber temp = new BigNumber("0", false);

        //check for dividing by 0
        if (divisor.equals(temp)) {

            throw new IllegalArgumentException("Divisor cannot be 0");

        }

        temp.magnitude[0] = 1;

        //if dividing by 1, return invoker (this does not check for leading 0's)
        if (divisor.equals(temp)) {

            //return invoker as quotient, second value in pair (remainder) is initialized to 0
            result.setFirst(this);
            return result;

        }

        //calculate quotient and remainder by method of repeated addition
        //create new non-negative BigNumber with magnitude of temp to compare workingTemp against in case invoker is negative
        BigNumber refTemp = new BigNumber(this.magnitude, false);
        BigNumber workingTemp = new BigNumber("0", false);
        BigNumber remainder = new BigNumber("0", false);
        int[] counter = new int[divisor.magnitude.length];
        boolean loop = true;

        //this loop will add the magnitude of divisor to workingTemp until it goes over the magnitude of the invoker, at which point it will subtract the magnitude of divisor and calculate the remainder
        while (loop == true) {

            //if divisor can be added to magnitude of workingTemp without going over magnitude of divisor, we don't yet need to calculate remainder
            workingTemp.add(divisor);
            increment(counter);

            if (workingTemp.compareTo(refTemp) == 0) {

                //if the magnitude of workingTemp is equal to the magnitude of the invoker, there is no remainder
                //decrement counter to remove extra addition
                decrement(counter);

            } else if (workingTemp.compareTo(refTemp) == -1) {

                //if the magnitude of working temp goes over the magnitude of the invoker, calculate remainder
                //subtract the magnitude of divisor again to be used in calculating remainder
                workingTemp.add(divisor);

                remainder.magnitude = subtract(this.magnitude, workingTemp.magnitude);

                //decrement counter to remove extra addition
                decrement(counter);

            }
        }

        //update magnitude of temp with value of counter in magnitude and return as quotient, return remainder as second value in pair
        temp.magnitude = counter;

        result.setFirst(temp);
        result.setSecond(remainder);
        return result;

    }

    /**
     * A function to find two factors of the invoker.
     * @return a pair containing the two factors of the invoker in descending order
     * @author Daniel Haluszka
     */
    public BigNumberPair factor() {

        BigNumberPair result = new BigNumberPair();
        BigNumber fact1 = new BigNumber("0", false);
        BigNumber fact2 = new BigNumber("2", false);

        //check if even
        if(this.magnitude[0] % 2 == 0) {

            //if even, divide by 2
            //divide will return first factor in first pair value, 0 in second because there will be no remainder
            result = this.divide(fact2);
            //update second pair value to 2
            result.setSecond(fact2);
            return result;

        } else {

            //use Knuth algorithm if odd

        }

        return result;

    }

    /**
     * A function to increment an array of ints that is being used to represent a large factor.
     * @param arr the array of ints representing a large factor to be incremented by one
     * @author Daniel Haluszka
     */
    public void increment(int[] arr) {

        for (int i = 0; i < arr.length; i++) {

            arr[i]++;

            if (arr[i] < 10) {

                break;

            } else {

                arr[i] = 0;

            }

        }

    }

    /**
     * A function to decrement an array of ints that is being used to represent a large number.
     * @param arr the array of ints representing a large number to be decremented by one
     * @author Daniel Haluszka
     */
    public void decrement(int[] arr) {

        for (int i = 0; i < arr.length; i++) {

            arr[i]--;

            if (arr[i] >= 0) {

                break;

            } else {

                arr[i] = 9;

            }

        }

    }

}
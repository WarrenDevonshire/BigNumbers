package com.company;

import java.util.Arrays;

public class BigNumber {

    private int[] magnitude;
    private static int base = 10;
    private boolean negative;//sign is true when positive, false when negative

    /**
     * @param baseTenNumber
     * @throws IllegalArgumentException
     */
    public BigNumber(String baseTenNumber) throws IllegalArgumentException {
        magnitude = processInput(baseTenNumber);
        normalize();
    }

    /**
     * @param baseTenNumber
     * @param negative
     * @throws IllegalArgumentException
     */
    public BigNumber(String baseTenNumber, boolean negative) throws IllegalArgumentException {
        magnitude = processInput(baseTenNumber);
        this.negative = negative;
        normalize();
    }

    /**
     * @param magnitude
     * @param negative
     */
    private BigNumber(int[] magnitude, boolean negative) {
        this.magnitude = magnitude;
        this.negative = negative;
        normalize();
    }

    //helper function for constructor.
    //takes string input and returns an array of int.
    //checks that string is a signed decimal number. throws IllegalArgumentException otherwise.

    /**
     * @param input
     * @return
     * @throws IllegalArgumentException
     */
    private int[] processInput(String input) throws IllegalArgumentException {
        if (input.length() == 0) throw new IllegalArgumentException("String length cannot be zero");
        int[] magnitude; //array to be returned.
        //check to see if input is negative
        if (input.charAt(0) == '-') {
            negative = true; //if negative set sign to false
            magnitude = new int[input.length() - 1];
            for (int i = 1; i < input.length(); i++) {
                try {//attempt to parse input string values into number array.
                    magnitude[i - 1] = Integer.parseInt(String.valueOf(input.charAt(i)));
                } catch (Exception e) {
                    throw new IllegalArgumentException("String contains invalid character");
                }
            }
        } else if (Character.isDigit(input.charAt(0))) {
            negative = false; //no negative sign detected so sign is set to positive.
            magnitude = new int[input.length()];
            for (int i = 0; i < input.length(); i++) {
                try {//attempt to parse input string values into number array.
                    magnitude[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
                } catch (Exception e) {
                    throw new IllegalArgumentException("String contains invalid character");
                }
            }
        } else {//Input contains invalid characters.
            throw new IllegalArgumentException("String contains invalid character");
        }

        //reversing array so least significant digit is first.
        //this allows the array to be traversed forward when adding.
        for (int i = 0; i < magnitude.length / 2; i++) {
            int temp = magnitude[i];
            magnitude[i] = magnitude[magnitude.length - i - 1];
            magnitude[magnitude.length - i - 1] = temp;
        }

        return magnitude;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        StringBuilder str;
        if (negative) {
            str = new StringBuilder(magnitude.length + 1);
            str.append('-');
        } else {
            str = new StringBuilder(magnitude.length);
        }
        for (int i = magnitude.length - 1; i >= 0; i--) {
            str.append(magnitude[i]);
        }
        return str.toString();
    }

    /**
     * @param number
     * @return
     */
    public BigNumber add(BigNumber number) {
        int[] operand1 = this.magnitude; //renaming for clarity
        int[] operand2 = number.magnitude; //renaming for clarity
        //make arrays equal length
        if (operand1.length > operand2.length) {
            operand2 = normalize(operand2, operand1.length);//pad operand2 with zeroes
        } else if (operand1.length < operand2.length) {
            operand1 = normalize(operand1, operand2.length);//pad operand1 with zeroes
        }

        if (this.negative == number.negative) {//signs are equal so just add.
            return new BigNumber(add(operand1, operand2), this.negative);
        }

        //signs are different, so need to find greater number and use subtraction instead.
        int z = findGreaterNumber(operand1, operand2);//determining if operands are of equal length.
        if (z == 1) {//operand1 is greater than operand2, so operand1 - operand2 = sum
            return new BigNumber(subtract(operand1, operand2), this.negative);//sum is the sign of the larger number
        } else if (z == -1) {//operand1 is less than operand2 so, operand2 - operand1 = sum
            return new BigNumber(subtract(operand2, operand1), number.negative);//sum is the sign of the larger number
        } else {
            return new BigNumber("0", false);//numbers are opposite sign but equal magnitude
        }
    }

    //assumes operands are same length

    /**
     * @param operand1
     * @param operand2
     * @return
     */
    private int[] add(int[] operand1, int[] operand2) {
        int k = 0; //k is the carry digit.
        int[] sum = new int[operand1.length + 1];

        for (int i = 0; i < operand1.length; i++) {
            sum[i] = (operand1[i] + operand2[i] + k) % base;
            k = (operand1[i] + operand2[i] + k) / base;
        }
        sum[sum.length - 1] = k;

        return sum;
    }

    //assume operand1 >= operand2

    /**
     * @param operand1
     * @param operand2
     * @return
     */
    private int[] subtract(int[] operand1, int[] operand2) {
        int k = 0;
        int[] sum = new int[operand1.length];

        for (int i = 0; i < operand1.length; i++) {
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
     * @param p1
     * @param p2
     * @return
     */
    private int findGreaterNumber(int[] p1, int[] p2) {
        for (int i = p1.length - 1; i >= 0; i--) {
            if (p1[i] > p2[i]) {
                return 1;//p1 is greater than p2
            } else if (p1[i] < p2[i]) {
                return -1;//p1 is < p2
            } else {
                continue;
            }
        }
        return 0;//numbers are equal
    }

    /**
     *
     */
    private void normalize() {
        int counter = 0;
        for (int i = magnitude.length - 1; i > 0; i--) {
            if (magnitude[i] == 0) {
                counter++;
            } else {
                break;
            }
        }
        if (counter == 0) {//array is already normalized.
            return;
        } else {
            int[] temp = new int[magnitude.length - counter];
            for (int i = 0; i < temp.length; i++) {//copy only relevant values from number.
                temp[i] = magnitude[i];
            }
            magnitude = temp;
        }
    }

    //assumes number is less than toSize

    /**
     * @param number
     * @param toSize
     * @return
     */
    private int[] normalize(int[] number, int toSize) {
        int[] temp = new int[toSize];
        for (int i = 0; i < number.length; i++) {
            temp[i] = number[i];
        }//remaining indexes in temp are zero by default.
        return temp;
    }

    //returns ~this

    /**
     * @return
     */
    public BigNumber negate() {
        return new BigNumber(magnitude, !negative);
    }


    /**
     * @param number the BigNumber to be checked for equality against the invoker
     * @return a boolean value representing whether or not the invoker and argument are equal
     * @author Daniel Haluszka
     */
    public boolean equals(BigNumber number) {

        //check that the signs are the same before doing any more work
        if (this.negative != number.negative) {

            return false;

        }

        //rename for clarity
        int[] operand1 = this.magnitude;
        int[] operand2 = number.magnitude;

        /**
         * Should put this padding check block in a function
         */
        //if the two BigNumbers being compared are not the same size, pad the shorter of the two
        if (operand1.length != operand2.length) {

            //pad operand2
            if (operand1.length > operand2.length) {

                operand2 = normalize(operand2, operand1.length);

            } else { //pad operand1

                operand1 = normalize(operand1, operand2.length);

            }

        }

        //we now know that operands are same sign and length, check if values in magnitude are equal
        for (int i = operand1.length - 1; i >= 0; i--) {

            if (operand1[i] != operand2[i]) {

                //if any value in magnitude is not equal, BigNumbers are not equal
                return false;

            }

        }

        return true;

    }

    /**
     * @param number the BigNumber to compare the invoker to
     * @return -1 if argument is smaller than the invoker, 0 if the argument is equal to the invoker, 1 if the argument is larger than the invoker
     * @author Daniel Haluszka
     */
    public int compareTo(BigNumber number) { //maybe this should replace the findGreaterNumber?

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

        //otherwise the signs are the same, check values

        //rename for clarity
        int[] operand1 = this.magnitude;
        int[] operand2 = number.magnitude;

        //if the two BigNumbers being compared are not the same size, pad the shorter of the two
        if (operand1.length != operand2.length) {

            //pad operand2
            if (operand1.length > operand2.length) {

                operand2 = normalize(operand2, operand1.length);

            } else { //pad operand1

                operand1 = normalize(operand1, operand2.length);

            }

        }

        //we now know that operands are same sign and length, compare values of magnitudes

        //this variable will indicate whether or not we're still looking at leading zeros, will turn to false once we see any other value
        boolean leadingZeros = true;

        for (int i = operand1.length - 1; i >= 0; i--) {

            //if still reading leading zeros and both are 0, move to next digit in magnitude
            if (leadingZeros == true && operand1[i] == 0 && operand2[i] == 0) {

                continue;

            }

            if (leadingZeros == true && operand1[i] == 0 && operand2[i] != 0) {

                //if we see a value other than leading zeros from operand2 first and the signs are negative, operand2 is smaller
                if (this.negative == true) {

                    return -1;

                } else {

                    //else the signs must be positive and operand2 is larger
                    return 1;

                }

            } else if (leadingZeros == true && operand1[i] != 0 && operand2[i] == 0) {

                //if we see a value other than leading zeros from operand1 first and the signs are negative, operand1 is smaller
                if (this.negative == true) {

                    return 1;

                } else {

                    //else the signs must be positive and operand1 is larger
                    return -1;

                }

            } else if (leadingZeros == true && operand1[i] != 0 && operand2[i] != 0) {

                //if both magnitudes are the same number of digits long

                //if current digit of magnitudes are equal, move to next digit
                if (operand1[i] == operand2[i]) {

                    continue;

                }

                //if the current digit of operand1 is greater than current digit of operand2
                if (operand1[i] > operand2[i]) {

                    //if the signs are negative, operand1 is smaller, return 1
                    if (this.negative == true) {

                        return 1;

                    } else {

                        //else the signs must be positive and operand1 is larger, return -1
                        return -1;

                    }

                //if the current digit of operand2 is greater than the current digit of operand1
                } else if (operand1[i] < operand2[i]) {

                    //if the signs are negative, operand2 is smaller, return 1
                    if (this.negative == true) {

                        return -1;

                    } else {

                        //else the signs must be positive and operand2 is larger, return -1
                        return 1;

                    }

                }

            }

        }

        return 0;

    }

    /**
     * @return -1 if sign of invoker is negative, 1 if sign of invoker is positive
     * @author Daniel Haluszka
     */
    public int sign() {

        int sign = 0;

        if (this.negative == true) {

            return -1;

        } else {

            return 1;

        }

    }

    /**
     * @param number the BigNumber to multiply the invoker by
     * @return the product of the invoker and argument
     * @author Daniel Haluszka
     */
    public BigNumber multiply(BigNumber number) {

        BigNumber temp = new BigNumber("0");

        //if multiplying by 0, return 0
        if (number.equals(temp)) {

            return temp;

        }

        temp.magnitude[0] = 1;

        //if multiplying by 1, return invoker (this doesn't check for leading 0's)
        if (number.equals(temp)) {

            return this;

        }

        temp.magnitude[0] = 0;

        //rename for clarity
        int[] operand1 = this.magnitude;
        int[] operand2 = number.magnitude;

        //if the two BigNumbers being compared are not the same size, pad the shorter of the two
        //doing this first allows us to use one loop to get through both operands in the next step as we know that they will be the same length
        if (operand1.length != operand2.length) {

            //pad operand2
            if (operand1.length > operand2.length) {

                operand2 = normalize(operand2, operand1.length);

            } else { //pad operand1

                operand1 = normalize(operand1, operand2.length);

            }

        }

        /**
         * MIGHT NOT NEED TO CALCULATE NUMBER OF DIGITS IN PRODUCT, ADD MIGHT TAKE CARE OF THIS AUTOMATICALLY
         */
        //find first significant digit in magnitude of invoker and argument in case of leading zeros
        int sigDig1 = 0;
        int sigDig2 = 0;

        for (int i = operand1.length - 1; i >= 0; i--) {

            if (i == (operand1.length - 1) && operand1[i] != 0) {

                //if the first digit is not a zero, there are no leading zeros
                sigDig1 = i;

            } else if (operand1[i] != 0) {

                //otherwise note first significant digit after leading zeros
                sigDig1 = i;

            }

            if (i == (operand1.length - 1) && operand2[i] != 0) {

                //if the first digit is not a zero, there are no leading zeros
                sigDig2 = i;

            } else if (operand2[i] != 0) {

                //otherwise note first significant digit after leading zeros
                sigDig2 = i;

            }

        }

        //calculate number of digits needed for product and assign new magnitude array to temp BigNumber
        int sizeOfProduct = (int) (Math.floor(Math.log(sigDig1) + Math.log(sigDig2)));
        int tempMagnitude[] = new int[sizeOfProduct];
        temp.magnitude = tempMagnitude;

        //convert magnitude of argument to String for use in last step
        String numberToString = number.toString();

        //remove negative sign if necessary
        if (number.negative = true) {

            numberToString = numberToString.substring(numberToString.length() - 1);

        }

        //finally, perform multiplication using given values by method of repeated addition
        for (int i = 0; i < Integer.parseInt(numberToString); i++) {

            temp.add(this);

        }

        //check if negative sign is needed base on signs of operands
        if(this.negative == true && number.negative == false || this.negative == false && number.negative == true) {

            temp.negative = true;

        }

        return temp;

    }

    /**
     * @param number the BigNumber to divide the invoker by
     * @return the quotient of the division of the invoker and argument
     * @throws IllegalArgumentException
     * @author Daniel Haluszka
     */
    public BigNumber divide(BigNumber number) throws IllegalArgumentException {

        BigNumber test = new BigNumber("0");

        //check for dividing by 0
        if (number.equals(test)) {

            throw new IllegalArgumentException("Divisor cannot be 0");

        }

        test.magnitude[0] = 1;

        //if dividing by 1, return invoker (this does not check for leading 0's)
        if (number.equals(test)) {

            return this;

        }

        //rename for clarity
        int[] operand1 = this.magnitude;
        int[] operand2 = number.magnitude;

        //if the two BigNumbers being compared are not the same size, pad the shorter of the two (this should be the divisor but may not due to leading 0's)
        if (operand1.length != operand2.length) {

            //pad operand2
            if (operand1.length > operand2.length) {

                operand2 = normalize(operand2, operand1.length);

            } else { //pad operand1

                operand1 = normalize(operand1, operand2.length);

            }

        }

        //calculate quotient and remainder by method of repeated subtraction
        BigNumber workingTemp = new BigNumber(this.magnitude, this.negative);
        int remainder;

        //this loop will subtract the value of the argument each time and subtract it from the magnitude of workingTemp until it needs to calculate remainder,
        //at which point it will set the magnitude of workingTemp = 0 to indicate that the operation is complete
        int counter = 0;

        while (!workingTemp.magnitude.equals(0)) {

            //if number can be subtracted from magnitude of workingTemp without going negative, we don't yet need to calculate remainder
            workingTemp.subtract(number); //why is there no subtract given argument of type BigNumber?
            counter++;

            if (workingTemp.negative == true) {

                //if the magnitude has gone negative, it's time to calculate remainder

                //readd the value of number again to be used in calculating remainder
                workingTemp.add(number);

                //still need to calculate remainder
                remainder = 0;

                //set magnitude = 0
                int[] tempMagnitude = new int[1];
                tempMagnitude[0] = 0;
                workingTemp.magnitude = tempMagnitude;

                counter--;

            }

        }

        //create new BigNumber with value of counter in magnitude and return as quotient, throw out remainder for now
        BigNumber temp = new BigNumber(Integer.toString(counter));

        return temp;

    }

    /**
     * @param number
     * @return
     * @author Daniel Haluszka
     */
    public BigNumber factor(BigNumber number) {

        return number;

    }

}
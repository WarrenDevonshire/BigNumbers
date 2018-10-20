package com.company;

import java.util.Arrays;

/**
 * <h1>BigNumber</h1>
 * The BigNumber class implements arbitrary precision arithmetic.
 * <p>
 * Sign and magnitude and used as the representation of BigNumber.
 * Magnitude is stored as an int[]
 *
 * @author  Warren Devonshire, Daniel Haluszka
 */
public class BigNumber {

    //magnitudes have least significant digit first.
    private int[] magnitude;
    private static int base = 10; //Working in base 10
    //negative is true when sign is negative, false otherwise
    //might be changed to int sign, holding either -1, 0, or 1
    private boolean negative;

    /**
     * @param baseTenNumber
     * @throws IllegalArgumentException Throws when string input is formatted incorrectly.
     * @author Warren Devonshire
     */
    public BigNumber(String baseTenNumber) throws IllegalArgumentException {
        magnitude = processInput(baseTenNumber);
        normalize();
        if (magnitude.length == 1) {//if magnitude is zero make sign positive.
            if (magnitude[0] == 0) {
                this.negative = false;
            }
        }
    }

    /**
     * @param baseTenNumber
     * @param negative
     * @throws IllegalArgumentException Throws when string input is formatted incorrectly
     * @author Warren Devonshire
     */
    public BigNumber(String baseTenNumber, boolean negative) throws IllegalArgumentException {
        magnitude = processInput(baseTenNumber);
        this.negative = negative;
        normalize();
        if (magnitude.length == 1) {
            if (magnitude[0] == 0) {
                this.negative = false;
            }
        }
    }

    /**
     * @param magnitude
     * @param negative
     * @author Warren Devonshire
     */
    private BigNumber(int[] magnitude, boolean negative) {
        this.magnitude = magnitude;
        this.negative = negative;
        normalize();
        if (magnitude.length == 1) {
            if (magnitude[0] == 0) {
                this.negative = false;
            }
        }
    }

    /**
     * Takes string input and returns an int[]. Checks that string is a signed decimal number.
     *
     * @param input This is a string of representing a number in base 10. may have a leading '-' sign.
     * @return int[] This returns a magnitude
     * @throws IllegalArgumentException Throws when string input is formatted incorrectly
     * @author Warren Devonshire
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
     * Adds two BigNumbers and returns a new BigNumber
     *
     * @param number The BigNumber to be added to this.
     * @return BigNumber The sum
     * @author Warren Devonshire
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
        int z = this.compareTo(number); //determining if operands are of equal length.
        if (z == 1) {//operand1 is greater than operand2, so operand1 - operand2 = sum
            return new BigNumber(subtract(operand1, operand2), this.negative);//sum is the sign of the larger number
        } else if (z == -1) {//operand1 is less than operand2 so, operand2 - operand1 = sum
            return new BigNumber(subtract(operand2, operand1), number.negative);//sum is the sign of the larger number
        } else {
            return new BigNumber("0", false);//numbers are opposite sign but equal magnitude
        }
    }


    /**
     * Takes two in[] and adds them.
     *
     * @param operand1 The first operand.
     * @param operand2 The second operand.
     * @return int[] The sum.
     * @author Warren Devonshire
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


    /**
     * Subtracts operand2 from operand1. Assumes operand1 is bigger than operand2
     *
     * @param operand1 Operand to be subtracted from
     * @param operand2 Operand to subtract from operand1
     * @return int[] The result.
     * @author Warren Devonshire
     */
    private int[] subtract(int[] operand1, int[] operand2) {
        int k = 0; //the borrow
        int[] sum = new int[operand1.length];//sum will never be longer than operand1

        for (int i = 0; i < operand1.length; i++) {
            sum[i] = (operand1[i] - operand2[i] + k + base) % base;
            k = (operand1[i] - operand2[i] + k) < 0 ? -1 : 0; //calculate borrow
        }

        return sum;
    }

    /**
     * Removes leading zeroes in the magnitude
     *
     * @author Warren Devonshire
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

    /**
     * Pads a magnitude with leading zeroes.
     *
     * @param number The int[] to be padded.
     * @param toSize The size padded to.
     * @return
     * @author Warren Devonshire
     */
    private int[] normalize(int[] number, int toSize) {
        int[] temp = new int[toSize];
        for (int i = 0; i < number.length; i++) {
            temp[i] = number[i];
        }//remaining indexes in temp are zero by default.
        return temp;
    }

    /**
     * @return Returns new negated BigNumber with same magnitude as self.
     * @author Warren Devonshire
     */
    public BigNumber negate() {
        return new BigNumber(magnitude, !negative);
    }


    /**
     * Checks the sign and magnitude of the invoker for equality against a second BigNumber.
     *
     * @param number the BigNumber to be checked for equality against the invoker
     * @return true if the signs and magnitudes of the invoker and argument are equal, false if not
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
     * Compares the sign and magnitude of the invoker to a second BigNumber.
     *
     * @param number the BigNumber to compare the invoker to
     * @return 1 if the invoker is larger than the argument, 0 if the invoker is equal to the argument, -1 if the invoker is smaller than the argument
     * @author Daniel Haluszka
     */
    public int compareTo(BigNumber number) {

        //if the signs are not the same
        if (this.negative != number.negative) {

            //if the invoker is negative, then the argument must be positive, return 1
            if (this.negative == true) {

                return -1;

            } else {

                //else invoker is positive and argument must be negative, return -1
                return 1;

            }

        }

        //otherwise the signs are the same, check length of operands

        //rename for clarity
        int[] operand1 = this.magnitude;
        int[] operand2 = number.magnitude;

        if (operand1.length > operand2.length) {

            if (this.negative == true) {

                //signs are negative so operand1 must be smaller
                return -1;

            } else {

                //else signs must be positive so operand1 must be larger
                return 1;

            }

        } else if (operand1.length < operand2.length) {

            if (this.negative == true) {

                //signs are negative so operand1 must be larger
                return 1;

            } else {

                //else signs must be positive so operand1 must be smaller
                return -1;

            }

        }

        //if the signs and lengths are the same, check the digits in the magnitude
        for (int i = operand1.length - 1; i >= 0; i--) {

            if (operand1[i] > operand2[i]) {

                if (this.negative == true) {

                    //signs are negative so operand1 must be smaller
                    return -1;

                } else {

                    //sign must be positive so operand1 must be larger
                    return 1;

                }

            } else if (operand1[i] < operand2[i]) {

                if (this.negative == true) {

                    //signs are negative so operand1 must be larger
                    return 1;

                } else {

                    //sign must be positive so operand1 must be smaller
                    return -1;

                }

            }

        }

        //if signs, lengths, and digits are all the same, the invoker and argument are equal
        return 0;

    }

    /**
     * Returns the sign of the invoker.
     *
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
     * Multiplies the invoker by a second BigNumber using 'Algorithm M.'
     *
     * @param factor the BigNumber to multiply the invoker by
     * @return the product of the invoker and argument
     * @author Algorithm by Donald Knuth, implementation by Daniel Haluszka
     */
    public BigNumber multiply(BigNumber factor) {

        //TODO: rename variables to something more readable, maybe add checks for 0 or 1 multiplier back in before Knuth code?

        BigNumber result = new BigNumber("0", false);
        BigNumber zeroBn = new BigNumber("0", false);
        int[] w = new int[this.magnitude.length + factor.magnitude.length];
        int[] u = new int[this.magnitude.length];
        int[] v = new int[factor.magnitude.length];
        u = this.magnitude;
        v = factor.magnitude;
        int t = 0;
        int k = 0; //carry
        int m = u.length;
        int n = v.length;

        //step M1
        for (int j = 0; j < n; j++) {

            //check for zero multiplier, step M2
            if (v[j] == 0) {

                w[j + m] = 0;

            } else {

                //step M3
                k = 0;

                for (int i = 0; i < m; i++) {

                    //step M4
                    t = ((u[i] * v[j]) + w[i + j] + k);
                    w[i + j] = t % base;
                    k = t / base;

                }

                //step M5
                w[j + m] = k;

            }

        }

        System.out.print("\n");
        result.magnitude = w;
        result.normalize();

        //check if negative sign is needed base on signs of operands
        if (this.negative == true && factor.negative == false && result.equals(zeroBn) == false || this.negative == false && factor.negative == true && result.equals(zeroBn) == false) {

            result.negative = true;

        }

        return result;

    }

    /**
     * Divides the invoker by a second BigNumber using 'Algorithm D.'
     *
     * @param divisor the BigNumber to divide the invoker by
     * @return a pair containing the quotient of the division of the invoker and argument in the first position, and the remainder in the second position.
     * @throws IllegalArgumentException throws if dividing by 0
     * @author Algorithm by Donald Knuth, implementation by Daniel Haluszka
     */
    public BigNumberPair divide(BigNumber divisor) throws IllegalArgumentException {

        //TODO: rename variables to something more readable, maybe add check for 1 divisor back in?

        int[] zeroArr = new int[1];

        //check for dividing by 0
        if (divisor.magnitude == zeroArr) {

            throw new IllegalArgumentException("Divisor cannot be 0");

        }

        int[] u = new int[this.magnitude.length];
        int[] v = new int[divisor.magnitude.length];
        v = divisor.magnitude;
        BigNumber quotient = new BigNumber("0", false);
        BigNumber remainder = new BigNumber("0", false);
        int qHat = 0;
        int rHat = 0;
        BigNumberPair result = new BigNumberPair();
        int d = 0;

        //step D1
        d = (int) Math.floor((base - 1) / v[v.length - 1]);
        System.out.println(d);
        BigNumber dBn = new BigNumber(Integer.toString(d), false);
        u = this.multiply(dBn).magnitude;

        //step D2
        for (int i = u.length; i >= 0; i--) {

            //step D3
            qHat = (int) Math.floor(((u[i + v.length] * base) + u[i + v.length - 1]) / v[v.length - 1]);
            rHat = ((u[i + v.length] * base) + u[i + v.length - 1]) % v[v.length - 1];

            while (rHat < base) {

                if (qHat == base || (qHat * v[v.length - 2]) > ((base * rHat) + u[i + v.length - 2])) {

                    qHat--;
                    rHat += v[v.length - 1];

                }

            }

            //step D4
            BigNumber qHatBn = new BigNumber(Integer.toString(qHat));
            qHatBn.multiply(divisor);
            this.magnitude = subtract(this.magnitude, qHatBn.magnitude);
            //TODO: if this result is negative
            if (this.negative == true) {

                //base's complement

            }

        }

        //step D8
        remainder = this.divide(dBn).getFirst();
        result.setFirst(quotient);
        result.setSecond(remainder);

        //check if negative sign is needed base on signs of operands
        if (this.negative == true && divisor.negative == false && result.getFirst().magnitude != zeroArr|| this.negative == false && divisor.negative == true && result.getFirst().magnitude != zeroArr) {

            result.getFirst().negative = true;

        }

        return result;

    }

    /**
     * Returns two BigNumber prime factors of the invoker.
     *
     * @return a pair containing the two factors of the invoker in descending order
     * @author Daniel Haluszka
     */
    public BigNumberPair factor() {

        //TODO: clean up redundant variables in this function

        BigNumberPair result = new BigNumberPair();
        BigNumber twoBn = new BigNumber("2", false);

        //check if even
        if (this.magnitude[0] % 2 == 0) {

            //if even, divide by 2
            //divide will return first factor in first pair value, 0 in second because there will be no remainder
            result = this.divide(twoBn);
            //update second pair value to 2
            result.setSecond(twoBn);
            return result;

        } else {

            //approximate length of square root of invoker (n/2) and loop through all numbers of that length or less
            int rootLength = (int) Math.ceil((double) this.magnitude.length / 2); //the length of the square root of the invoker
            int maxRoot = (int) (Math.pow(10, rootLength) - 1); //the highest number of length rootLength for loop to start at
            BigNumber mag = new BigNumber("0");
            int[] zeroArr = new int[1];
            int[] oneArr = new int[1];
            oneArr[0] = 1;
            BigNumberPair divResult = new BigNumberPair(); //store the result of the division operation in the loop

            for (int i = maxRoot; i < maxRoot; i++) {

                if (this.divide(mag).getSecond().magnitude == zeroArr) {

                    //if there is no remainder from the division, a factor is found
                    result.setFirst(mag);
                    result.setSecond(divResult.getFirst());
                    return result;

                } else {

                    //this is not a factor, check next largest number
                    mag.magnitude = add(mag.magnitude, oneArr);

                }

            }

        }

        return result;

    }

}
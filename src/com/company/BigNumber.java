package com.company;

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
        int z = findGreaterMagnitude(operand1, operand2);//determining if operands are of equal length.
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


    public BigNumber subtract(BigNumber number){
        return add(number.negate());
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
     * The same as a compareTo method. Will be changed later.
     * @author Warren Devonshire
     * @param p1
     * @param p2
     * @return
     */
    private int findGreaterMagnitude(int[] p1, int[] p2){
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

        BigNumber result = new BigNumber("0", false);
        BigNumber zeroBn = new BigNumber("0", false);
        BigNumber oneBn = new BigNumber("1", false);

        //check for 0 or 1 multiplier and return simple cases if so
        if (factor.equals(zeroBn)) {

            return zeroBn;

        } else if (factor.equals(oneBn)) {

            return this;

        } else {

            int[] product = new int[this.magnitude.length + factor.magnitude.length];
            int[] operand1 = new int[this.magnitude.length];
            int[] operand2 = new int[factor.magnitude.length];
            operand1 = this.magnitude;
            operand2 = factor.magnitude;
            int temp = 0;
            int carry = 0; //carry
            int m = operand1.length;
            int n = operand2.length;

            //step M1
            for (int j = 0; j < n; j++) {

                //check for zero multiplier, step M2
                if (operand2[j] == 0) {

                    product[j + m] = 0;

                } else {

                    //step M3
                    carry = 0;

                    for (int i = 0; i < m; i++) {

                        //step M4
                        temp = ((operand1[i] * operand2[j]) + product[i + j] + carry);
                        product[i + j] = temp % base;
                        carry = temp / base;

                    }

                    //step M5
                    product[j + m] = carry;

                }

            }

            result.magnitude = product;
            result.normalize();

            //check if negative sign is needed base on signs of operands
            if (this.negative == true && factor.negative == false && result.equals(zeroBn) == false || this.negative == false && factor.negative == true && result.equals(zeroBn) == false) {

                result.negative = true;

            }

            return result;

        }

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

        /**
        //TODO: rename variables to something more readable, maybe add check for 1 divisor back in?

        BigNumber zeroBn = new BigNumber("0", false);

        //check for dividing by 0
        if (divisor.equals(zeroBn)) {

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
        int n = v.length + 1;
        int m = u.length - n;

        //step D1
        d = (int) Math.floor((base - 1) / v[n - 1]);
        System.out.println(d);
        BigNumber dBn = new BigNumber(Integer.toString(d), false);
        u = this.multiply(dBn).magnitude;
        v = divisor.multiply(dBn).magnitude;

        if (d == 1) {

            u[m + n] = 0;

        }

        //step D2
        for (int i = m; i >= 0; i--) {

            //step D3
            qHat = (int) Math.floor(((u[i + n] * base) + u[i + (n - 1)]) / v[n - 1]);
            rHat = ((u[i + n] * base) + u[i + (n - 1)] % v[n - 1]);

            while (rHat < base) {

                if (qHat == base || (qHat * v[n - 2]) > ((base * rHat) + u[i + (n - 2)])) {

                    qHat--;
                    rHat += v[n - 1];

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
        if (this.negative == true && divisor.negative == false && result.getFirst().equals(zeroBn) || this.negative == false && divisor.negative == true && result.getFirst().equals(zeroBn)) {

            result.getFirst().negative = true;

        }

        return result;
         **/

        BigNumberPair result = new BigNumberPair();
        BigNumber counter= new BigNumber("0", false);

        //check for dividing by 0
        if (divisor.equals(counter)) {

            throw new IllegalArgumentException("Divisor cannot be 0");

        }

        counter.magnitude[0] = 1;

        //if dividing by 1, return invoker (this does not check for leading 0's)
        if (divisor.equals(counter)) {

            //return invoker as quotient, second value in pair (remainder) is initialized to 0
            result.setFirst(this);
            return result;

        }

        counter.magnitude[0] = 0;

        //calculate quotient and remainder by method of repeated addition
        //create new non-negative BigNumber with magnitude of temp to compare workingTemp against in case invoker is negative
        BigNumber refTemp = new BigNumber(this.magnitude, false);
        BigNumber workingTemp = new BigNumber("0", false);
        BigNumber remainder = new BigNumber("0", false);
        BigNumber oneBn = new BigNumber("1", false);
        BigNumber zeroBn = new BigNumber("0", false);
        boolean loop = true;

        //this loop will add the magnitude of divisor to workingTemp until it goes over the magnitude of the invoker, at which point it will subtract the magnitude of divisor and calculate the remainder
        while (loop == true) {

            //if divisor can be added to magnitude of workingTemp without going over magnitude of divisor, we don't yet need to calculate remainder
            workingTemp = workingTemp.add(divisor);
            counter = counter.add(oneBn);

            if (workingTemp.compareTo(refTemp) == 0) {

                //if the magnitude of workingTemp is equal to the magnitude of the invoker, there is no remainder
                loop = false;

            } else if (workingTemp.compareTo(refTemp) == 1) {

                //if the magnitude of working temp goes over the magnitude of the invoker, calculate remainder
                //subtract the magnitude of divisor again to be used in calculating remainder
                workingTemp = workingTemp.add(divisor.negate());

                remainder = this.add(workingTemp.negate());
                counter = counter.add(oneBn.negate());

                loop = false;

            }
        }

        //update magnitude of temp with value of counter in magnitude and return as quotient, return remainder as second value in pair
        result.setFirst(counter);
        result.setSecond(remainder);

        if (this.negative == true && divisor.negative == false && !result.getFirst().equals(zeroBn) || this.negative == false && divisor.negative == true && !result.getFirst().equals(zeroBn)) {

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

        BigNumberPair result = new BigNumberPair();
        BigNumber zeroBn = new BigNumber("0", false);

        //check for zero invoker, if so return 0
        if (this.equals(zeroBn)) {

            result.setFirst(zeroBn);
            result.setSecond(zeroBn);
            return result;

        }

        //approximate length of square root of invoker (n/2) and loop through all numbers of that length or less stopping at 1 (which will always be a factor of the invoker)
        int rootLength = (int) Math.ceil((double) this.magnitude.length / 2); //the length of the square root of the invoker
        int maxRoot = (int) (Math.pow(10, rootLength) - 1); //the highest number of length rootLength for loop to start at
        BigNumber mag = new BigNumber("2", false); //the current potential factor being checked
        BigNumber oneBn = new BigNumber("1", false);
        BigNumberPair divResult = new BigNumberPair(); //store the result of the division operation in the loop

        for (int i = 2; i <= maxRoot; i++) {

            System.out.println(mag.toString());

            //divide invoker by current potential factor being checked
            divResult = this.divide(mag);

            if (divResult.getSecond().equals(zeroBn)) {

                //if there is no remainder from the division, a factor is found
                result.setFirst(mag);
                result.setSecond(divResult.getFirst());

                return result;

            } else {

                //this is not a factor, check next largest number
                mag = mag.add(oneBn);

            }

        }

        //if the loop does not find any factors, the number is prime
        result.setFirst(this);
        result.setSecond(oneBn);

        return result;

    }

}
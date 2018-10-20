package com.company;

/**
 * A data structure to hold a pair of associated BigNumbers
 * @author Daniel Haluszka
 */
public class BigNumberPair {

    /**
     * a primitive array of BigNumbers of size 2 to hold the pair of BigNumberss
     */
    private BigNumber[] arr = new BigNumber[2];

    /**
     * Constructor for the BigNumberPair class. Instantiates a BigNumber pair with two non-negative 0-valued BigNumbers as the values of the pair
     * @author Daniel Haluszka
     */
    public BigNumberPair() {

        BigNumber temp = new BigNumber("0", false);

        this.arr[0] = temp;
        this.arr[1] = temp;

    }

    /**
     * Sets the BigNumber object in the first position of the invoker.
     * @param newVal the BigNumber to replace the value currently in the first position of the BigNumberPair
     * @author Daniel Haluszka
     */
    public void setFirst(BigNumber newVal) {

        this.arr[0] = newVal;

    }

    /**
     * Returns the BigNumber object in the second position of the invoker.
     * @return the BigNumber currently in the first position of the BigNumberPair
     * @author Daniel Haluszka
     */
    public BigNumber getFirst() {

        return this.arr[0];

    }

    /**
     * Sets the BigNumber object in the first position of the invoker.
     * @param newVal the BigNumber to replace the value currently in the second position of the BigNumberPair
     * @author Daniel Haluszka
     */
    public void setSecond(BigNumber newVal) {

        this.arr[1] = newVal;

    }

    /**
     * Returns the BigNumber object currently in the second position of the invoker.
     * @return the BigNumber currently in the second position in the BigNumberPair
     * @author Daniel Haluszka
     */
    public BigNumber getSecond() {

        return this.arr[1];

    }

}

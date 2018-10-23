package com.company;

import java.util.Scanner;

/**
 * A simple driver program for debugging and testing the BigNumber class.
 *
 * @author Daniel Haluszka
 */
public class Main {

    public static void main(String[] args) {

        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (running == true) {

            System.out.println("Enter magnitude for first big number:\n");
            input = scanner.nextLine();
            BigNumber test = new BigNumber(input, false);

            System.out.println("Enter sign for first big number (-1/1):\n");
            input = scanner.nextLine();
            if (Integer.parseInt(input) == -1) {

                test = test.negate();

            }

            System.out.println("Enter magnitude for second big number:\n");
            input = scanner.nextLine();
            BigNumber test2 = new BigNumber(input, false);

            System.out.println("Enter sign for second big number (-1/1):\n");
            input = scanner.nextLine();
            if (Integer.parseInt(input) == -1) {

                test2 = test2.negate();

            }

            System.out.println("Enter operation to be performed (a/s/m/d/f/e/c):\n");
            input = scanner.nextLine();

            switch (input) {

                case "a":
                    System.out.println("Sum: " + test.add(test2).toString()  + "\n");
                    break;

                case "s":
                    System.out.println("Difference: " + test.subtract(test2).toString() + "\n");
                    break;

                case "m":
                    System.out.println("Product: " + test.multiply(test2).toString() + "\n");
                    break;

                case "d":
                    BigNumberPair resultD = test.divide(test2);
                    System.out.println("Quotient: " + resultD.getFirst().toString() + " Remainder: " + resultD.getSecond().toString() + "\n");
                    break;

                case "f":
                    BigNumberPair resultF = test.factor();
                    System.out.println("Factor 1: " + resultF.getFirst().toString() + " Factor 2: " + resultF.getSecond().toString() + "\n");
                    break;

                case "e":
                    System.out.println(test.equals(test2));
                    break;

                case "c":
                    System.out.println(test.compareTo(test2));
                    break;

                default:
                    System.out.println("Invalid input");
                    break;
            }

            if (test.sign() != 1) {

                test.negate();

            }

            System.out.println("Run again? (y/n):\n");
            input = scanner.nextLine();

            if (input.equals("n")) {

                running = false;

            }

        }

    }

}

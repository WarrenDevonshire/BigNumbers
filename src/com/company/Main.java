package com.company;

import java.util.Scanner;

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

                System.out.println("negate 2\n");

            }

            System.out.println("Enter operation to be performed (a/s/m/d/f):\n");
            input = scanner.nextLine();

            switch (input) {

                case "a":
                    System.out.println(test.add(test2).toString() + "\n");
                    break;

                case "s":
                    //System.out.println(test.subtract(test2).toString() + "\n");
                    break;

                case "m":
                    System.out.println(test.multiply(test2).toString() + "\n");
                    break;

                case "d":
                    System.out.println("Quotient: " + test.divide(test2).getFirst().toString() + " Remainder: " + test.divide(test2).getSecond().toString() + "\n");
                    break;

                case "f":
                    System.out.println("Factor 1: " + test.factor().getFirst().toString() + " Factor 2: " + test.factor().getSecond().toString() + "\n");
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

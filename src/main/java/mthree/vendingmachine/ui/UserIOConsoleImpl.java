/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.ui;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Implementation of UserIO interface, handles printing output to user and getting input from user.
 * 
 * @author utkua
 */
public class UserIOConsoleImpl implements UserIO {

    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.print(prompt);
        String output;
        Scanner s;
        
        // External library java.util.Scanner
        s = new Scanner(System.in);
        // Get output from use
        output = s.nextLine();
        
        return output;
    }
    
    @Override
    public String readYesNo(String prompt) {
        System.out.print(prompt);
        String output;
        Scanner s;
        
        do {   
            s = new Scanner(System.in);
            output = s.nextLine();
            if (output.equals("y") || output.equals("n")) {
                break;
            } else {
                System.out.print("Input must be be either y or n");
            }
        } while (true);
        
        return output;
    }

    @Override
    public int readInt(String prompt) {
        System.out.print(prompt);
        Scanner s;
        int output;
        
        do {   
            try {
                s = new Scanner(System.in);
                output = s.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Input must be an integer: ");
            }
        } while (true);
        
        return output;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.print(prompt);
        Scanner s;
        int output;
        
        do {   
            try {
                s = new Scanner(System.in);
                output = s.nextInt();
                if ((output >= min) &&(output <= max)) {
                    break;
                } else {
                    System.out.print("Input must be between " + min + " and " + max + ": ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Input must be an integer: ");
            }
        } while (true);
        
        return output;
    }

    @Override
    public double readDouble(String prompt) {
        System.out.print(prompt);
        Scanner s;
        double output;
        
        do {   
            try {
                s = new Scanner(System.in);
                output = s.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Input must be a double: ");
            }
        } while (true);
        
        return output;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        System.out.print(prompt);
        Scanner s;
        double output;
        
        do {   
            try {
                s = new Scanner(System.in);
                output = s.nextDouble();
                if ((output >= min) &&(output <= max)) {
                    break;
                } else {
                    System.out.print("Input must be between " + min + " and " + max + ": ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Input must be a double: ");
            }
        } while (true);
        
        return output;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.print(prompt);
        Scanner s;
        float output;
        
        do {   
            try {
                s = new Scanner(System.in);
                output = s.nextFloat();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Input must be a float: ");
            }
        } while (true);
        
        return output;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        System.out.print(prompt);
        Scanner s;
        float output;
        
        do {   
            try {
                s = new Scanner(System.in);
                output = s.nextFloat();
                if ((output >= min) &&(output <= max)) {
                    break;
                } else {
                    System.out.print("Input must be between " + min + " and " + max + ": ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Input must be a float: ");
            }
        } while (true);
        
        return output;
    }

    @Override
    public long readLong(String prompt) {
        System.out.print(prompt);
        Scanner s;
        long output;
        
        do {   
            try {
                s = new Scanner(System.in);
                output = s.nextLong();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Input must be a long: ");
            }
        } while (true);
        
        return output;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        System.out.print(prompt);
        Scanner s;
        long output;
        
        do {   
            try {
                s = new Scanner(System.in);
                output = s.nextLong();
                if ((output >= min) &&(output <= max)) {
                    break;
                } else {
                    System.out.print("Input must be between " + min + " and " + max + ": ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Input must be a long: ");
            }
        } while (true);
        
        return output;
    }
    
}

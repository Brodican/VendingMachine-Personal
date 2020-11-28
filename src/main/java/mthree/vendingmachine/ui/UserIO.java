/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.ui;

/**
 * Class that handles printing output to user and getting input from user.
 * 
 * @author utkua
 */
public interface UserIO {
    
    /**
     * Prints passed message.
     * @param msg message to be printed.
     */
    void print(String msg);

    /**
     * Prints prompt and reads use input, which must be a double for return.
     * @param prompt prompt to print to user.
     * @return double input by user.
     */
    double readDouble(String prompt);

    /**
     * Prints prompt and reads use input, which must be 
     * a double between min and max for return.
     * @param prompt prompt to print to user.
     * @param min minimum accepted value.
     * @param max maximum accepted value.
     * @return double input by user.
     */
    double readDouble(String prompt, double min, double max);

    /**
     * Prints prompt and reads use input, which must be a float for return.
     * @param prompt prompt to print to user.
     * @return float input by user.
     */
    float readFloat(String prompt);

    /**
     * Prints prompt and reads use input, which must be a float for return.
     * @param prompt prompt to print to user.
     * @param min minimum accepted value.
     * @param max maximum accepted value.
     * @return float input by user.
     */
    float readFloat(String prompt, float min, float max);

    /**
     * Prints prompt and reads use input, which must be a int for return.
     * @param prompt prompt to print to user.
     * @return int input by user.
     */
    int readInt(String prompt);

    /**
     * Prints prompt and reads use input, which must be a int for return.
     * @param prompt prompt to print to user.
     * @param min minimum accepted value.
     * @param max maximum accepted value.
     * @return int input by user.
     */
    int readInt(String prompt, int min, int max);

    /**
     * Prints prompt and reads use input, which must be a long for return.
     * @param prompt prompt to print to user.
     * @return long input by user.
     */
    long readLong(String prompt);

    /**
     * Prints prompt and reads use input, which must be a long for return.
     * @param prompt prompt to print to user.
     * @param min minimum accepted value.
     * @param max maximum accepted value.
     * @return long input by user.
     */
    long readLong(String prompt, long min, long max);

    /**
     * Prints prompt and reads use input, which must be a String for return.
     * @param prompt prompt to print to user.
     * @return String input by user.
     */
    String readString(String prompt);
    
    /**
     * Prints prompt and reads use input, which must be a y or n for return.
     * 'y' represents yes, 'n' represent no.
     * @param prompt prompt to print to user.
     * @return String ('y' or 'n') input by user.
     */
    String readYesNo(String prompt);
    
}

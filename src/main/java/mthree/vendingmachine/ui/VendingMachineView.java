/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.ui;

import java.math.BigDecimal;
import mthree.vendingmachine.dto.Item;

/**
 * Implements view layer of this application.
 * Uses UserIO class to retrieve input from user and return to Controller,
 * and display necessary output to user.
 * 
 * @author utkua
 */
public class VendingMachineView {
    
    private UserIO io;

    /**
     * Get UserIO object when constructed (dependency injection).
     * 
     * @param io UserIO object passed for use by this object.
     */
    public VendingMachineView(UserIO io) {
        this.io = io;
    }
    
    /**
     * Inform user vending machine items will be listed.
     */
    public void itemsBanner() {
        io.print("Vending Machine Items:\n"
                + "___________________\n");
    }
    
    /**
     * Print the passed item details as well as its number in the choice list.
     * 
     * @param number number of item in choice list.
     * @param item corresponding to number.
     */
    public void printItem(int number, Item item) {
        String moneyString = getMoneyString(item.getPrice());
        io.print(number + ". " + item.getName() + " _ price: " + moneyString + " _ remaining: " + item.getRemaining() + "\n");
    }
    
    /**
     * Print exit choice with its number in the choice list.
     * 
     * @param number number corresponding to exit in list.
     */
    public void printExitChoice(int number) {
        io.print(number + ". Exit\n");
    }
    
    /**
     * Get numeric choice from user, where choice can be between first and last value.
     * First is just 1, but is included as a variable for generality.
     * 
     * @param first first choice in list.
     * @param last last choice in list.
     * @return user choice from list of choices.
     */
    public int readChoice(int first, int last) {
        return io.readInt("\nPlease select from the above choices: ", first, last);
    }
    
    /**
     * Get user input on whether they want to add more credit.
     * 
     * @return user choice on whether they want to add more credit.
     */
    public String readChooseAddMoreCredit() {
        return io.readYesNo("Would you like to add more credit? y/n: ");
    }
    
    /**
     * Get number of each coin the user whishes to add.
     * 
     * @return array containing number of each coin the user added.
     */
    public int[] readAddedCredit() {
        int[] addedCredit = new int[4];
        addedCredit[0] = io.readInt("\nEnter quarter count: ");
        addedCredit[1] = io.readInt("Enter dime count: ");
        addedCredit[2] = io.readInt("Enter nickel count: ");
        addedCredit[3] = io.readInt("Enter penny count: ");
        
        return addedCredit;
    }
    
    /**
     * Display money the user has left in the machine, as a String.
     * 
     * @param credit amount user has as a BigDecimal.
     */
    public void displayRemainingCredit(BigDecimal credit) {
        String moneyString = getMoneyString(credit);
        io.print("Credit remaining: " + moneyString + "\n\n");
    }
    /**
     * Takes price in pennies as BigDecimal and returns price as String
     * of format $dollars.pennies.
     * @param price price in pennies.
     * @return price as String in desired format.
     */
    private String getMoneyString(BigDecimal price) {
        // Split price into two char arrays, with the dollar value and pennies seperately.
        char[] priceElements = price.toString().toCharArray();
        char[] dollarsArr = new char[priceElements.length - 2];
        char[] penniesArr = new char[2];
        System.arraycopy(priceElements, 0, dollarsArr, 0, dollarsArr.length);
        System.arraycopy(priceElements, dollarsArr.length, penniesArr, 0, penniesArr.length);
        
        // Put these values into Strings for printing.
        // 
        String dollars = (dollarsArr.length > 0) ? new String(dollarsArr) : "0";
        String pennies = new String(penniesArr);
        
        return "$" + dollars + "." + pennies;
    }
    
    /**
     * Display change user has left, printing number of each coin separately.
     * 
     * @param change number of each coin in a BigDecimal array.
     */
    public void displayChange(BigDecimal[] change) {
        io.print("Your change: \n" 
                + change[0] + " * " + Coins.QUARTER + "\n" 
                + change[1] + " * " + Coins.DIME + "\n" 
                + change[2] + " * " + Coins.NICKEL + "\n"
                + change[3] + " * " + Coins.PENNY + "\n\n");
    }
    
    /**
     * Inform user of error with descriptive message.
     * 
     * @param message description of error.
     */
    public void displayErrorMessage(String message) {
        io.print("=== ERROR ===\n" + message + "\n");
    }
    
    /**
     * Inform user of wrong input, with descriptive message.
     * Useful when UserIO cannot automatically catch wrong input,
     * or if UserIO wrong input message is not sufficiently descriptive.
     * 
     * @param message describes the nature of the user's wrong input.
     */
    public void displayWrongInputMessage(String message) {
        io.print("Wrong input: " + message + "\n");
    }
    
    /**
     * Inform user application is to exit, and thank them.
     */
    public void displayExitMessage() {
        io.print("Thank you for using this machine.");
    }
    
    /**
     * Inform user machine is trying to vend an item.
     */
    public void displayBuyItemBanner() {
        io.print("ATTEMPTING TO VEND ITEM\n");
    }
    
    /**
     * Inform user input item was bought successfully.
     * 
     * @param name name of bought item.
     */
    public void displaySuccessfulBuy(String name) {
        io.print("SUCCESSFULY BOUGHT: " + name + ".\n");
    }
}

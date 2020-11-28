/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Gets number of each coin either from pennies or array or coin type amounts.
 * 
 * @author utkua
 */
public class Change {
    
    private BigDecimal pennies;
    private BigDecimal nickels;
    private BigDecimal dimes;
    private BigDecimal quarters;
    
    /**
     * Takes number of pennies, uses this to calculate and store number
     * of each coin in that amount of pennies.
     * 
     * @param pennies credit input in terms of pennies.
     */
    public Change(BigDecimal pennies) {
        BigDecimal quarter = new BigDecimal(25);
        BigDecimal dime = new BigDecimal(10);
        BigDecimal nickel = new BigDecimal(5);
        
        this.pennies = pennies;
        this.quarters = this.pennies.divide(quarter, 0, RoundingMode.FLOOR);
        this.pennies = this.pennies.subtract(quarters.multiply(quarter));
        this.dimes = this.pennies.divide(dime, 0, RoundingMode.FLOOR);
        this.pennies = this.pennies.subtract(dimes.multiply(dime));
        this.nickels = this.pennies.divide(nickel, 0, RoundingMode.FLOOR);
        this.pennies = this.pennies.subtract(nickels.multiply(nickel));
    }
    
    /**
     * Takes array containing amount of each coin and stores these.
     * 
     * @param coinAmounts array of number of each coin input - quarters,
     * dimes, nickels, pennies respectively.
     */
    public Change(int[] coinAmounts) {
        this.quarters = new BigDecimal(coinAmounts[0]);
        this.dimes = new BigDecimal(coinAmounts[1]);
        this.nickels = new BigDecimal(coinAmounts[2]);
        this.pennies = new BigDecimal(coinAmounts[3]);
    }

    public BigDecimal getPennies() {
        return pennies;
    }

    public BigDecimal getNickels() {
        return nickels;
    }

    public BigDecimal getDimes() {
        return dimes;
    }

    public BigDecimal getQuarters() {
        return quarters;
    }
    
}

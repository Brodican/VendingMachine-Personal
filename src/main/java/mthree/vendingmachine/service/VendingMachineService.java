/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.service;

import java.math.BigDecimal;
import java.util.List;
import mthree.vendingmachine.dao.VendingMachinePersistenceException;
import mthree.vendingmachine.dto.Item;

/**
 *
 * @author utkua
 */
public interface VendingMachineService {
    
    /**
     * Retrieves all Item objects with stock remaining in Item HashMap.
     * 
     * @return List of all Item objects.
     * @throws VendingMachinePersistenceException 
     */
    List<Item> getAllItems() throws 
            VendingMachinePersistenceException;
    
    /**
     * Gets count of Items with stock remaining.
     * 
     * @return count of Items.
     * @throws VendingMachinePersistenceException 
     */
    int getCount() throws 
            VendingMachinePersistenceException;
    
    /**
     * Adds passed amount to user credit total.
     * 
     * @param addedCoins array of coins with values corresponding to 
     * number of each coin added.
     */
    void addCredit(int[] addedCoins);
    
    /**
     * Gets credit user currently has in machine.
     * 
     * @return remaining credit as BigDecimal.
     */
    BigDecimal getCredit();
    
    /**
     * Attempts to process purchase of an item by user.
     * If successful, reduces stock of chosen item and 
     * reduces user credit.
     * 
     * @param name name of Item user wishes to buy.
     * @return Item bought.
     * @throws VendingMachinePersistenceException
     * @throws NoItemInventoryException
     * @throws InsufficientFundsException 
     */
    Item buyOne(String name) throws 
            VendingMachinePersistenceException,
            NoItemInventoryException,
            InsufficientFundsException;
    
    /**
     * Gets user change as coins.
     * 
     * @return change returns to user as array with number of each
     * coin returned, where each array index corresponds to a coin.
     */
    BigDecimal[] getChange();
    
}

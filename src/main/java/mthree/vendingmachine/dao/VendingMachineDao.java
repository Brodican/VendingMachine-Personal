/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.dao;

import java.util.List;
import mthree.vendingmachine.dto.Item;

/**
 * DAO for reading and writing to Item inventory file,
 * retrieving items and updating their stock.
 * @author utkua
 */
public interface VendingMachineDao {
    
    /**
     * Get item with name passed.
     * 
     * @param name name of Item to retrieve
     * @return Item with name passed.
     * @throws VendingMachinePersistenceException 
     */
    Item getItem(String name)
            throws VendingMachinePersistenceException;
    
    /**
     * Returns a List of all Items in the machine. 
     * 
     * @return Item List containing all Items in the machine.
     * @throws VendingMachinePersistenceException
     */
    List<Item> getAllItems()
            throws VendingMachinePersistenceException;
    
    /**
     * Returns how many Items there are in the machine.
     * 
     * @return int of item count
     * @throws VendingMachinePersistenceException
     */
    int getCount()
            throws VendingMachinePersistenceException;
    
    /**
     * Returns the item chosen by the user to buy.
     * 
     * @param name name of item to be bought.
     * @return Item that had its stock reduced.
     * @throws VendingMachinePersistenceException
     */
    Item buyOne(String name)
            throws VendingMachinePersistenceException;
}

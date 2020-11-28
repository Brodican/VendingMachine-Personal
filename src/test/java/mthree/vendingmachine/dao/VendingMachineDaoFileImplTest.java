/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import mthree.vendingmachine.dto.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class testing each method of VendingMachineDao
 * 
 * @author utkua
 */
public class VendingMachineDaoFileImplTest {
    
    VendingMachineDao testDao;
    
    public VendingMachineDaoFileImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    /**
     * Makes test file and prints some dummy items for testing.
     * 
     * @throws IOException 
     */
    @BeforeEach
    public void setUp() throws IOException {
        String testFile = "testitems.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        PrintWriter out = new PrintWriter(new FileWriter(testFile, false));
        // Print 3 items to inventory, 2 with stock remaining and 1 without.
        out.println("item2::250::2\n" 
                + "item1::200::1\n"
                + "item3::100::0");
        // force PrintWriter to write line to the file
        out.flush();
        testDao = new VendingMachineDaoFileImpl(testFile);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test that Item known to be within inventory can be retrieved.
     * 
     * @throws VendingMachinePersistenceException 
     */
    @Test
    public void testGetItem() throws VendingMachinePersistenceException {
        // Make test item with same fields as known item in file
        Item testItem = new Item("item2", new BigDecimal(250));
        testItem.setRemaining(2);
        
        // Retrieve item of same fields with getItem
        Item retrievedItem = testDao.getItem(testItem.getName());
        // Check the data is equal
        assertEquals(testItem.getName(),
                    retrievedItem.getName(),
                    "Checking item name.");
        assertEquals(testItem.getPrice(),
                    retrievedItem.getPrice(),
                    "Checking item price.");
        assertEquals(testItem.getRemaining(), 
                    retrievedItem.getRemaining(),
                    "Checking item remaining stock.");
    }
    
    /**
     * Test that item list is not null, then test size of item
     * list (should be 2 as 2 items with stock). Also test
     * that known Item objects are in list (with field comparison
     * as items themselves are not added).
     * 
     * @throws VendingMachinePersistenceException 
     */
    @Test
    public void testGetItems() throws VendingMachinePersistenceException {
        // Make test items with same fields as known item in file
        Item testItem1 = new Item("item1", new BigDecimal(200));
        testItem1.setRemaining(1);
        Item testItem2 = new Item("item2", new BigDecimal(250));
        testItem2.setRemaining(2);
        
        // Retrieve the list of all Items within the DAO
        List<Item> allItems = testDao.getAllItems();

        // First check the general contents of the list
        // We know what has been added upon setup
        assertNotNull(allItems, "The list of Items must not be null.");
        assertEquals(2, allItems.size(),"List of Items should have 2 Items. It currently has " 
                + allItems.size() + ".");
        
        // Retrieve item of same fields with getItem
        Item retrievedItem1 = testDao.getItem(testItem1.getName());
        // Check the data is equal
        assertEquals(testItem1.getName(),
                    retrievedItem1.getName(),
                    "Checking item 1 name.");
        assertEquals(testItem1.getPrice(),
                    retrievedItem1.getPrice(),
                    "Checking item 1 price.");
        assertEquals(testItem1.getRemaining(), 
                    retrievedItem1.getRemaining(),
                    "Checking item 1 remaining stock.");
        
        // Same for item 2
        Item retrievedItem2 = testDao.getItem(testItem2.getName());
        assertEquals(testItem2.getName(),
                    retrievedItem2.getName(),
                    "Checking item 2 name.");
        assertEquals(testItem2.getPrice(),
                    retrievedItem2.getPrice(),
                    "Checking item 2 price.");
        assertEquals(testItem2.getRemaining(), 
                    retrievedItem2.getRemaining(),
                    "Checking item 2 remaining stock.");
    }
    
    /**
     * Test that count from getCount method of DAO is 2.
     * Should be 2 as 2 items in stock.
     * 
     * @throws VendingMachinePersistenceException 
     */
    @Test
    public void testGetCount() throws VendingMachinePersistenceException {
        // Get count from
        int count = testDao.getCount();
        
        // Check count from getCount equal to known count
        assertEquals(2, count, "Checking item count.");
    }
    
    /**
     * Tests that the buying of an item (with buyOne) 
     * actually reduces its stock.
     * 
     * @throws VendingMachinePersistenceException 
     */
    @Test
    public void testBuyOne() throws VendingMachinePersistenceException {
        Item testItem = new Item("item2", new BigDecimal(250));
        testItem.setRemaining(1);
        
        Item retrievedItem = testDao.buyOne(testItem.getName());
        
        // Check if item has 1 stock remaining after 1 was bought (originally set to 2).
        assertEquals(testItem.getRemaining(), retrievedItem.getRemaining());
        testDao.buyOne(testItem.getName());
        
        // Retrieve the list of all Items within the DAO
        List<Item> allItems = testDao.getAllItems();
        
        // If item is bought out, getAllItems should retrieve only one item.
        assertEquals(1, allItems.size(), "Checking only item remaining in stock retrieved.");
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.service;

import java.math.BigDecimal;
import mthree.vendingmachine.dao.VendingMachinePersistenceException;
import mthree.vendingmachine.dto.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Test class testing each method of the service class.
 * 
 * @author utkua
 */
public class VendingMachineServiceImplTest {
    
    private VendingMachineService testService;
    
    /**
     * Loads ApplicationContext, prepares test service layer object 
     * with respective bean from said context.
     */
    public VendingMachineServiceImplTest() {
        // Load context from resources
        ApplicationContext ctx = 
                new ClassPathXmlApplicationContext("applicationContext.xml");
        // Get controller object from its bean, everything in memory is built
        testService = 
                ctx.getBean("serviceLayer", VendingMachineService.class);
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Tests that getting all items gets a list of size 1 - the size the DAO stub gets.
     * 
     * @throws VendingMachinePersistenceException 
     */
    @Test
    public void testGetAllItems() throws VendingMachinePersistenceException {
        Item testItem = new Item("item1", new BigDecimal(200));
        testItem.setRemaining(1);
        
        assertEquals(1, testService.getAllItems().size(), "Should only have 1 Item.");
        assertTrue(testService.getAllItems().contains(testItem), "Should contain test Item item1.");
    }
    
    /**
     * Tests that adding credit in the form of coins adds the correct amount of credit,
     * and that getting credits returns the correct amount.
     */
    @Test
    public void testAddGetCredit() {
        int[] addedCoins = {3, 2, 4, 1};
        testService.addCredit(addedCoins);
        
        // Set credit.
        BigDecimal credit = testService.getCredit();
        // Result should be 116.
        BigDecimal shouldBe = new BigDecimal(116);
        
        // Assert that added credit is 116.
        assertEquals(shouldBe, credit, "Checking that correct amount of credit was added.");
        
        credit = testService.getCredit();
        // Add to compared amount, then ensure comparison is wrong.
        shouldBe = shouldBe.add(new BigDecimal(1));
        
        assertNotEquals(shouldBe, credit, "Checking that incorrect amount is not retrieved.");
    }
    
    /**
     * Tests that NoItemInventoryException and InsufficientFundsException
     * are thrown at appropriate times, and that buying an item works otherwise.
     * Implicitly tests getMoneyString.
     */
    @Test
    public void testBuyOne() throws VendingMachinePersistenceException {
        int[] addedCoins = {4, 0, 0, 0};
        
        testService.addCredit(addedCoins);
        try {
            testService.buyOne("item1");
            fail("Expected Exception was not thrown.");
        } catch (VendingMachinePersistenceException
                | NoItemInventoryException e) {
            fail("Incorrect exception was thrown.");
        } catch (InsufficientFundsException e){
            // This tests 'getMoneyString' too.
            assertEquals("Item item1 costs $2.00. You only input $1.00.", e.toString().split(": ")[1]);
        }
        
        testService.addCredit(addedCoins);
        try {
            testService.buyOne("item1");
        } catch (VendingMachinePersistenceException
                | NoItemInventoryException
                | InsufficientFundsException e) {
            fail("Exception was thrown.");
        }
        
        // Check that item is no longer returned (as it has no stock).
        assertEquals(0, testService.getAllItems().size(), "Checking that there are no items with stock left.");
        
        testService.addCredit(addedCoins);
        testService.addCredit(addedCoins);
        try {
            testService.buyOne("item1");
        } catch (VendingMachinePersistenceException
                | InsufficientFundsException e) {
            fail("Incorrect exception was thrown.");
        } catch (NoItemInventoryException e) {
        
        }
    }
    
    /**
     * Test that count returned by getCount is initially 1, 
     * then 0 after single item is bought.
     * 
     * @throws VendingMachinePersistenceException
     * @throws NoItemInventoryException
     * @throws InsufficientFundsException 
     */
    public void testGetCount() throws 
            VendingMachinePersistenceException, 
            NoItemInventoryException, 
            InsufficientFundsException {
        assertEquals(1, testService.getCount());
        testService.addCredit(new int[]{10, 0, 0, 0});
        testService.buyOne("item1");
        assertEquals(0, testService.getCount());
    }
    
    public void testGetChange() {
        int[] addedCoins = {4, 2, 1, 3};
        testService.addCredit(addedCoins);
        BigDecimal[] retrievedCredits = testService.getChange();
        
        BigDecimal[] expected = new BigDecimal[4];
        expected[0] = new BigDecimal(5);
        expected[1] = new BigDecimal(0);
        expected[2] = new BigDecimal(0);
        expected[3] = new BigDecimal(3);
        
        assertEquals(expected, retrievedCredits, "Checking change is correct.");
    }
    
}

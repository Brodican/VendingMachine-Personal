/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import mthree.vendingmachine.dto.Item;

/**
 *
 * @author utkua
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {

    // Hashmap from library java.util.HashMap
    // Map from java.util.Map
    private Map<String, Item> items = new HashMap<>();
    
    // Won't change after being set in constructor
    public final String ITEMS_FILE;
    // Won't change
    public static final String DELIMITER = "::";
    
    /**
     * Constructs a VendingMachineDaoFileImpl object with default items file name.
     */
    public VendingMachineDaoFileImpl() {
        ITEMS_FILE = "items.txt";
    }
    
    /**
     * Constructs a VendingMachineDaoFileImpl object with passed custom items file name.
     * 
     * @param ITEMS_FILE name of file to read/write.
     */
    public VendingMachineDaoFileImpl(String ITEMS_FILE) {
        this.ITEMS_FILE = ITEMS_FILE;
    }
    
    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
        readItems();
        return items.get(name);
    }
    
    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        // Read items from file to ensure all items in memory.
        readItems();
        // Arraylist with all values
        // From library java.util.ArrayList
        ArrayList<Item> itemList = new ArrayList<>(items.values());
        
        return itemList.stream().filter((i) -> i.getRemaining() != 0)
                .collect(Collectors.toList());
    }
    
    @Override
    public int getCount() throws VendingMachinePersistenceException {
        readItems();
        ArrayList<Item> itemList = new ArrayList(items.values());
        return itemList.stream().filter((i) -> i.getRemaining() != 0)
                .collect(Collectors.toList()).size();
    }
    
    @Override
    public Item buyOne(String name) throws VendingMachinePersistenceException {
        readItems();
        Item bought = items.get(name);
        bought.reduceStock();
        items.put(name, bought);
        writeItems();
        return bought;
    }
    
    /**
     * Marshals an item to a String.
     * 
     * @param anItem item to convert to string (marshal).
     * @return String with item fields split by delimiter.
     */
    private String marshalItem(Item anItem){
        // Turn a Item object into a line of text for file

        // Start with the Item name, since that's supposed to be first
        String itemAsText = anItem.getName() + DELIMITER;

        // Add rest of properties in the correct order:

        // Price of Item
        itemAsText += anItem.getPrice()+ DELIMITER;

        // Remaining stock of Item
        itemAsText += anItem.getRemaining();

        // Return Item as text.
        return itemAsText;
    }
    
    /**
     * Unmarshal a line from file (String) into an Item
     * 
     * @param itemsAsText String to convert to Item (unmarshal).
     * @return Item with fields set according to input String.
     */
    private Item unmarshalItem(String itemsAsText) throws VendingMachinePersistenceException {
        
        // split returns string array split on DELIMETER
        String[] itemFields = itemsAsText.split(DELIMITER);
        BigDecimal itemPrice;
        
        Item itemFromFile;
        
        double ePlusPi = Math.PI + Math.E;
        
        try {
            // Item name is in index 0 of the array.
            String itemName = itemFields[0];
            
            // Item price is index 2.
            // Check if item has wrong format in file
            if (itemFields[1].contains(".")) {
                throw new VendingMachinePersistenceException("Error in format of an Item, "
                       + "price in file should not contain decimal point.");
            }
            
            itemPrice = new BigDecimal(itemFields[1]);
            if (itemPrice.equals(new BigDecimal(0))) {
                throw new VendingMachinePersistenceException("Error in format of an Item, "
                        + "price in file should not be 0.");
            }
            
            // Remaining stock is index 3.
            int remaining = Integer.parseInt(itemFields[2]);

            // Which we can then use to create a new Item object to satisfy
            // the requirements of the Item constructor.
            itemFromFile = new Item(itemName, itemPrice);

            // 1 remaining field that needs to be set into the
            // new Item object. Do this manually by using the appropriate setter.
        
            // Index 1 - Release date
            itemFromFile.setRemaining(remaining);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            // Error translation
            throw new VendingMachinePersistenceException(
                    "Error in format of an Item.", e);
        }


        // Return Item created from file
        return itemFromFile;
    }
    
    
    /**
     * Writes all Items in the machine out to a ITEMS_FILE.  See readItems
     * for file format.
     * 
     * @throws VendingMachinePersistenceException if an error occurs writing to the file.
     */
    private void writeItems() throws VendingMachinePersistenceException {
        // Printwriter from java.io.PrintWriter;
        PrintWriter out;

        try {
            // Filewriter from java.io.FileWriter
            out = new PrintWriter(new FileWriter(ITEMS_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save Item data.", e);
        }

        // Write out the Item objects to the items file.
        String itemAsText;
        List<Item> itemList = new ArrayList(items.values());
        for (Item currentItem : itemList) {
            // turn a Item into a String
            itemAsText = marshalItem(currentItem);
            // write the Item object to the file
            out.println(itemAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }
    
    /**
     * Reads all Items in the library to @items.
     * 
     * @throws VendingMachinePersistenceException if an error occurs reading from the file.
     */
    private void readItems() throws VendingMachinePersistenceException {
        // Scanner from java.util.Scanner
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    // BuferredRead from java.io.BufferedReader
                    new BufferedReader(
                            // Filereader from java.io.FileReader
                            new FileReader(ITEMS_FILE)));
        } catch (FileNotFoundException e) { // Catch and translate FileNotFoundException
            throw new VendingMachinePersistenceException(
                    "Could not load item data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentItem holds the most recent Item unmarshalled
        Item currentItem;
        // Go through ITEMS_FILE line by line, decoding each line into a 
        // Item object by calling the unmarshallItem method.
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into an Item
            currentItem = unmarshalItem(currentLine);

            // Put currentItem into the map using Item name as the key
            items.put(currentItem.getName(), currentItem);
        }
        // close scanner
        scanner.close();
    }
    
}

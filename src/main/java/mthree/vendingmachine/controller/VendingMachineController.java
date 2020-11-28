/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.controller;

import java.math.BigDecimal;
import mthree.vendingmachine.dao.VendingMachinePersistenceException;
import mthree.vendingmachine.dto.Item;
import mthree.vendingmachine.service.InsufficientFundsException;
import mthree.vendingmachine.service.NoItemInventoryException;
import mthree.vendingmachine.service.VendingMachineService;
import mthree.vendingmachine.ui.VendingMachineView;

/**
 *
 * @author utkua
 */
public class VendingMachineController {
    
    private VendingMachineView view;
    private VendingMachineService service;
    
    public VendingMachineController(VendingMachineView view, VendingMachineService service) {
        this.view = view;
        this.service = service;
    }
    
    /**
     * Application loop in this method, where control of application occurs.
     */
    public void run() {
        Item menuSelection;
        boolean keepGoing = true;
        try {
            showMenuSelection();
            if (getCredit() == false) {
                exitMessage();
                return;
            }
            showRemainingCredit();
            menuSelection = getMenuSelection();
            if (menuSelection == null) {
                keepGoing = false;
            } else {
                buyItem(menuSelection.getName());
                showRemainingCredit();
            }
        }  catch (VendingMachinePersistenceException ex) {
            view.displayErrorMessage(ex.getMessage());
            keepGoing = false;
        } catch (NoItemInventoryException | InsufficientFundsException ex) {
            view.displayErrorMessage(ex.getMessage());
        }
        while (keepGoing) {
            try {
                showMenuSelection();
                if (addMoreCredit()) {
                    getCredit();
                }
                showRemainingCredit();
                menuSelection = getMenuSelection();
                if (menuSelection == null) {
                    keepGoing = false;
                } else {
                    buyItem(menuSelection.getName());
                    view.displaySuccessfulBuy(menuSelection.getName());
                    showRemainingCredit();
                }
            
            }  catch (VendingMachinePersistenceException ex) {
                view.displayErrorMessage(ex.getMessage());
                keepGoing = false;
            } catch (NoItemInventoryException | InsufficientFundsException ex) {
                view.displayErrorMessage(ex.getMessage());
            }
        }
        showChange();
        exitMessage();
    }
    
    /**
     * Print all items with remaining stock
     * 
     * @throws VendingMachinePersistenceException 
     */
    private void showMenuSelection() throws VendingMachinePersistenceException {
        int first = 1;
        int last = service.getCount();
        for (int i = 0; i < last; i++) {
            view.printItem(i + 1, service.getAllItems().get(i));
        }
    }
    
    /**
     * Prints available menu items and returns the item corresponding to user's choice.
     * 
     * @return Item chosen by user.
     * @throws VendingMachinePersistenceException 
     */
    private Item getMenuSelection() throws VendingMachinePersistenceException {
        int first = 1;
        int last = service.getCount();
        for (int i = 0; i < last; i++) {
            view.printItem(i + 1, service.getAllItems().get(i));
        }
        
        view.printExitChoice(last + 1);
        // Subtract 1 from choice to ensure choice matches index in HashMap
        int choice = view.readChoice(first, last + 1) - 1;
        if (choice == last) {
            return null;
        }
        
        return service.getAllItems().get(choice);
    }
    
    private boolean addMoreCredit() {
        String addCredit;
        
        // Let user choose if they wish to add more credit.
        addCredit = view.readChooseAddMoreCredit();
        return addCredit.equals("y");
    }
    
    /**
     * Gets amount to add from user, ensures it is added.
     * 
     * @return true if credits added successfully, false if not.
     */
    private boolean getCredit() {
        boolean keepGoing = true;
        int[] addedCoins;
        while (keepGoing) {
            addedCoins = view.readAddedCredit();
            if ((addedCoins[0] == 0) && (addedCoins[1] == 0) && (addedCoins[2] == 0) && (addedCoins[3] == 0)){
                view.displayWrongInputMessage("You must input at least one coin.");
                // Let user choose if they wish to add more credit.
                String addCredit = view.readChooseAddMoreCredit();
                if (addCredit.equals("y")) {
                    break;
                } else if (addCredit.equals("n")) {
                    return false;
                }
            } else {
                service.addCredit(addedCoins);
                keepGoing = false;
            }
        }
        
        return true;
    }
    
    private void showChange() {
        BigDecimal[] change = service.getChange();
        view.displayChange(change);   
    }
    
    /**
     * Retrieves remaining credit, has it printed.
     */
    private void showRemainingCredit() {
        BigDecimal creditLeft = service.getCredit();
        view.displayRemainingCredit(creditLeft);
    }
    
    /**
     * Has user informed that item is being bought, then calls 
     * appropriate service method to apply vend to bought item.
     * 
     * @param name name of item bought
     * @throws NoItemInventoryException
     * @throws VendingMachinePersistenceException
     * @throws InsufficientFundsException 
     */
    private void buyItem(String name) throws 
            NoItemInventoryException, 
            VendingMachinePersistenceException, 
            InsufficientFundsException {
        view.displayBuyItemBanner();
        service.buyOne(name);
        view.displaySuccessfulBuy(name);
    }
    
    /**
     * Has farewell message printed.
     */
    private void exitMessage() {
        view.displayExitMessage();
    }
    
}

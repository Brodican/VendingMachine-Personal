/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import mthree.vendingmachine.dao.VendingMachineAuditDao;
import mthree.vendingmachine.dao.VendingMachineDao;
import mthree.vendingmachine.dao.VendingMachinePersistenceException;
import mthree.vendingmachine.dto.Item;

/**
 *
 * @author utkua
 */
public class VendingMachineServiceImpl implements VendingMachineService {
    
    VendingMachineDao dao;
    VendingMachineAuditDao auditDao;
    BigDecimal credit = new BigDecimal(BigInteger.ZERO);
    
    public VendingMachineServiceImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }
    
    @Override
    public int getCount() throws VendingMachinePersistenceException {
        return dao.getCount();
    }
    
    @Override
    public void addCredit(int[] addedCoins) {
        BigDecimal addedPennies = new BigDecimal(addedCoins[0] * 25 
                + addedCoins[1] * 10 + addedCoins[2] * 5 + addedCoins[3]);
        this.credit = this.credit.add(addedPennies);
    }
    
    @Override
    public BigDecimal getCredit() {
        return credit;
    }
    
    @Override
    public Item buyOne(String name) throws 
            VendingMachinePersistenceException,
            NoItemInventoryException,
            InsufficientFundsException {
        Item item = dao.getItem(name);
        if (item.getRemaining() == 0) {
            throw new NoItemInventoryException("Item " + item.getName() 
                    + " has no stock remaining.");
        }
        
        BigDecimal price = item.getPrice();
        if (price.compareTo(credit) > 0) {
            throw new InsufficientFundsException("Item " + item.getName() 
                    + " costs " + getMoneyString(item.getPrice()) + ". You only input " + getMoneyString(credit) + ".");
        }
        
        auditDao.writeAuditEntry("Item " + name + " bought. Remaining stock: " + item.getRemaining() + ".");
        credit = credit.subtract(price);
        
        return dao.buyOne(name);
    }
    
    @Override
    public BigDecimal[] getChange() {
        Change change = new Change(credit);
        BigDecimal[] changeCoins = {change.getQuarters(), change.getDimes(), change.getNickels(), change.getPennies()};
    
        return changeCoins;
    }
    
    /**
     * Takes price in pennies as BigDecimal and returns price as String
     * of format $dollars.pennies.
     * 
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
        String dollars = new String(dollarsArr);
        String pennies = new String(penniesArr);
        
        return "$" + dollars + "." + pennies;
    }
    
}

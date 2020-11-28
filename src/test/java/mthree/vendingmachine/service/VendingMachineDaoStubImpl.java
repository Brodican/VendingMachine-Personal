/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mthree.vendingmachine.dao.VendingMachineDao;
import mthree.vendingmachine.dao.VendingMachinePersistenceException;
import mthree.vendingmachine.dto.Item;

/**
 *
 * @author utkua
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {
    
    private Item onlyItem;
    
    public VendingMachineDaoStubImpl() {
        onlyItem = new Item("item1", new BigDecimal(200));
        onlyItem.setRemaining(1);
    }

    public VendingMachineDaoStubImpl(Item onlyItem) {
        this.onlyItem = onlyItem;
    }
    
    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
        if (name.equals(onlyItem.getName())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        List<Item> itemList = new ArrayList<>();
        if (onlyItem.getRemaining() > 0) {
            itemList.add(onlyItem);
        }
        
        return itemList;
    }

    @Override
    public int getCount() throws VendingMachinePersistenceException {
        if (onlyItem.getRemaining() > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public Item buyOne(String name) throws VendingMachinePersistenceException {
        if (name.equals(onlyItem.getName())) {
            onlyItem.reduceStock();
            return onlyItem;
        } else {
            return null;
        }
    }
    
}

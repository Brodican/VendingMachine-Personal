/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Holds name, price, remaining count for an item.
 * @author utkua
 */
public class Item {
    
    private String name;
    private BigDecimal price;
    private int remaining;

    /**
     * Creates new item with given name and price.
     * 
     * @param name name of item.
     * @param price price of item.
     */
    public Item(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    /**
     * 
     * @return name of item.
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return price of item.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 
     * @return remaining item inventory.
     */
    public int getRemaining() {
        return remaining;
    }

    /**
     * Set the remaining stock.
     * @param remaining remaining stock of item.
     */
    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
    
    /**
     * Reduce remaining by 1.
     */    
    public void reduceStock() {
        remaining--;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.price);
        hash = 19 * hash + this.remaining;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.remaining != other.remaining) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }
    
}

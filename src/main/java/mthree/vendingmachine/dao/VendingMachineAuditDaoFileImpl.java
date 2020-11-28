/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author utkua
 */
public class VendingMachineAuditDaoFileImpl implements VendingMachineAuditDao {
    
    public final String AUDIT_FILE;
    
    /**
     * Sets file audit DAO will read from to default.
     */
    public VendingMachineAuditDaoFileImpl() {
        AUDIT_FILE = "audit.txt";
    }
    
    /**
     * Sets file read from to passed file name.
     * @param AUDIT_FILE file name for audit DAO to read from.
     */
    public VendingMachineAuditDaoFileImpl(String AUDIT_FILE) {
        this.AUDIT_FILE = AUDIT_FILE;
    }
   
    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        PrintWriter out;
       
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not persist audit information.", e);
        }
 
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
    }    
    
}

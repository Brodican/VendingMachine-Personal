/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mthree.vendingmachine.app;

import mthree.vendingmachine.controller.VendingMachineController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Class through which application is accessed.
 * Loads ApplicationContext, prepares test Controller object 
 * with respective bean from said context. 
 * @author utkua
 */
public class App {
    
    /**
     * Main method of the application - starting point.
     * 
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        // Load the XML file with our application context, from resources directory by default
        ApplicationContext ctx = 
           new ClassPathXmlApplicationContext("applicationContext.xml");
        // Get controller object from its bean, everything in memory is built
        VendingMachineController controller = 
           ctx.getBean("controller", VendingMachineController.class);
        // Run program loop
        controller.run();
    }
    
}

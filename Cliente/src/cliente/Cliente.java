/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliente;

import corrector.Corrector;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joaquin Pereira Chapel
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    private static final String HOST = "localhost";
    private static final int PUERTO = 5555;
    
    public static void main(String[] args) {
        Corrector corrector = null;
        
        Registry registry;
        
        try {
            registry = LocateRegistry.getRegistry(HOST, PUERTO);
            
            corrector = (Corrector)registry.lookup("Correcion");
            
            double nota = corrector.corregir("aaaaaaabbb");
            
            System.out.println("Nota: " + nota);
            System.out.println("Media: " + corrector.getMedia());
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}

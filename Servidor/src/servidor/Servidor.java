/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import corrector.Corrector;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joaquin Pereira Chapel
 */
public class Servidor implements Corrector {
    
    private static double suma = 0;
    private static double contador = 0;
    private static final String SOLUCION = "aaaaaaaaaa";
    private static final Semaphore mutex = new Semaphore(1);
    private static final int PUERTO = 5555;

    public static void main(String[] args) {
        System.out.println("Creando el registro de objetos remotos");
        
        Registry registry;
        
        try {
            registry = LocateRegistry.createRegistry(PUERTO);
            
            Servidor servidor = new Servidor();
            
            registry.bind("Correcion", (Corrector)UnicastRemoteObject.exportObject(servidor, PUERTO));
            
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public double corregir(String respuesta) throws RemoteException {
        double nota = 0;
        nota = comprobarRespuestas(respuesta, nota);
        
        try {
            mutex.acquire();
            Servidor.suma += nota;
            Servidor.contador ++;
            mutex.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nota;
    }

    private double comprobarRespuestas(String respuesta, double nota) {
        for (int i = 0; i < SOLUCION.length(); i++) {
            if (respuesta.charAt(i) != 0) {
                if (respuesta.charAt(i) == SOLUCION.charAt(i)) {
                    nota++;
                } else {
                    nota -= 0.25;
                }
            }
        }
        if(nota < 0){
            nota = 0;
        }
        return nota;
    }

    @Override
    public double getMedia() throws RemoteException {
        double media = 0;
        
        try {
            mutex.acquire();
            media = suma / contador;
            mutex.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return media;
    }

}

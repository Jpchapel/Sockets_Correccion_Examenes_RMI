/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package corrector;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Joaquin Pereira Chapel
 */
public interface Corrector extends Remote{
    public double corregir(String respuesta) throws RemoteException;
    public double getMedia() throws RemoteException;
}

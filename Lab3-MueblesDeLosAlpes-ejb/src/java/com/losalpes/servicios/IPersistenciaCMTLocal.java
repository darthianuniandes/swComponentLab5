/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import com.losalpes.entities.Mueble;
import com.losalpes.entities.Usuario;
import com.losalpes.entities.Vendedor;
import com.losalpes.excepciones.CupoInsuficienteException;
import java.util.ArrayList;
import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author darthian
 */
@Local
public interface IPersistenciaCMTLocal {
    
    public void insertRemoteDatabase(Vendedor vendedor);
    
    public void deleteRemoteDatabase(Vendedor vendedor);
    
    void comprar(Usuario usuario, ArrayList<Mueble> inventario, double precioTotalInventario) 
            throws CupoInsuficienteException;
  
    public void descontarCupoTarjeta(long documento, 
            double precioTotalInventario );
}

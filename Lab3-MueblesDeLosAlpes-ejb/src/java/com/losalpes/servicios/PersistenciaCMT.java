/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import com.losalpes.entities.Mueble;
import com.losalpes.entities.RegistroVenta;
import com.losalpes.entities.TarjetaCredito;
import com.losalpes.entities.Usuario;
import com.losalpes.entities.Vendedor;
import com.losalpes.excepciones.CupoInsuficienteException;
import com.losalpes.excepciones.OperacionInvalidaException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author darthian
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PersistenciaCMT implements IPersistenciaCMTLocal, IPersistenciaCMTRemote{
    
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private IServicioPersistenciaMockLocal persistencia;
        
    @PersistenceContext(unitName = "Lab3-MueblesDeLosAlpes-ejbPU")
    private EntityManager entityDerby;

    @Override
    public void insertRemoteDatabase(Vendedor vendedor) {
        try {
            persistencia.create(vendedor);
        } catch (OperacionInvalidaException ex) {
            Logger.getLogger(PersistenciaBMT.class.getName()).log(Level.SEVERE, null, ex);    
        }
    }
    
    @Override
    public void deleteRemoteDatabase(Vendedor vendedor) {
        try {
            persistencia.delete(vendedor);
        } catch (OperacionInvalidaException ex) {
            Logger.getLogger(PersistenciaBMT.class.getName()).log(Level.SEVERE, null, ex);    
        }
    }
    
    @Override
    public void comprar(Usuario usuario, ArrayList<Mueble> inventario, double precioTotalInventario) 
            throws CupoInsuficienteException {
        
        this.registrarVenta(usuario, inventario);
        this.validarCupoTarjeta(usuario, precioTotalInventario);
    }
    
    private void registrarVenta(Usuario usuario, ArrayList<Mueble> inventario) {
        for (Mueble mueble : inventario) {
            Mueble editar = (Mueble) persistencia.findById(Mueble.class, mueble.getReferencia());
            editar.setCantidad(editar.getCantidad() - mueble.getCantidad());
            RegistroVenta compra = new RegistroVenta(new Date(System.currentTimeMillis()), 
                    mueble, mueble.getCantidad(), null, usuario);
            Random rand = new Random();
            int  n = rand.nextInt(50) + 1;
            System.out.println("ISM ID"+n);
            compra.setId((long) n);
            usuario.agregarRegistro(compra);
            System.out.println("ISM ACTUALIZA");
            persistencia.update(usuario);
            persistencia.update(editar);
        }
    }
    
    private void validarCupoTarjeta(Usuario usuario, double precioTotalInventario) 
            throws CupoInsuficienteException {
        
        TarjetaCredito credito = (TarjetaCredito) entityDerby.createNamedQuery(
                "TarjetaCredito.findByNombreTitular")
                .setParameter("nombreTitular", usuario.getLogin()).getSingleResult();
        
        //se valida el cupo de la tarjeta 
        if (credito.getCupo() < precioTotalInventario) {
            System.out.print("Cupo insuficiente");
            throw new CupoInsuficienteException("Cupo Insufuciente para realizar la compra");   
        }
    }

    @Override
    public void descontarCupoTarjeta(long documento, double precioTotalInventario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

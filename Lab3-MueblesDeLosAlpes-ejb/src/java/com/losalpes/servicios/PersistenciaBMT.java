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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author darthian
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class PersistenciaBMT implements IPersistenciaBMTLocal, IPersistenciaBMTRemote {
    
    @Resource
    private UserTransaction userTransaction;
    
    @Resource
    private SessionContext context;
    
    @PersistenceContext(unitName = "Lab3-MueblesDeLosAlpes-ejbPU")
    private EntityManager entityDerby;
    
    @EJB
    private IServicioPersistenciaMockLocal persistencia;

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
            try {        
                userTransaction.setRollbackOnly();
            } catch (IllegalStateException ex1) {
                Logger.getLogger(PersistenciaBMT.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SystemException ex1) {
                Logger.getLogger(PersistenciaBMT.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    @Override
    public void comprar(Usuario usuario, ArrayList<Mueble> inventario, double precioTotalInventario) 
            throws CupoInsuficienteException {
        try {
            userTransaction.begin();
            
            this.registrarVenta(usuario, inventario);
            this.validarCupoTarjeta(usuario, precioTotalInventario);
            
            userTransaction.commit();       
        
    }   catch (RollbackException | HeuristicMixedException 
            | HeuristicRollbackException | SecurityException
            | IllegalStateException | NotSupportedException 
            | SystemException | javax.transaction.RollbackException ex) {
            Logger.getLogger(PersistenciaBMT.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    private void registrarVenta(Usuario usuario, ArrayList<Mueble> inventario) {
        Mueble mueble;
        for (int i = 0; i < inventario.size(); i++)
        {
            mueble = inventario.get(i);
            Mueble editar=(Mueble) persistencia.findById(Mueble.class, mueble.getReferencia());
            editar.setCantidad(editar.getCantidad() - mueble.getCantidad());
            RegistroVenta compra = new RegistroVenta(new Date(System.currentTimeMillis()), 
                    mueble, mueble.getCantidad(), null, usuario);
            usuario.agregarRegistro(compra);

            persistencia.update(usuario);
            persistencia.update(editar);
        }
    }
    
    private void validarCupoTarjeta(Usuario usuario, double precioTotalInventario) 
            throws CupoInsuficienteException, IllegalStateException, SecurityException, SystemException {
        
        TarjetaCredito credito = (TarjetaCredito)entityDerby.createNamedQuery(
                "Tarjetacreditoalpes.findByDocumentoTitular")
                .setParameter("documentoTitular", new Double(usuario.getDocumento())).getSingleResult();
        
        //se valida el cupo de la tarjeta 
        if (credito.getCupo() < precioTotalInventario) {
            userTransaction.rollback();
            throw new CupoInsuficienteException("Cupo Insufuciente para realizar la compra");   
        }
    }

    @Override
    public void descontarCupoTarjeta(long documento, double precioTotalInventario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
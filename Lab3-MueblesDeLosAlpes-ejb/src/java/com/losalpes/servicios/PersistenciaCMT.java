/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import com.losalpes.entities.Mueble;
import com.losalpes.entities.TarjetaCredito;
import com.losalpes.entities.Vendedor;
import com.losalpes.excepciones.OperacionInvalidaException;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author darthian
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PersistenciaCMT {
    @Resource
    private SessionContext context;
    
    @EJB
    private ServicioPersistencia servicio;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void insertRemoteDatabase(Vendedor vendedor) {
        try {
            servicio.create(vendedor);
        } catch (OperacionInvalidaException ex) {
            context.setRollbackOnly();        
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteRemoteDatabase(Vendedor vendedor) {
        try {
            servicio.delete(vendedor);
        } catch (OperacionInvalidaException ex) {
            context.setRollbackOnly();        
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void comprar (Mueble mueble, TarjetaCredito tc) {
        
    }
}

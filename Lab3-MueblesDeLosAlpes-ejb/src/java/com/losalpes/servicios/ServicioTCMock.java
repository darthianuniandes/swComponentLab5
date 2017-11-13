/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import com.losalpes.entities.TarjetaCredito;
import com.losalpes.excepciones.OperacionInvalidaException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author darthian
 */
@Stateless
public class ServicioTCMock implements IServicioTCMockLocal, IServicioTCMockRemote {
    
    @EJB
    private IServicioPersistenciaMockLocal persistencia;

    public ServicioTCMock() {
    }
    
    @Override
    public void agregarTC(TarjetaCredito tc)throws OperacionInvalidaException {
        try {
            persistencia.create(tc);
        }
        catch (OperacionInvalidaException ex) {
            throw new OperacionInvalidaException(ex.getMessage());
        }
    }

    @Override
    public void eliminarTC(long id)throws OperacionInvalidaException {
        TarjetaCredito v = (TarjetaCredito) persistencia.findById(TarjetaCredito.class, id);
        try {
            persistencia.delete(v);
        } catch (OperacionInvalidaException ex) {
            throw new OperacionInvalidaException(ex.getMessage());
        }
    }

    @Override
    public List<TarjetaCredito> getTC() {
        return persistencia.findAll(TarjetaCredito.class);
    }
}

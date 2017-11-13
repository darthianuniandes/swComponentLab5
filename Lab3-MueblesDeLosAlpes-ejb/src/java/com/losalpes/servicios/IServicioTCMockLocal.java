/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import com.losalpes.entities.TarjetaCredito;
import com.losalpes.excepciones.OperacionInvalidaException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author darthian
 */
@Local
public interface IServicioTCMockLocal {

    public void agregarTC(TarjetaCredito vendedor) throws OperacionInvalidaException;

    public void eliminarTC(long id) throws OperacionInvalidaException;

    public List<TarjetaCredito> getTC();
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.excepciones;

import javax.ejb.ApplicationException;

/**
 *
 * @author darthian
 */
@ApplicationException(rollback = true)
public class CupoInsuficienteException extends Exception {
    
    private String mensaje;
    
    public CupoInsuficienteException(String message) {
        super(message);
    }
    
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

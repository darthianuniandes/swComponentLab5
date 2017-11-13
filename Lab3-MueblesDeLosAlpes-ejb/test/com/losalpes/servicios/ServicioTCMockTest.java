/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import com.losalpes.entities.TarjetaCredito;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author darthian
 */
public class ServicioTCMockTest {
    
    private IServicioTCMockRemote servicio;
    
    
    @Before
    public void setUp() {
        try {
            Properties env = new Properties();
            env.put("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
            env.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
            env.put("org.omg.CORBA.ORBInitialPort", "3700");
            InitialContext contexto;
            contexto = new InitialContext(env);
            servicio = (IServicioTCMockRemote) contexto.lookup("com.losalpes.servicios.IServicioTCMockRemote");
        } 
        catch (NamingException e){
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void testAgregarTC() throws Exception {
        TarjetaCredito tc = new TarjetaCredito();
        tc.setCupo((long) 100000);
        tc.setId((long) 2);
        tc.setNombre("ISM");
        tc.setNumero("123");
        tc.setNombreBanco("Banco de los Alpes :v");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("23/09/2015");
        long time = date.getTime();
        tc.setFechaExp(new Timestamp(time));
        date = dateFormat.parse("24/09/2019");
        time = date.getTime();
        tc.setFechaVen(new Timestamp(time));
        servicio.agregarTC(tc);
        assertTrue(true);
    }
    
    @After
    public void tearDown() {
        
    }
}

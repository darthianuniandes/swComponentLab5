/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import com.losalpes.entities.ExperienciaVendedor;
import com.losalpes.entities.Mueble;
import com.losalpes.entities.TipoMueble;
import com.losalpes.entities.TipoUsuario;
import com.losalpes.entities.Usuario;
import com.losalpes.entities.Vendedor;
import com.losalpes.excepciones.CupoInsuficienteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author darthian
 */
public class PersistenciaCMTTest extends TestCase {
    private Usuario usuario;
    private ArrayList<Mueble> inventario;
    
    @EJB
    private IPersistenciaBMTRemote istancia;
    
    public PersistenciaCMTTest() {
        
    }
    
    @Before
    @Override
    public void setUp() throws NamingException {
        inventario = new ArrayList<Mueble>();
        inventario.add(new Mueble(1L, "Silla clásica", "Una confortable silla con estilo del siglo XIX.", TipoMueble.Interior, 1, "", 123));
        inventario.add(new Mueble(2L, "Silla moderna", "Lo último en la moda de interiores. Esta silla le brindará la comodidad e innovación que busca", TipoMueble.Interior, 1, "", 5464));
        inventario.add(new Mueble(3L, "Mesa de jardín", "Una bella mesa para comidas y reuniones al aire libre.", TipoMueble.Exterior, 1, "", 4568));
        
        usuario = new Usuario("batman", "batipass", TipoUsuario.Cliente);
        
        
            Properties env = new Properties();
            env.put("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
            env.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
            env.put("org.omg.CORBA.ORBInitialPort", "3700");
            InitialContext contexto;
            contexto = new InitialContext(env);
            istancia = (IPersistenciaBMTRemote) contexto.lookup("com.losalpes.servicios.IPersistenciaBMTRemote");       

    }
    
    /**
     * test para el metodo comprar
     */
    @Test
    public void testInsertRemoteDataBase() {
        
        Vendedor vendedor = new Vendedor();
        
        vendedor.setApellidos("appellidos");
        vendedor.setComisionVentas(40000);
        vendedor.setFoto("foto");
        Random rand = new Random();
        int  n = rand.nextInt(50) + 1;
        vendedor.setIdentificacion(n);
        vendedor.setNombres("nombres");
        vendedor.setPerfil("perfil");
        vendedor.setSalario(300000);
        List<ExperienciaVendedor> experiencia = new ArrayList<ExperienciaVendedor>();
        vendedor.setExperiencia(experiencia);
//        PersistenciaBMT istancia2 = new PersistenciaBMT();
        istancia.insertRemoteDatabase(vendedor);
//        istancia2.insertRemoteDatabase(vendedor);
        assertTrue("Compra Correcta", true);
    }
    
    /**
     * test para el metodo comprar
     * 
     */     
    @Test
    public void testComprar() {
        
        try {
//            PersistenciaBMT istancia = new PersistenciaBMT();
            istancia.comprar(usuario, inventario, 1000);
            assertTrue("Compra Correcta", true);
            
        } catch (CupoInsuficienteException ex) {
            System.out.println("Sale una excpcion de cupo");
            assertTrue("Cupo insuficiente", false);
        }
    }
}

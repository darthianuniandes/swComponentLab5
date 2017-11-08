/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$ Vendedor.java
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 * Licenciado bajo el esquema Academic Free License version 3.0
 *
 * Ejercicio: Muebles de los Alpes
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.losalpes.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase que modela un vendedor dentro del sistema.
 */
@Entity
@Table(name="LAB4_VENDEDOR")
public class Vendedor implements Serializable
{

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------

    /**
     * Número de identificación del vendedor
     */
    @Id
    @Column(name="ID")
    private long id;

    /**
     * Nombres del vendedor.
     */
    @Column(name="NOMBRES")
    private String nombres;

    /**
     * Apellidos del vendedor.
     */
    @Column(name="APELLIDOS")
    private String apellidos;

    /**
     * Lista de ítems de experiencia del vendedor.
     */
    @OneToMany(cascade=CascadeType.ALL) 
    private List<ExperienciaVendedor> experiencia;

    /**
     * Salario del vendedor.
     */
    @Column(name="SALARIO")
    private double salario;

    /**
     * Comisión por ventas para el vendedor.
     */
    @Column(name="COMISION_VENTA")
    private double comisionVentas;

    /**
     * Perfil de vendedor.
     */
    @Column(name="PERFIL")
    private String perfil;

    /**
     * Foto del vendedor.
     */
    @Column(name="FOTO")
    private String foto;

    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------

    /**
     * Constructor sin argumentos
     */
    public Vendedor() {
        experiencia = new ArrayList<ExperienciaVendedor>();
    }

    /**
     * Constructor con argumentos de la clase
     * @param id Identificador único del vendedor
     * @param nombres Nombre(s) del vendedor
     * @param apellidos Apellido(s) del vendedor
     * @param experiencia Lista con la experiencia laboral del vendedor
     * @param salario Salario del vendedor
     * @param comisionVentas Valor en comisión por ventas
     * @param perfil Perfil del vendedor
     * @param foto Nombre de la foto del vendedor
     */
    public Vendedor(long id, String nombres, String apellidos, List<ExperienciaVendedor> experiencia, double salario, double comisionVentas, String perfil, String foto)
    {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.experiencia = experiencia;
        this.salario = salario;
        this.comisionVentas = comisionVentas;
        this.perfil = perfil;
        this.foto = foto;
    }

    //-----------------------------------------------------------
    // Getters y setters
    //-----------------------------------------------------------

    /**
     * Devuelve el número único de identificación del vendedor
     * @return id Número de identificación
     */
    public long getIdentificacion()
    {
        return id;
    }

    /**
     * Modifica el número de identificación del cliente
     * @param id Nuevo número de identificación
     */
    public void setIdentificacion(long id)
    {
        this.id = id;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public double getComisionVentas() {
        return comisionVentas;
    }

    public void setComisionVentas(double comisionVentas) {
        this.comisionVentas = comisionVentas;
    }

    public List<ExperienciaVendedor> getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(List<ExperienciaVendedor> experiencia) {
        this.experiencia = experiencia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setItemExperiencia(ExperienciaVendedor experiencia)
    {
        this.experiencia.add(experiencia);
    }

}
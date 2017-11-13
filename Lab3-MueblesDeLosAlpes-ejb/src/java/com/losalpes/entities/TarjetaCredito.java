/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 *
 * @author darthian
 */
@Entity
@Table(name="LAB4_TARJETA_CREDITO")
public class TarjetaCredito implements Serializable {

    private static final long serialVersionUID = 1L;
     
    @Id
    private Long id;
    
    private String numero;
    
    private String nombreTitular;
    
    private String nombreBanco;
    
    private Timestamp fechaExp;
    
    private Timestamp fechaVen;
    
    private Long cupo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombreTitular;
    }

    public void setNombre(String nombre) {
        this.nombreTitular = nombre;
    }

    public Long getCupo() {
        return cupo;
    }

    public void setCupo(Long cupo) {
        this.cupo = cupo;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public Timestamp getFechaExp() {
        return fechaExp;
    }

    public void setFechaExp(Timestamp fechaExp) {
        this.fechaExp = fechaExp;
    }

    public Timestamp getFechaVen() {
        return fechaVen;
    }

    public void setFechaVen(Timestamp fechaVen) {
        this.fechaVen = fechaVen;
    }
}
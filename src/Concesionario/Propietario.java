/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Concesionario;

/**
 *
 * @author DAW
 */
public class Propietario {
    private int id_prop;
    private String nombre_prop;
    private String dni_prop;

    public Propietario(int id_prop, String nombre_prop, String dni_prop) {
        this.id_prop = id_prop;
        this.nombre_prop = nombre_prop;
        this.dni_prop = dni_prop;
    }

    public int getId_prop() {
        return id_prop;
    }

    public void setId_prop(int id_prop) {
        this.id_prop = id_prop;
    }

    public String getNombre_prop() {
        return nombre_prop;
    }

    public void setNombre_prop(String nombre_prop) {
        this.nombre_prop = nombre_prop;
    }

    public String getDni_prop() {
        return dni_prop;
    }

    public void setDni_prop(String dni_prop) {
        this.dni_prop = dni_prop;
    }
    
    
}

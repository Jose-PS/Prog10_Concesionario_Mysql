/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Concesionario;

import com.prog11.princ.Prog11_Principal;

/**
 *
 * @author DAW
 */
public class Vehiculo {
    private String mat_veh;
    private String marca_veh;
    private int kms_veh;
    private float precio_veh;
    private String desc_veh;
    private int id_prop;

    public Vehiculo(String mat_veh, String marca_veh, int kms_veh, float precio_veh, String desc_veh, int id_prop) {
        this.mat_veh = mat_veh;
        this.marca_veh = marca_veh;
        this.kms_veh = kms_veh;
        this.precio_veh = precio_veh;
        this.desc_veh = desc_veh;
        this.id_prop = id_prop;
    }

    public String getMat_veh() {
        return mat_veh;
    }

    public void setMat_veh(String mat_veh) {
        this.mat_veh = mat_veh;
    }

    public String getMarca_veh() {
        return marca_veh;
    }

    public void setMarca_veh(String marca_veh) {
        this.marca_veh = marca_veh;
    }

    public int getKms_veh() {
        return kms_veh;
    }

    public void setKms_veh(int kms_veh) {
        this.kms_veh = kms_veh;
    }

    public float getPrecio_veh() {
        return precio_veh;
    }

    public void setPrecio_veh(float precio_veh) {
        this.precio_veh = precio_veh;
    }

    public String getDesc_veh() {
        return desc_veh;
    }

    public void setDesc_veh(String desc_veh) {
        this.desc_veh = desc_veh;
    }

    public int getId_prop() {
        return id_prop;
    }

    public void setId_prop(int id_prop) {
        this.id_prop = id_prop;
    }
    
    

    @Override
    public String toString() {
        return 
               "MATRICULA: "+mat_veh+"\n"
              +"MARCA: "+marca_veh+"\n"
              +"KILOMETROS: "+kms_veh+"\n"
              +"PRECIO: "+precio_veh+"\n"
              +"DESCRIPCION: "+desc_veh+"\n"
              +"PROPIETARIO: "+Prog11_Principal.conce.getOwnerName(id_prop);
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Concesionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mariadb.jdbc.Connection;

/**
 *
 * @author DAW
 */
public class Concesionario {
    private HashMap<String, Vehiculo> coches=new HashMap<>();
    private TreeMap<Integer, Propietario> propietarios=new TreeMap<>();
    
    public Concesionario(){
        
    }

    
    
    public void loadCars(Connection con) {
        String mat_veh;
        String marca_veh;
        int kms_veh;
        float precio_veh;
        String desc_veh;
        int id_prop;
        try ( PreparedStatement stt = con.prepareStatement("SELECT * FROM VEHICULOS")) {
            ResultSet rs=stt.executeQuery();
            while(rs.next()){
                mat_veh=rs.getString("MAT_VEH");
                marca_veh=rs.getString("MARCA_VEH");
                kms_veh=rs.getInt("KMS_VEH");
                precio_veh=rs.getFloat("PRECIO_VEH");
                desc_veh=rs.getString("DESC_VEH");
                id_prop=rs.getInt("ID_PROP");
                coches.put(mat_veh, new Vehiculo(mat_veh, marca_veh, kms_veh, precio_veh, desc_veh, id_prop));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Concesionario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadOwners(Connection con) {
        int id_prop;
        String nombre_prop;
        String dni_prop;
        try (PreparedStatement stt=con.prepareStatement("SELECT * FROM PROPIETARIOS")){
            ResultSet rs=stt.executeQuery();
            while(rs.next()){
                id_prop=rs.getInt("ID_PROP");
                nombre_prop=rs.getString("NOMBRE_PROP");
                dni_prop=rs.getString("DNI_PROP");
                propietarios.put(id_prop, new Propietario(id_prop, nombre_prop, dni_prop));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Concesionario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getOwnerName(int id_prop) {
        return propietarios.get(id_prop).getNombre_prop();
    }
    
    

    public ArrayList<String> getOwnerVehicles(String dni_prop) {
        ArrayList<String> lista=new ArrayList<>();
        int cont=0;
        for (Propietario p : propietarios.values()) {
            if (p.getDni_prop().equals(dni_prop)) {
                for (Vehiculo v : coches.values()) {
                    if (v.getId_prop() == p.getId_prop()) {
                        cont++;
                        lista.add("********* Vehiculo " + cont + " *********");
                        lista.add(v.toString());
                        lista.add("------------------------------");
                    }
                }
            }
        }
        return lista;
    }

    public ArrayList<String> getByBrand(String marca_veh) {
        ArrayList<String> lista=new ArrayList<>();
        int cont = 0;
        for (Vehiculo v : coches.values()) {
            if (v.getMarca_veh().equals(marca_veh.toUpperCase())) {
                cont++;
                lista.add("********* Vehiculo " + cont + " *********");
                lista.add(v.toString());
                lista.add("------------------------------");
            }
        }
        return lista;
    }

    public ArrayList<String> getAllVehicles() {
        ArrayList<String> lista = new ArrayList<>();
        int cont = 0;
        for (Vehiculo v : coches.values()) {
            cont++;
            lista.add("********* Vehiculo " + cont + " *********");
            lista.add(v.toString());
            lista.add("------------------------------");
        }
        return lista;
    }


    

    


    
    
    
}

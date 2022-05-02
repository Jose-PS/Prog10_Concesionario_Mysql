/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prog11.bbdd;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mariadb.jdbc.Connection;


/**
 *
 * @author DAW
 */
public class VehiculosDAO {
    
    /**
     * Recibe por parámetro los datos del vehículo a insertar, trata de insertarlo en la BBDD,
     * devuelve 0 si la operación se realizó con éxito o -1 en caso contrario.
     * @param mat_veh
     * @param marca_veh
     * @param kms_veh
     * @param precio_veh
     * @param desc_veh
     * @param id_prop
     * @param con
     * @return 
     */
    public static int addVehicle(String mat_veh, String marca_veh, int kms_veh, float precio_veh, String desc_veh, int id_prop, Connection con){
        String sql="INSERT INTO VEHICULOS VALUES ('"+mat_veh+"', '"+marca_veh+"', "+kms_veh+", "+precio_veh+", '"+desc_veh+"', "+id_prop+")";
        try (PreparedStatement stt= con.prepareStatement(sql)){
            if (stt.execute()){
            return 0;
            }else return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }
    /**
     * Recibe por parámetro la matrícula de un vehículo junto al identificador de un propietario 
     * y trata de actualizar el vehículo en la BBDD.Devuelve 0 si la operación se realizó con éxito o -1 si el vehículo no existe.
     * @param mat_veh
     * @param id_prop
     * @param con
     * @return 
     */
    public static int updateOwner (String mat_veh, int id_prop, Connection con){
        String sql="UPDATE TABLE VEHICULOS SET ID_PROP="+id_prop+"WHERE MAT_VEH='"+mat_veh+"'";
        try (PreparedStatement stt= con.prepareStatement(sql)){
            stt.executeUpdate();
            return 0;
        } catch (SQLException ex) {
            return -1;
        }

    }
    /**
     * Recibe por parámetro la matrícula de un vehículo y trata de eliminarlo de la BBDD.Devuelve 0 si la operación se realizó con éxito o -1 si el vehículo no existe.
     * @param mat_veh
     * @param con
     * @return 
     */
    public static int deleteVehicle (String mat_veh, Connection con){
        String sql="DELETE FROM VEHICULOS WHERE MAT_VEH='"+mat_veh+"'";
        try (PreparedStatement stt= con.prepareStatement(sql)){
            stt.executeUpdate();
            return 0;
        } catch (SQLException ex) {
            return -1;
        }

    }
    /**
     * No recibe parámetros y devuelve una lista con todos los vehículos del concesionario.Para cada vehículo, la lista contendrá una cadena de caracteres con los datos del mismo, 
     * incluido el nombre del propietario
     * @param con
     * @return 
     */
    public static ArrayList<String> getAllVehicles (Connection con) {
        String sql="SELECT V.MAT_VEH, V.MARCA_VEH, V.KMS_VEH, V.PRECIO_VEH, V.DESC_VEH, P.NOMBRE_PROP FROM VEHICULOS V, PROPIETARIOS P WHERE V.ID_PROP=P.ID_PROP";
        ArrayList<String> list=new ArrayList<>();
        ResultSet datos=null;
        try (PreparedStatement stt= con.prepareStatement(sql)){
        int cont=0;
        datos = stt.executeQuery();
        while (datos.next()) {
            cont++;
            list.add("********* Vehiculo "+cont+" *********");
            list.add("MATRICULA: " + datos.getString("MAT_VEH"));
            list.add("MARCA: " + datos.getString("MARCA_VEH"));
            list.add("KILOMETROS: " + datos.getInt("KMS_VEH"));
            list.add("PRECIO: " + datos.getFloat("PRECIO_VEH"));
            list.add("DESCRIPCION: " + datos.getString("DESC_VEH"));
            list.add("PROPIETARIO: " + datos.getString("NOMBRE_PROP"));
            list.add("------------------------------");
        }
        } catch (SQLException ex) {
            Logger.getLogger(VehiculosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       return list;     
    }
    /**
     * Recibe una marca por parámetro y devuelve una lista con el listado de vehículos de la citada marca.Para cada vehículo, la lista contendrá una cadena de caracteres con los datos del mismo, 
     * incluido el nombre del propietario.Si no existen vehículos, devuelve el ArrayList vacío.
     * @param marca_veh
     * @param con
     * @return 
     */
    public static ArrayList<String> getByBrand (String marca_veh, Connection con){
        ArrayList<String> list=new ArrayList<>();
        ResultSet datos;
        String sql="SELECT V.MAT_VEH, V.MARCA_VEH, V.KMS_VEH, V.PRECIO_VEH, V.DESC_VEH, P.NOMBRE_PROP FROM VEHICULOS V, PROPIETARIOS P WHERE V.ID_PROP=P.ID_PROP AND MARCA_VEH='"+marca_veh+"'";
        try (PreparedStatement stt=con.prepareStatement(sql)){
        int cont=0;
        datos = stt.executeQuery();
        while (datos.next()) {
            cont++;
            list.add("********* Vehiculo "+cont+" *********");
            list.add("MATRICULA: " + datos.getString("MAT_VEH"));
            list.add("MARCA: " + datos.getString("MARCA_VEH"));
            list.add("KILOMETROS: " + datos.getInt("KMS_VEH"));
            list.add("PRECIO: " + datos.getFloat("PRECIO_VEH"));
            list.add("DESCRIPCION: " + datos.getString("DESC_VEH"));
            list.add("PROPIETARIO: " + datos.getString("NOMBRE_PROP"));
            list.add("------------------------------");
        }
        } catch (SQLException ex) {
            Logger.getLogger(VehiculosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    /**
     * No recibe parámetros (solo la coneción con la BBDD) 
     * y retorna una lista con la matrícula, marca, kilómetros y precio de cada vehículo.
     * @param con
     * @return 
     */
    public static ArrayList<String> getByConnection (Connection con) {
        String sql="SELECT MAT_VEH, MARCA_VEH, KMS_VEH, PRECIO_VEH FROM VEHICULOS";
        ArrayList<String> list=new ArrayList<>();
        ResultSet datos = null;
        try(PreparedStatement stt=con.prepareStatement(sql)){
        datos= stt.executeQuery();
        while (datos.next()){
        list.add(datos.toString());
        } 
        } catch (SQLException ex) {
            Logger.getLogger(VehiculosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
}


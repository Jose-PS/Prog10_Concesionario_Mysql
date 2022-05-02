/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prog11.bbdd;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mariadb.jdbc.Connection;

/**
 *
 * @author DAW
 */
public class PropietariosDAO {
    
    
    /**
     * Recibe por parámetro los datos de un propietario a insertar, 
     * trata de insertarlo en la BBDD y devuelve el id_prop si la operación se realizó con éxito o -1 en caso contrario.
     * @param nombre_prop
     * @param dni_prop
     * @param con
     * @return 
     */
    public static int insertOwner (String nombre_prop, String dni_prop, Connection con) {
        String sql = "INSERT INTO propietarios VALUES ('" + nombre_prop + "', '" + dni_prop + "')";
        int id_prop = 0;
        try ( PreparedStatement stt = con.prepareStatement(sql)) {
            stt.executeUpdate();
            try ( PreparedStatement st = con.prepareStatement("SELECT ID_PROP FROM PROPIETARIOS WHERE DNI_PROP=?")) {
                st.setString(1, dni_prop);
                ResultSet datos = st.executeQuery();
                while (datos.next()) {
                    id_prop = datos.getInt(1);
                    System.out.println(id_prop);
                }
            }
            return id_prop;
        } catch (SQLException ex) {
            return -1;
        }
    }
    
    
    /**
     * Recibe por parámetro el DNI de un propietario y devuelve una lista con 
     * la matrícula, marca, número de kms y precio de todo sus vehículos. 
     * Retornará null si hubo problemas.
     * @param dni_prop
     * @param con
     * @return
     * @throws SQLException 
     */
    public static ArrayList<String> getOwnerVehicles (String dni_prop, Connection con) {
        String sql="SELECT V.MAT_VEH, V.MARCA_VEH, V.KMS_VEH, V.PRECIO_VEH, V.DESC_VEH, P.NOMBRE_PROP FROM VEHICULOS V, PROPIETARIOS P WHERE V.ID_PROP=P.ID_PROP AND DNI_PROP='"+dni_prop+"'";
        ArrayList<String> list=new ArrayList<>();
        ResultSet datos;
        try(PreparedStatement stt=con.prepareStatement(sql)){
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
            Logger.getLogger(PropietariosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    /**
     * Recibe por parámetro el DNI de un propietario y trata de eliminarlo de la BBDD. 
     * Devuelve el número de registros eliminados.
     * @param dni_prop
     * @param con
     * @return 
     */
    public static int deleteOwner (String dni_prop, Connection con ){
        String sql="DELETE FROM PROPIETARIOS WHERE DNI_PROP='"+dni_prop+"'";
        try (PreparedStatement stt= con.prepareStatement(sql)){
            return stt.executeUpdate();
        } catch (SQLException ex) {
            return -1;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prog11.princ;

import Concesionario.Concesionario;
import Utils.CancelException;
import Utils.Inputs;
import com.prog11.bbdd.ConnectionDB;
import com.prog11.bbdd.PropietariosDAO;
import com.prog11.bbdd.VehiculosDAO;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import org.mariadb.jdbc.Connection;

/**
 *
 * @author DAW
 */
public class Prog11_Principal {
    static Scanner lec= new Scanner(System.in);
    public static Concesionario conce=new Concesionario();

    public static void run() {
        char opt = 0;
        try ( Connection con = ConnectionDB.openConnection()) {
            System.out.println("Conectado a base de datos!");
            System.out.println("Cargando datos...");
            conce.loadCars(con);
            conce.loadOwners(con);
            System.out.println("Cargado con exito!");
            do{
            System.out.println("------Talleres Paco------");
            System.out.println("---Escolle unha opcion---");
            System.out.println("""
                               (I)nsertar vehiculo e/ou propietario/s.
                               (A)ctualizar propietario dun veh\u00edculo.
                               (E)liminar.
                               (L)istados.
                               (S)air""");
            opt=lec.nextLine().toLowerCase().charAt(0);
            try{
            switch (opt){
                case 'i'->{
                    inserta(con);
                    break;
                }
                case 'a'->{
                    actualizaProp(con);
                    break;
                }
                case 'e'->{
                    elimina(con);
                    break;
                }
                case 'l'->{
                    lista(con);
                    break;
                }
                case 's'->{
                    break;
                }
            }
            }catch (CancelException ex){
                System.out.println(ex.getCause());
            }
            }while(opt!='s');
        } catch (SQLException ex) {
            System.out.println("Houbo un erro conectando coa base de datos: "+ex.getMessage());
        } 
    }

    private static void inserta(Connection con) throws CancelException {
        char opt=0;
        System.out.println("Insertar (V)ehiculo, (P)ropietario, (S)air.");
        opt=lec.nextLine().toLowerCase().charAt(0);
        switch (opt){
            case 'v'->{
                altaVehiculo(con);
                break;
            }
            case 'p'->{
                altaPropietario(con);
                break;
            }
            case 's'->{
                break;
            }
        }
    }

    private static void actualizaProp(Connection con) throws CancelException {
        boolean ok = false;
        System.out.println("Introduce a matricula e o ID do novo propietario, (*) Cancelar.");
        String mat_veh = Inputs.getString("Matricula:");
        int id_prop = Inputs.getInt("ID do propietario:");
        VehiculosDAO.updateOwner(mat_veh, id_prop, con);
    }

    private static void elimina(Connection con) throws CancelException {
        String mat_veh=null;
        String dni_prop=null;
        char opt=0;
        System.out.println("Eliminar (V)ehiculo, (P)ropietario, (S)air.");
        opt=lec.nextLine().toLowerCase().charAt(0);
        switch (opt){
            case 'v'->{
                mat_veh=Inputs.getString("Introduce a matricula, (*) Cancelar.");
                System.out.println(VehiculosDAO.deleteVehicle(mat_veh, con)+"rexistros eliminados.");
                break;
            }
            case 'p'->{
                dni_prop=Inputs.getString("Introduce o dni, (*) Cancelar.");
                System.out.println(PropietariosDAO.deleteOwner(dni_prop, con)+"rexistros eliminados.");
                break;
            }
            case 's'->{
                break;
            }
        }
    }

    private static void lista(Connection con) throws SQLException, CancelException {
        ArrayList<String> listado;
        char opt=0;
        String marca_veh=null;
        String dni_prop=null;
        System.out.println("""
                           Listado de:
                           (T)odos os Vehiculos
                           (V)ehiculos por marca
                           (P)or propietario
                           (S)air.""");
        opt=lec.nextLine().toLowerCase().charAt(0);
        switch (opt){
            case 't'->{
                listado=conce.getAllVehicles();
//                listado=VehiculosDAO.getAllVehicles(con);
                listado.forEach(s -> {
                    System.out.println(s);
            });
                System.out.println("Exportar txt? (S)i (N)on");
                if (lec.nextLine().toLowerCase().charAt(0)=='s'){
                    exportaTxt(listado);
                }
                break;
            }
            case 'v'->{
                marca_veh=Inputs.getString("Introduce a marca, (*) Cancelar.");
                listado=conce.getByBrand(marca_veh);
//                listado=VehiculosDAO.getByBrand(marca_veh, con);
                listado.forEach(s -> {
                    System.out.println(s);
            });
                break;
            }
            case 'p'->{
                dni_prop=Inputs.getString("Introduce o dni, (*) Cancelar.");
                listado=conce.getOwnerVehicles(dni_prop);
//                listado=PropietariosDAO.getOwnerVehicles(dni_prop, con);
                listado.forEach(s -> {
                    System.out.println(s);
            });
                break;
            }
            case 's'->{
                break;
            }
        }
    }

    private static void altaVehiculo(Connection con) throws CancelException {
        String mat_veh = null;
        String marca_veh = null;
        int kms_veh = 0;
        float precio_veh = 0;
        String desc_veh = null;
        int id_prop = -2;
        String nombre_prop = null;
        String dni_prop = null;
        char opt = 0;
        System.out.println("Queres dar de alta un novo propietario? (S)i (N)on.");
        opt=lec.nextLine().toLowerCase().charAt(0);
            if (opt=='s'){
                id_prop=altaPropietario(con);
            }
                mat_veh = Inputs.getString("Matricula, (*) Cancelar.:");
                marca_veh = Inputs.getString("Marca, (*) Cancelar.:");
                kms_veh = Inputs.getInt("Kilometros, (*) Cancelar.:");
                precio_veh = Inputs.getFloat("Prezo, (*) Cancelar.:");
                desc_veh = Inputs.getString("Descripcion, (*) Cancelar.:");
                if (id_prop<-1){
                id_prop=Inputs.getInt("Introduce o ID do propietario, (*) Cancelar.");
                }
                VehiculosDAO.addVehicle(mat_veh, marca_veh, kms_veh, precio_veh, desc_veh, id_prop, con);
                conce.loadCars(con);
        }
        
        
  
    

    private static int altaPropietario(Connection con) throws CancelException {
        int num=0;
        String nombre_prop=null;
        String dni_prop=null;
        System.out.println("Introduce os datos do propietario, (*) Cancelar.");
        nombre_prop=Inputs.getString("Nome, (*) Cancelar.:");
        dni_prop=Inputs.getString("DNI, (*) Cancelar.:");
        num=PropietariosDAO.insertOwner(nombre_prop, dni_prop, con);
        conce.loadOwners(con);
        return num;
    }

    private static void exportaTxt(ArrayList<String> listado) {
        try (PrintWriter lista=new PrintWriter("ListadoVehiculos.txt")){
            listado.forEach(s -> {
                lista.println(s);
            });
        } catch (FileNotFoundException ex) {
        }
    }

    public static Concesionario getConce() {
        return conce;
    }
    
    public static void main(String[] args) {
        run();
                
    }
}

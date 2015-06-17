/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcalcclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Alberto
 */
public class Usuarios {
    private static final String NOMFICHERO = "usuarios";
    private static final String SEPARADOR  = ";";
    private String usuario;
    private String pass;

    public Usuarios() {
    }

    public Usuarios(String nombre, String pass) {
        this.usuario = nombre;
        this.pass = pass;
    }
    public static boolean comprobarUsuario(String usuario, String pass, ArrayList<Usuarios> lista){
        boolean comprobar = false;
        for(Usuarios u1 : lista){
            if(u1.usuario.equals(usuario) && u1.pass.equals(pass)){
                comprobar = true;
            }
        }
        return comprobar;
    }
    public static ArrayList<Usuarios> cargarUsuarios() throws FileNotFoundException, IOException{
        ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
        try{
            File f = new File(NOMFICHERO);
            if (f.exists()){
                FileReader fr = new FileReader(NOMFICHERO);
                BufferedReader br = new BufferedReader(fr);
                String linea ;
                lista = new ArrayList<Usuarios>();
                while ((linea = br.readLine())!= null){
                    String tokens[] = linea.split(SEPARADOR);
                    Usuarios obj = new Usuarios(tokens[0], tokens[1]);
                    lista.add(obj);
                }
                fr.close();
            }
            
        } catch(Exception ex){
            System.out.println(ex.toString());
        }
        return lista;
    }
}
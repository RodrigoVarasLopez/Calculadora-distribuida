/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcalccontroller;

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
public class ServidoresCalc {
    private static final String NOMFICHERO = "servidores";
    private static final String SEPARADOR  = ";";
    private String direccion;
    private String puerto;

    public ServidoresCalc() {
    }

    public ServidoresCalc(String nombre, String pass) {
        this.direccion = nombre;
        this.puerto = pass;
    }

    public static ArrayList<ServidoresCalc> cargarServidores() throws FileNotFoundException, IOException{
        ArrayList<ServidoresCalc> lista = new ArrayList<ServidoresCalc>();
        try{
            File f = new File(NOMFICHERO);
            if (f.exists()){
                FileReader fr = new FileReader(NOMFICHERO);
                BufferedReader br = new BufferedReader(fr);
                String linea ;
                lista = new ArrayList<ServidoresCalc>();
                while ((linea = br.readLine())!= null){
                    String tokens[] = linea.split(SEPARADOR);
                    ServidoresCalc obj = new ServidoresCalc(tokens[0], tokens[1]);
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

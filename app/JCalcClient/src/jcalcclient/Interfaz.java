/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcalcclient;

/**
 *
 * @author Axtro
 */
public class Interfaz {
    
    public static void showmenu(){
        System.out.println("Operaciones:\n"
                +"1. Aproximacion a E \n"
                +"2. Media Geometrica\n"
                +"3. Media Aritmetica\n"
                +"4. op X2Y21Z3\n"
                +"5. Operacion de segundo grado\n"
                +"6. Varianza\n"
                +"7. Desviación típica\n"
                +"8. Moda\n"
                +"s. Salir\n"
                +"Seleccione la operación desada:"
            );
        
    }
    public static boolean isvalid (int ch){
        if (ch <'1'| ch>'8'& ch !='s') return false;
        else return true;
    }
    public static String respuesta(char ch){
        if (ch == '1') return "aproximacionE";
        if (ch == '2') return "medGeometrica";
        if (ch == '3') return "media";
        if (ch == '4') return "opX2Y21Z3";
        if (ch == '5') return "segundoGrado";
        if (ch == '6') return "varianza";
        if (ch == '7') return "desviacion";
        if (ch == '8') return "moda";
        if (ch == 's') return "salir";
        else return "fallo";  
    }
}

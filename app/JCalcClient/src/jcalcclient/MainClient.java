/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcalcclient;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

import protocol.common.*;
import protocol.clientcontroller.*;
import protocol.clientcalculator.*;
/**
 *
 * @author root
 */
public class MainClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        
        // TODO code application logic here
       char respuesta = 'o';
       char salto; 
       int contador = 0;
       String usuario, pass;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            if(contador == 3){System.out.println("Ha superado el nÃºmero de intentos");break;}
            System.out.println("Nombre usuario?");
            usuario = br.readLine();
            System.out.println("ContraseÃ±a?");
            pass = br.readLine();
            ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
            lista = Usuarios.cargarUsuarios();
            int login = 0;
            if(Usuarios.comprobarUsuario(usuario, pass, lista)){
                while (true){

                    do{
                        Interfaz.showmenu();
                        respuesta = (char) System.in.read();
                    }while (!Interfaz.isvalid(respuesta));
                    salto= (char) System.in.read();

                    if (Interfaz.optionName(respuesta)=="salir") break;
                    else{
                        try {
                            //System.out.println("prueba");
                            Socket sc = new Socket("localhost",7000);   // Controller            

                            ClientControllerProtocol ccp = new ClientControllerProtocol(sc);
                            ccp.initClient();

                            Request rq = ccp.createCalculationRequest(Interfaz.optionName(respuesta));
                            ccp.sendRequest(rq);
                            Response rs = ccp.receiveResponse();

                            rq = new Request();
                            rq.setSubtype("_JCALC_END_");
                            ccp.sendRequest(rq);

                            if( rs.getSubtype().compareTo("_JCALC_CALCULATION_ERROR_")==0 ) {
                                System.out.println("Error");
                            }
                            else if( rs.getSubtype().compareTo("_JCALC_CALCULATION_OK_")==0 ) {

                                Calculation calc = (Calculation)rs.getData().get(0);

                                for(Operation op : calc.getOperaciones()) {                    
                                    TaskOperation task = new TaskOperation(op, calc);
                                    Thread th = new Thread((Runnable) task);
                                    th.start();
                                    th.join();
                                    if( task.getError()==null ) {
                                        calc.updateOperation( task.getResult() );                        
                                    }                                        
                                }  // for Operation

                                // El resultado del calculo es el de la Ãºltima operacion
                                Operation last = calc.getOperaciones().get(calc.getOperaciones().size()-1);
                                if (Interfaz.optionName(respuesta)=="segundoGrado"){
                                    calc.setResult( last.getResult() );
                                    Double[] res = new Double[2];
                                    res=(Double[]) calc.getResult();
                                    System.out.println("Exito. Resultado1: " + res[0]);
                                    System.out.println("Exito. Resultado2: " + res[1]);   
                                }
                                else{
                                    if (last.getError()==null){
                                        calc.setResult( last.getResult() );
                                        System.out.println("Exito. Resultado: " + calc.getResult() );
                                    }
                                    else  System.out.println(last.getError().msg);
                                }
                                sc.close();
                            }           
                        }
                        catch(UnknownHostException e) {
                        }
                        catch(IOException e) {
                        }
                    }
                }
            }else{
                System.out.println("Introduce los datos acceso correctamente");
                contador++;
            }
            if (Interfaz.optionName(respuesta)=="salir") break;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcalcclient;

import java.net.*;
import java.io.*;

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
    public static void main(String[] args) throws IOException, InterruptedException {
        char respuesta, salto;
        String operacion=null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
                            Thread th1 = new Thread((Runnable) task);
                            th1.start();
                            th1.join();
                            if( task.getError()==null ) {
                                calc.updateOperation( task.getResult() );                        
                            }                                        
                        }  // for Operation

                        // El resultado del calculo es el de la última operacion
                        Operation last = calc.getOperaciones().get(calc.getOperaciones().size()-1);
                        if (Interfaz.optionName(respuesta)=="segundoGrado"){
                            calc.setResult( last.getResult() );
                            Double[] res = new Double[2];
                            res=(Double[]) calc.getResult();
                            System.out.println("Exito. Resultado1: " + res[0]);
                            System.out.println("Exito. Resultado1: " + res[1]);   
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
    }
}

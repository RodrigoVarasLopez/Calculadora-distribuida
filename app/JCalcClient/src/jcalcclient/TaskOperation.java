/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcalcclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import protocol.clientcalculator.*;
import protocol.common.*;

/**
 *
 * @author root
 */
public class TaskOperation implements Runnable {
    
    private Calculation calc;
    private Operation op;
    private protocol.common.Error err;
    
    //--------------------------------------------------------------------------
    
    public TaskOperation(Operation op, Calculation calc) {
        this.op   = op;
        this.calc = calc;
        this.err  = null;
    }

    //--------------------------------------------------------------------------
    
    public void run() {
        
        try {
            // Abrimos la conexion al servidor calculadora
            Socket st = new Socket(op.getServer().name,op.getServer().port);   
            ClientCalculatorProtocol ctp = new ClientCalculatorProtocol(st);
            ctp.initClient();
            
            this.prepareInput();
            
            // Ejecutamos la operación contra el servidor
            Request crq = ctp.createOperationRequest(op);
            ctp.sendRequest(crq);
            Response crs = ctp.receiveResponse();
            if( crs.getSubtype().compareTo("_JCALC_OPERATION_OK_")==0 ) {                
                // Exito. Se pasa a la siguiente operacion
                this.op = (Operation)crs.getData().get(0);
                crq = new Request();
                crq.setSubtype("_JCALC_END_");
                ctp.sendRequest(crq);   
                st.close();
            }
            else if( crs.getSubtype().compareTo("_JCALC_OPERATION_ERROR_")==0 ) {
                // Fallo. Se aborta el Calculo o se reintenta?
                err = (protocol.common.Error)crs.getData().get(0);
                System.err.println(err.msg);
            }            
        }
        catch(UnknownHostException ex) {  
            err = new protocol.common.Error();
            err.type = "UnknownHost";
        }
        catch(IOException ex) {            
            err = new protocol.common.Error();
            err.type = "IOError";
        }
    }
    
    //--------------------------------------------------------------------------
    
    public protocol.common.Error getError() {
        return this.err;
    }
    
    //--------------------------------------------------------------------------
    
    private void prepareInput() {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            // Preparamos los datos. Leemos por teclado lo que se necesite
            for(InputData inData : op.getInputData()) {
                if( inData.type.compareTo("Number")==0 ) {
                    int salir = 0;
                    do{ 
                        try{
                            System.out.print("Introduzca el valor de " + inData.name + ": ");
                            inData.value = new Float(Float.parseFloat(br.readLine()));
                            salir=1;
                        }catch(NumberFormatException e){
                            salir =2;
                        }
                    }while(salir != 1);
                    salir = 0;
                }
                else if( inData.type.compareTo("Array")==0 ) {
                    int n=0,salir = 0;
                    do{
                        try{
                            System.out.print("Cuantos elementos en el array?: ");
                             n = Integer.parseInt(br.readLine()); 
                            salir=1;
                        }catch(NumberFormatException e){
                            salir = 2;
                        }
                    }while(salir != 1);
                    salir=0;
                   
                    Float[] datos = new Float[n];
                    System.out.println("Introduzca los elementos: ");
                    for(int i=0;i<n;i++) {
                         salir=0;
                        do{
                            try{
                                System.out.println("elemento: " + i);
                                datos[i] = new Float(Float.parseFloat(br.readLine()));
                                salir=1;
                            }catch(NumberFormatException e){   
                                salir = 2;
                            }
                        }while (salir != 1);
                    }
                    inData.value = datos;            
                }
                else if( inData.type.compareTo("Constant")==0 ) {
                }    
                else if( inData.type.compareTo("Input")==0 ) {
                    boolean found = false;
                    int i = 0;
                    Operation op2 = null;
                    while( !found && i<calc.getOperaciones().size() ) {
                        op2 = calc.getOperaciones().get(i);
                        if( op2.getId()!=op.getId() ) {
                            for(InputData inData2 : op2.getInputData()) {
                                if( inData2.name.compareTo(inData.name)==0 ) {
                                    inData.value = inData2.value;
                                    found = true;
                                    break;
                                }
                            }
                        }
                        else {                            
                        }
                        i++;
                    }
                }  
                else if( inData.type.compareTo("Result")==0 ) {
                    for(Operation op2 : calc.getOperaciones()) {
                        if( op2.getId().compareTo(inData.name)==0 ) {
                            inData.value = new Float(((Double)op2.getResult()).floatValue());
                        }
                    }
                }
            }     
        }
        catch(IOException ex) { 
            err = new protocol.common.Error();
            err.type = "ReadInput";            
        }
    }
    
    //--------------------------------------------------------------------------

    public Operation getResult() {
        return this.op;
    }
}

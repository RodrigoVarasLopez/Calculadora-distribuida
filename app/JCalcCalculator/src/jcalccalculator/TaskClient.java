/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcalccalculator;

import java.net.*;
import java.io.*;
import org.apache.commons.lang3.ArrayUtils;

import protocol.common.*;
import protocol.clientcalculator.*;

import compengine.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class TaskClient {
    
    private ClientCalculatorProtocol ccp;
    private FloatEngine fengine;
        
    //--------------------------------------------------------------------------
    
    public TaskClient(ClientCalculatorProtocol ccp)
    {
        this.ccp = ccp;
        this.fengine = new FloatEngine();
    }
    
    //--------------------------------------------------------------------------
    
    public void run()
    {
        // Recibimos peticiones y las procesamos
        boolean end = false;
        Request r = ccp.receiveRequest();
        while( !end && r!=null ) {
            if( r.getSubtype().compareTo("_JCALC_END_")==0 ) {
                end = true;
            }
            else if( r.getSubtype().compareTo("_JCALC_OPERATION_")==0 ) {
                this.cmdOperation(r);
            }                
            r = ccp.receiveRequest();
        }

        // Finalizamos la conexion
        // this.sc.close();
    }
    
    //--------------------------------------------------------------------------
    
    public boolean cmdOperation(Request rq)
    {   
        Response rs = new Response();
        
        Operation op = (Operation)rq.getData().get(0);
        if( op.getType().compareTo("x+y")==0 ) {
            Float[] numeros = new Float[op.getInputData().size()];
            for(int i=0; i<numeros.length; i++) {
                numeros[i] = (Float)op.getInputData().get(i).value;
            }
            float[] datos = ArrayUtils.toPrimitive(numeros);
            Double res = new Double(fengine.sumar(datos));
            op.setResult(res);
            
            rs.setSubtype("_JCALC_OPERATION_OK_");
            rs.addData(op);
            return ccp.sendResponse(rs);
        }
        else if( op.getType().compareTo("x-y")==0 ) {
            
            Float[] numeros = new Float[op.getInputData().size()];
            for(int i=0; i<numeros.length; i++) {
                numeros[i] = (Float)op.getInputData().get(i).value;
            }
            float[] datos = ArrayUtils.toPrimitive(numeros);
            Double res = new Double(fengine.restar(datos));
            op.setResult(res);
            
            rs.setSubtype("_JCALC_OPERATION_OK_");
            rs.addData(op);
            return ccp.sendResponse(rs);
        }
              
         
        else if( op.getType().compareTo("media")==0 ) {
            Float[] numeros =(Float[])op.getInputData().get(0).value;
 
            float[] datos = ArrayUtils.toPrimitive(numeros);
            Double res = new Double(fengine.media(datos));
            op.setResult(res);
            
            rs.setSubtype("_JCALC_OPERATION_OK_");
            rs.addData(op);
            return ccp.sendResponse(rs);
        }
         else if( op.getType().compareTo("varianza")==0 ) {
            Float[] numeros =(Float[])op.getInputData().get(0).value;
 
            float[] datos = ArrayUtils.toPrimitive(numeros);
            Double res = new Double(fengine.varianza(datos));
            op.setResult(res);
            
            rs.setSubtype("_JCALC_OPERATION_OK_");
            rs.addData(op);
            return ccp.sendResponse(rs);
        }
        else if( op.getType().compareTo("mediaGeometrica")==0 ) {
            Float[] numeros =(Float[])op.getInputData().get(0).value;
 
            float[] datos = ArrayUtils.toPrimitive(numeros);
            Double res = null;
            try {
                res = fengine.mediaGeometrica(datos);
            } catch (ComputeEngineException ex) {
                Logger.getLogger(TaskClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            op.setResult(res);
            
            rs.setSubtype("_JCALC_OPERATION_OK_");
            rs.addData(op);
            return ccp.sendResponse(rs);
        }
        else if( op.getType().compareTo("aproximacionE")==0 ) {
            Float n = (Float)op.getInputData().get(0).value;
            double rss = fengine.aproximacionE(n.floatValue());
            Double res = new Double(rss);
            // Double res = new Double(fengine.x2(n.floatValue()));
            op.setResult(res);            
            rs.setSubtype("_JCALC_OPERATION_OK_");
            rs.addData(op);
            return ccp.sendResponse(rs);       
        }

        else if( op.getType().compareTo("x*y")==0 ) {
            
            Float[] numeros = new Float[op.getInputData().size()];
            for(int i=0; i<numeros.length; i++) {
                numeros[i] = (Float)op.getInputData().get(i).value;
            }
            float[] datos = ArrayUtils.toPrimitive(numeros);
            Double res = new Double(fengine.multiplicar(datos));
            op.setResult(res);
            
            rs.setSubtype("_JCALC_OPERATION_OK_");
            rs.addData(op);
            return ccp.sendResponse(rs);
        }
                           
        else if( op.getType().compareTo("x/y")==0 ) {
            Float[] numeros = new Float[op.getInputData().size()];
            for(int i=0; i<numeros.length; i++) {
                numeros[i] = (Float)op.getInputData().get(i).value;
            }
            float[] datos = ArrayUtils.toPrimitive(numeros);
            try {
                Double res = new Double(fengine.dividir(datos));
                op.setResult(res);
            
                rs.setSubtype("_JCALC_OPERATION_OK_");
                rs.addData(op);
            }
            catch(ComputeEngineException e) {
                // Error si se dividio por cero
            }
            return ccp.sendResponse(rs);            
        }        
        else if( op.getType().compareTo("x2")==0 ) {
            
            Float n = (Float)op.getInputData().get(0).value;
            double rss = fengine.x2(n.floatValue());
            Double res = new Double(rss);
            // Double res = new Double(fengine.x2(n.floatValue()));
            op.setResult(res);            
            rs.setSubtype("_JCALC_OPERATION_OK_");
            rs.addData(op);
            return ccp.sendResponse(rs);            
        } 
        else if( op.getType().compareTo("cambioSigno")==0 ) {
            
            Float n = (Float)op.getInputData().get(0).value;
            double rss = fengine.cambioSigno(n.floatValue());
            Double res = new Double(rss);
            // Double res = new Double(fengine.x2(n.floatValue()));
            op.setResult(res);            
            rs.setSubtype("_JCALC_OPERATION_OK_");
            rs.addData(op);
            return ccp.sendResponse(rs);            
        }        
        

      
        else if( op.getType().compareTo("raiz2")==0 ) {
            
            Float n = (Float)op.getInputData().get(0).value;
            double rss = 0;
            try {
                rss = fengine.raiz2(n.floatValue());
            } catch (ComputeEngineException ex) {
                Logger.getLogger(TaskClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            Double res = new Double(rss);
            // Double res = new Double(fengine.x2(n.floatValue()));
            op.setResult(res);            
            rs.setSubtype("_JCALC_OPERATION_OK_");
            rs.addData(op);
            return ccp.sendResponse(rs); 
        }        
        else if( op.getType().compareTo("segundoGrado")==0 ) {
            Float[] numeros = new Float[op.getInputData().size()];
            for(int i=0; i<numeros.length; i++) {
                numeros[i] = (Float)op.getInputData().get(i).value;
            }
            float[] datos = ArrayUtils.toPrimitive(numeros);
            float[] resta= new float[2]; 
            float[] suma= new float[2];
            resta[0] = datos[0];
            resta[1] = datos[2];
            suma[0] = datos [1];
            suma[1] = datos [2];
            try {
                Double[] res = new Double[2];
                res[0]= new Double(fengine.dividir(suma));
                res[1]= new Double(fengine.dividir(resta));
                op.setResult(res);
            
                rs.setSubtype("_JCALC_OPERATION_OK_");
                rs.addData(op);
            }
            catch(ComputeEngineException e) {
                // Error si se dividio por cero
            }
            return ccp.sendResponse(rs);            
        } 
         
        return true;
    }      
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcalccontroller;

import java.net.*;
import java.io.*;

import protocol.common.*;
import protocol.clientcontroller.*;

/**
 *
 * @author fjpalacios
 */
public class TaskClient {
    
    private ClientControllerProtocol ccp;
    
    //--------------------------------------------------------------------------
    
    public TaskClient(ClientControllerProtocol ccp)
    {
        this.ccp = ccp;
    }
    
    //--------------------------------------------------------------------------
    
    public void run()
    {
        // Recibimos peticiones y las procesamos
        boolean end = false;
        boolean success = false;
        Request r = ccp.receiveRequest();
        while( !end && r!=null ) {
            if( r.getSubtype().compareTo("_JCALC_END_")==0 ) {
                end = true;
            }
            else if( r.getSubtype().compareTo("_JCALC_CALCULATION_")==0 ) {
                success = this.cmdCalculation(r);
            }                
            r = ccp.receiveRequest();
        }

        // Finalizamos la conexion
        // this.sc.close();
    }
    
    //--------------------------------------------------------------------------
    
    public boolean cmdCalculation(Request rq)
    {   
        String calculationType = (String)rq.getData().get(0);

        Calculation calc = (new CalculationParser()).parse(calculationType+".xml");

        Response rs = new Response();
        rs.setSubtype("_JCALC_CALCULATION_OK_");
        rs.addData(calc);
        
        return ccp.sendResponse(rs);
    }  
}

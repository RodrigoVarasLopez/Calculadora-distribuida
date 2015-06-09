/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.clientcalculator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import protocol.*;
import protocol.common.*;

/**
 *
 * @author root
 */
public class ClientCalculatorProtocol extends BaseProtocol {
    
    public ClientCalculatorProtocol(Socket s) {
        super(s);
    }
    
    public boolean initClient() {
        try {
            out = new ObjectOutputStream( s.getOutputStream() );
            in  = new ObjectInputStream( s.getInputStream() );
            return true;
        }
        catch(IOException e) {
            return false;
        }
    }
    
    public boolean initCalculator() {
        try {
            in  = new ObjectInputStream( s.getInputStream() );
            out = new ObjectOutputStream( s.getOutputStream() );
            return true;
        }
        catch(IOException e) {
            return false;
        }        
    }   
    
    //--------------------------------------------------------------------------
    
    public Request createOperationRequest(Operation operation)
    {
        Request r = new Request();
        r.setSubtype("_JCALC_OPERATION_");
        r.addData(operation);
        return r;
    }    
}

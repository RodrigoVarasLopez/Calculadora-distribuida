package protocol.clientcontroller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import protocol.*;
import protocol.common.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author root
 */
public class ClientControllerProtocol extends BaseProtocol {
    
    public ClientControllerProtocol(Socket s) {
        super(s);
    }
    
    //--------------------------------------------------------------------------
    
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

    //--------------------------------------------------------------------------
    
    public boolean initController() {
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
    
    public Request createCalculationRequest(String type)
    {
        Request r = new Request();
        r.setSubtype("_JCALC_CALCULATION_");
        r.addData(type);
        return r;
    }
}

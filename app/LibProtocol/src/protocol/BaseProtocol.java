/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol;

import java.net.*;
import java.io.*;

import protocol.common.*;

/**
 *
 * @author root
 */
public class BaseProtocol {
    
    protected Socket s;
    protected ObjectOutputStream out;
    protected ObjectInputStream  in;
    
    //--------------------------------------------------------------------------
    
    public BaseProtocol(Socket s)
    {
        this.s = s;
    }

    //--------------------------------------------------------------------------
    
    public boolean sendRequest(Request request)
    {
        try {
            out.writeObject(request);
            return true;
        }
        catch(IOException ex) {
            return false;
        }
    }

    //--------------------------------------------------------------------------
    
    public Response receiveResponse()
    {
        try {
            Response res = (Response)in.readObject();
            return res;
        }
        catch(IOException ex) {
            return null;
        }        
        catch(ClassNotFoundException ex) {
            return null;
        }        
    }   

    //--------------------------------------------------------------------------
    
    public boolean sendResponse(Response response)
    {
        try {
            out.writeObject(response);
            return true;
        }
        catch(IOException ex) {
            return false;
        }
    }

    //--------------------------------------------------------------------------
    
    public Request receiveRequest()
    {
        try {
            Request res = (Request)in.readObject();
            return res;
        }
        catch(IOException ex) {
            return null;
        }        
        catch(ClassNotFoundException ex) {
            return null;
        }        
    }    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcalccalculator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import protocol.common.*;
import protocol.clientcalculator.*;

/**
 *
 * @author root
 */
public class MainCalculator {

    public static Configuration cfg;
    public static Log log;
    
    //--------------------------------------------------------------------------
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainCalculator app = new MainCalculator();
        app.init(args);
        app.run();           
    }
    
    
    //--------------------------------------------------------------------------
    
    public void init(String[] args)
    {
        MainCalculator.cfg = Configuration.createDefault();
        MainCalculator.log = new Log("screen");

        MainCalculator.log.write("Calculator started");
        
        int i = 0;
        while(i<args.length) {
            if( args[i].compareTo("-h")==0 || args[i].compareTo("--help")==0 ) {
                // Mostrar ayuda por consola
            }
            else if( args[i].compareTo("-p")==0 || args[i].compareTo("--port")==0 ) {
                i++;
                try {
                    MainCalculator.cfg.port = Integer.parseInt(args[i]);
                }
                catch(NumberFormatException ex) {
                    // Default value
                }
            } 
            i++;
        }
        MainCalculator.log.write("Loaded configuration");
    }
    
    //--------------------------------------------------------------------------
    
    public void finish()
    {
        MainCalculator.log.write("Finishing");
    }
    
    //--------------------------------------------------------------------------
    
    public void run()
    { 
        try {
            ServerSocket ss = new ServerSocket(MainCalculator.cfg.port);
            MainCalculator.log.write("Listening at port " + MainCalculator.cfg.port);
            
            while( true ) 
            {
                Socket sc = ss.accept();
                ClientCalculatorProtocol ccp = new ClientCalculatorProtocol(sc);
                ccp.initCalculator();
                new TaskClient(ccp).run();
            }
        }
        catch(IOException ex) {
            MainCalculator.log.write("Socket could not be created");
        }
        this.finish();
    }   
    
}

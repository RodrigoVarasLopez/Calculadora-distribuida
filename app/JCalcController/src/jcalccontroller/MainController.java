/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcalccontroller;

import java.text.ParseException;
import java.net.*;
import java.io.*;

import protocol.common.*;
import protocol.clientcontroller.*;

/**
 *
 * @author root
 */
public class MainController {

    public static Configuration cfg;
    public static Log log;
    
    //--------------------------------------------------------------------------

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainController app = new MainController();
        app.init(args);
        app.run();        
    }

    //--------------------------------------------------------------------------
    
    public void init(String[] args)
    {
        MainController.cfg = Configuration.createDefault();
        MainController.log = new Log("screen");

        MainController.log.write("Controller started");
        
        int i = 0;
        while(i<args.length) {
            if( args[i].compareTo("-h")==0 || args[i].compareTo("--help")==0 ) {
                // Mostrar ayuda por consola
            }
            else if( args[i].compareTo("-p")==0 || args[i].compareTo("--port")==0 ) {
                i++;
                try {
                    MainController.cfg.port = Integer.parseInt(args[i]);
                }
                catch(NumberFormatException ex) {
                    // Default value
                }
            } 
            i++;
        }
        MainController.log.write("Loaded configuration");
    }
    
    //--------------------------------------------------------------------------
    
    public void finish()
    {
        MainController.log.write("Finishing");
    }
    
    //--------------------------------------------------------------------------
    
    public void run()
    { 
        try {
            ServerSocket ss = new ServerSocket(MainController.cfg.port);
            MainController.log.write("Listening at port " + MainController.cfg.port);
            
            while( true ) 
            {
                Socket sc = ss.accept();
                ClientControllerProtocol ccp = new ClientControllerProtocol(sc);
                ccp.initController();
                new TaskClient(ccp).run();
            }
        }
        catch(IOException ex) {
            MainController.log.write("Socket could not be created");
        }
        this.finish();
    }    
}

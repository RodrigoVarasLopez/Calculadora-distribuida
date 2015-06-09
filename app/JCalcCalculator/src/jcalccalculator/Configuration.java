/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcalccalculator;

/**
 *
 * @author root
 */
public class Configuration {
    
    public int port;
    
    //--------------------------------------------------------------------------
    
    public static Configuration createDefault()
    {
        Configuration cfg = new Configuration();
        cfg.port = 7001;
        
        return cfg;
    }        
}

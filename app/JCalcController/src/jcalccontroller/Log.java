/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcalccontroller;

import java.io.*;
import java.text.*;
import java.util.Date;

/**
 *
 * @author fjpalacios
 */
public class Log {
    
    private PrintStream logsys;
    private SimpleDateFormat sdf;
    
    //--------------------------------------------------------------------------
    
    public Log(String type) {
        sdf = new SimpleDateFormat("Y-M-d H:m:s");
        if( type.compareTo("screen")==0 ) {
            logsys = System.err;
        }
        else if( type.compareTo("file")==0 ) {
            try(FileOutputStream stream = new FileOutputStream("data/controller.log", true)) {
                logsys = new PrintStream(stream);
            }catch (FileNotFoundException e) {                         
            }catch (IOException e) {
            }                
        }
    }

    //--------------------------------------------------------------------------
    
    public void write(String msg) {
        logsys.format("[%s]: %s%n", sdf.format(new Date()), msg);
    }
}

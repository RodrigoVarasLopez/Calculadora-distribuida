/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase Calculation. Representa un calculo complejo que habrá de ejecutarse a 
 * través de calculos sencillos en servidores de tipo Calculadora. Es generado
 * por un servidor Controller a partir de unos ficheros XML
 */
public class Calculation implements Serializable 
{    
    private String type;
    private ArrayList<Operation> operaciones;
    
    private Object result;  // Number or array as a java.util.ArrayList
    private Error error;    

    //--------------------------------------------------------------------------
    
    public Calculation()
    {
        this.operaciones = new ArrayList<Operation>();
        
        this.result = null;
        this.error  = null;
    }

    //--------------------------------------------------------------------------
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    //--------------------------------------------------------------------------
    
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    //--------------------------------------------------------------------------
    
    /**
     * @return the operaciones
     */
    public ArrayList<Operation> getOperaciones() {
        return operaciones;
    }

    //--------------------------------------------------------------------------
    
    public void addOperacion(Operation op) {
        operaciones.add(op);
    }

    //--------------------------------------------------------------------------
    
    /**
     * @return the result
     */
    public Object getResult() {
        return result;
    }

    //--------------------------------------------------------------------------
    
    /**
     * @param result the result to set
     */
    public void setResult(Object result) {
        this.result = result;
    }

    //--------------------------------------------------------------------------
    
    /**
     * @return the error
     */
    public Error getError() {
        return error;
    }

    //--------------------------------------------------------------------------
    
    /**
     * @param error the error to set
     */
    public void setError(Error error) {
        this.error = error;
    }
    
    //--------------------------------------------------------------------------

    public void updateOperation(Operation op) {
        for(int i=0; i<operaciones.size(); i++) {
            if( op.getId().compareTo(operaciones.get(i).getId())==0 ) {
                operaciones.set(i, op);
            }
        }
    }
}

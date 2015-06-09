/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase Operation. Representa una operación de calculo sencilla para ser realizada
 * en un operador de tipo Calculadora.
 */
public class Operation implements Serializable 
{    
    private String id;
    private String type;
    
    private CalculatorServer calcServer;

    private ArrayList<InputData> inputData; // Datos necesarios para la operacion

    private Object result;  // Resultado de la operacion. Number or ArrayList
    private Error error;

    //--------------------------------------------------------------------------
    
    public Operation(String id)
    {
        this.id = id;
        this.inputData = new ArrayList<InputData>();
        this.calcServer = new CalculatorServer();
    }

    //--------------------------------------------------------------------------
    
    public Operation(String id, String server, int port)
    {
        this.id = id;
        this.inputData = new ArrayList<InputData>();
        this.calcServer = new CalculatorServer();
        this.calcServer.name = server;
        this.calcServer.port = port;
    }

    //--------------------------------------------------------------------------

    public String getId()
    {
        return this.id;
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

    public CalculatorServer getServer()
    {
        return this.calcServer;
    }

    //--------------------------------------------------------------------------

    /**
     * @return the data
     */
    public ArrayList<InputData> getInputData() 
    {
        return inputData;
    }

    //--------------------------------------------------------------------------
    
    public void addInputData(String type, String name, Object data) 
    {
        InputData idata = new InputData();
        idata.type  = type;
        idata.name  = name;
        idata.value = data;
        this.inputData.add(idata);
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author root
 */
public class Message implements Serializable {
    
    protected String type;
    protected String subtype;
    protected ArrayList data;

    //--------------------------------------------------------------------------
    
    public Message() {
        data = new ArrayList();
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
     * @return the subtype
     */
    public String getSubtype() {
        return subtype;
    }

    //--------------------------------------------------------------------------
    
    /**
     * @param subtype the subtype to set
     */
    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    //--------------------------------------------------------------------------
    
    /**
     * @return the data
     */
    public ArrayList getData() {
        return data;
    }
    
    //--------------------------------------------------------------------------
    
    public void addData(Object obj)
    {
        data.add(obj);
    }    
}

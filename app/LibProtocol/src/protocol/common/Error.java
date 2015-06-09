/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.common;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class Error implements Serializable {
    public String type;
    public String msg;
}

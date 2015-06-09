/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcalccontroller;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

import protocol.common.*;


/**
 *
 * @author root
 */
public class CalculationParser {
    public static String CALCULATIONDIR = "data/calculations";
    
    public Calculation parse(String name)
    {
        Calculation calc = new Calculation();
        
        try {
            File fXmlFile = new File(CalculationParser.CALCULATIONDIR+"/"+name);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            calc.setType(doc.getDocumentElement().getAttribute("type"));
                    
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();        
            
            NodeList nList = doc.getElementsByTagName("operation");
            for (int i=0; i<nList.getLength(); i++) {
		Node nNode = nList.item(i); 
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String id = eElement.getAttribute("id");
                    String type = eElement.getElementsByTagName("type").item(0).getTextContent();

                    Element eServer = (Element)eElement.getElementsByTagName("server").item(0);
                    String serverName = eServer.getAttribute("name");
                    int serverPort = Integer.parseInt(eServer.getAttribute("port"));

                    Operation op = new Operation(id, serverName, serverPort);
                    op.setType(type);
                    
                    // Result
                    Element eResult = (Element)eElement.getElementsByTagName("result").item(0);
                    if( eResult.getAttribute("type").compareTo("Number")==0 ) {
                        op.setResult( new Double(0.0F) );
                    }
                    else if( eResult.getAttribute("type").compareTo("Array")==0 ) {
                        op.setResult( new ArrayList<Double>() );
                    }
                    
                    // Input. Puede haber varios
                    NodeList inputList = eElement.getElementsByTagName("input");
                    for (int j=0; j<inputList.getLength(); j++) {
                        Node nInput = inputList.item(j);
                        if (nInput.getNodeType() == Node.ELEMENT_NODE) {
                            Element inputElement = (Element) nInput;
                            if( inputElement.getAttribute("type").compareTo("Number")==0 ) {
                                op.addInputData("Number", inputElement.getAttribute("name"), null);    // Se creara en el cliente
                            }
                            else if( inputElement.getAttribute("type").compareTo("Array")==0 ) {
                                op.addInputData("Array", inputElement.getAttribute("name"), null);     // Se creara en el cliente
                            }
                            else if( inputElement.getAttribute("type").compareTo("Constant")==0 ) {
                                op.addInputData("Constant", null, new Float(Float.parseFloat(inputElement.getAttribute("value"))));
                            }                    
                            else if( inputElement.getAttribute("type").compareTo("Result")==0 ) {
                                op.addInputData("Result", inputElement.getAttribute("id"), inputElement.getAttribute("id"));
                            }                        
                            else if( inputElement.getAttribute("type").compareTo("Input")==0 ) {
                                op.addInputData("Input", inputElement.getAttribute("name"), inputElement.getAttribute("name"));
                            }                        
                        }
                    }
                    calc.addOperacion(op);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return calc;
    }
}

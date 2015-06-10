/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compengine;

import java.util.ArrayList;

/**
 *
 * @author profesor
 */
public class FloatEngine {
    
    public FloatEngine()
    {       
    }

    //--------------------------------------------------------------------------
    
    public double sumar(float[] numeros) 
    { 
        double result = 0.0;
        for(int i=0;i<numeros.length;i++)
            result += numeros[i];
        return result;        
    }

    //--------------------------------------------------------------------------
    
    public double restar(float[] numeros)
    {
        double result = numeros[0];
        for(int i=1;i<numeros.length;i++)
            result -= numeros[i];
        return result;
    }

    //--------------------------------------------------------------------------
    
    public double multiplicar(float[] numeros)
    {        
        double result = 1.0;
        for(int i=0;i<numeros.length;i++)
            result *= numeros[i];
        return result;
    }

    //--------------------------------------------------------------------------
    
    public double dividir(float[] numeros) throws ComputeEngineException
    {        
        double result = numeros[0];
        for(int i=1;i<numeros.length;i++) {
            if( numeros[i]==0 ) throw new ComputeEngineException("DIVIDE_BY_ZERO");
            result /= numeros[i];
        }
        return result;
    } 

    //--------------------------------------------------------------------------
    
    public double x2(float x)
    {        
        return (double)(x*x);
    }
     public double cambioSigno(float x)
    {        
        return (double)x-(double)(x+x);
    }   

    //--------------------------------------------------------------------------
    
    public double xey(float x, float y)
    {        
        return Math.pow((double)x, (double)y);
    }   
    
    //--------------------------------------------------------------------------
    
    public double raiz2(float x) throws ComputeEngineException
    {        
        if( x<0.0F ) throw new ComputeEngineException("SQUAREROOT_NEGATIVE");
        return Math.sqrt((double)x);
    }
    
    public double media (float[] operandos){
        double resultado = 0;
        for (float operando: operandos) {
            resultado = resultado + operando;
        }
        resultado = resultado / operandos.length;
        return resultado;
    }
    
    public double varianza (float[] operandos){
        Double media = media(operandos);
        double resultado = 0;
        for (float operando: operandos) {
            resultado =resultado + x2((float) (operando - media));
        }
        resultado = resultado / operandos.length;
        return resultado;
    }
    
    public double moda (float[] operandos){
        double resultado = 0;
        int contador=0, contadorComp=0;
        for (int j = 0; j < operandos.length; j++) {
            contadorComp = 0;
                for (int i = 1; i < operandos.length-1; i++) {
                    if (operandos[i]== operandos[j]){
                        contadorComp++;   
                    }
                    if (contadorComp>contador){
                        contador=contadorComp;
                        resultado = operandos[j];
                    }
                }  
            }       
        return resultado;
    }
    public double mediana (float[] operandos){
        //Metodo de la burbuja
        for (int i = 0; i < (operandos.length - 1); i++) { 
            for (int j = i + 1; j < operandos.length; j++) { 
                if (operandos[j] < operandos[i]) 
                    { 
                    float temp = operandos[j]; 
                      operandos[j] = operandos[i]; 
                      operandos[i] = temp; 
                    } 
                } 
        }
        return operandos[(int)(operandos.length/2)];
    }
    
    public double desviacion (float[] operandos) throws ComputeEngineException{
        Double varianza = varianza(operandos);
        double resultado;
        resultado = raiz2(varianza);
        return resultado;
    }
    
    public double mediaGeometrica (float[] operandos) throws ComputeEngineException{
        double resultado = 1;
        for (float operando: operandos) {
            resultado = resultado * operando;
        }
        if( resultado<0.0D ) throw new ComputeEngineException("SQUAREROOT_NEGATIVE");
        return Math.pow(resultado, 1.0/operandos.length);
    }
     public double aproximacionE(float x){
        double resultado=1;
        
        for (int i = 0; i <= x; i++) {
            resultado= resultado + 1/FloatEngine.factorial(i);      
             }
        return resultado;
     }
     public static  double factorial (int n){
        double resultado = 1;
        for(int i=1; i<=n; i++) {
            resultado *= i;
        }
        return resultado;
     }

    private double raiz2(Double x) throws ComputeEngineException {
        if( x<0.0 ) throw new ComputeEngineException("SQUAREROOT_NEGATIVE");
        return Math.sqrt((double)x);
    }
    
}

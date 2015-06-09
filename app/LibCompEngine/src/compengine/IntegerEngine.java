/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compengine;

/**
 *
 * @author profesor
 */
public class IntegerEngine {
    
    public IntegerEngine()
    {               
    }
    
    //--------------------------------------------------------------------------
    
    public int sumar(int[] numeros) 
    {  
        int result = 0;
        for(int i=0;i<numeros.length;i++)
            result += numeros[i];
        return result;
    }

    //--------------------------------------------------------------------------
    
    public int restar(int[] numeros)
    {
        int result = numeros[0];
        for(int i=1;i<numeros.length;i++)
            result -= numeros[i];
        return result;
    }

    //--------------------------------------------------------------------------
    
    public int multiplicar(int[] numeros)
    {        
        int result = 1;
        for(int i=0;i<numeros.length;i++)
            result *= numeros[i];
        return result;
    }

    //--------------------------------------------------------------------------
    
    public int dividir(int[] numeros) throws ComputeEngineException
    {        
        int result = numeros[0];
        for(int i=1;i<numeros.length;i++) {
            if( numeros[i]==0 ) throw new ComputeEngineException("DIVIDE_BY_ZERO");
            result /= numeros[i];
        }
        return result;
    }    
}

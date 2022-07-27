package conjuntistas;

/**
 *
 * @author sebastian.iovaldi
 */

public class NodoAVLDicc {
    
    Object dato;
    Object clave;
    int altura;
    NodoAVLDicc izquierdo;
    NodoAVLDicc derecho;
    
    public NodoAVLDicc(Object clave, Object dato, NodoAVLDicc izquierdo, NodoAVLDicc derecho)
    {
        this.dato = dato;
        this.clave = clave;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = Math.max( izquierdo==null ? 0 : izquierdo.altura + 1, derecho==null ? 0 : derecho.altura + 1 );
    }
    
    public Object getDato()        
    { 
        return dato; 
    }
    
    public Object getClave()
    {
        return clave;
    }
    
    public NodoAVLDicc getIzquierdo()   
    { 
        return izquierdo; 
    }
    
    public NodoAVLDicc getDerecho()     
    { 
        return derecho; 
    }
    
    public int getAltura()         
    { 
        return altura; 
    }
    
    public void setDato(Object dato)               
    { 
        this.dato = dato; 
    };
    
    public void setIzquierdo(NodoAVLDicc izquierdo)    
    { 
        this.izquierdo = izquierdo; 
    };
    
    public void setDerecho(NodoAVLDicc derecho)        
    { 
        this.derecho = derecho; 
    };
    
    public void recalcularAltura()
    {
        this.altura = Math.max(izquierdo==null ? 0 : izquierdo.getAltura() + 1, derecho==null ? 0 : derecho.getAltura() + 1);
    }
}

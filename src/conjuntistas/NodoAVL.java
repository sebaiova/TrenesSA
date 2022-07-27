package conjuntistas;

/**
 *
 * @author sebastian.iovaldi
 */

public class NodoAVL {
    
    Object elem;
    int altura;
    NodoAVL izquierdo;
    NodoAVL derecho;
    
    public NodoAVL(Object elem, NodoAVL izquierdo, NodoAVL derecho)
    {
        this.elem = elem;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = Math.max( izquierdo==null ? 0 : izquierdo.altura + 1, derecho==null ? 0 : derecho.altura + 1 );
    }
    
    public Object getElem()        
    { 
        return elem; 
    }
    
    public NodoAVL getIzquierdo()   
    { 
        return izquierdo; 
    }
    
    public NodoAVL getDerecho()     
    { 
        return derecho; 
    }
    
    public int getAltura()         
    { 
        return altura; 
    }
    
    public void setElem(Object elem)               
    { 
        this.elem = elem; 
    };
    
    public void setIzquierdo(NodoAVL izquierdo)    
    { 
        this.izquierdo = izquierdo; 
    };
    
    public void setDerecho(NodoAVL derecho)        
    { 
        this.derecho = derecho; 
    };
    
    public void recalcularAltura()
    {
        this.altura = Math.max(izquierdo==null ? 0 : (izquierdo.getAltura() + 1), derecho==null ? 0 : (derecho.getAltura() + 1));
    }
}

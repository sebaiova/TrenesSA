package grafos;

/**
 *
 * @author sebastian.iovaldi
 */

public class NodoVert {
    
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;
    
    public NodoVert(Object obj, NodoVert nodoVert)
    {
        this.sigVertice = nodoVert;
        this.elem = obj;
        this.primerAdy = null;
    }
    
    public Object getElem() {
        return elem;
    }
    
    public void setElem(Object obj) {
        this.elem = obj;
    }

    public NodoVert getSigVertice() {
        return sigVertice;
    }
    
    public void setSigVertice(NodoVert sigVert) {
        this.sigVertice = sigVert;
    }
    
    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }
    
    public void setPrimerAdy(NodoAdy nodoAdy) {
        this.primerAdy = nodoAdy;
    }
    
    @Override
    public String toString() {
        return elem.toString();
    }
    
    @Override
    public boolean equals(Object other) {
        return this.elem.equals(((NodoVert)other).elem);
    }
}

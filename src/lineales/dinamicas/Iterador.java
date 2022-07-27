package lineales.dinamicas;

/**
 *
 * @author sebastian.iovaldi
 */

public class Iterador {
        
    private Nodo nodo;
    
    public Iterador(Nodo nodo) {
        this.nodo = nodo;
    }
    
    public void next() {
        this.nodo = this.nodo.getEnlace();
    }
    
    public boolean end() {
        return nodo == null;
    }
    
    public Object getElem() {
        return this.nodo.getElem();
    }
}

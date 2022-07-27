package lineales.dinamicas;

/**
 *
 * @author sebastian.iovaldi
 */

public class Nodo {
    
    private Object elem;
    private Nodo enlace;
    
    public Nodo(Object object, Nodo enlace) {
        this.elem = object;
        this.enlace = enlace;
    }
    
    public void setElem(Object object) {
        this.elem = object;
    }
    
    public void setEnlace(Nodo link) {
        this.enlace = link;
    }
    
    public Object getElem() {
        return this.elem;
    }
    
    public Nodo getEnlace() {
        return this.enlace;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s", elem, enlace==null? "\n" : ", " + enlace.toString());
    }
}
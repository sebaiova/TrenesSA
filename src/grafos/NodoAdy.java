package grafos;

/**
 *
 * @author sebastian.iovaldi
 */

public class NodoAdy {
    
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private int etiqueta;
    
    public NodoAdy(NodoVert nodoVert, NodoAdy nodoAdy) {
        this.vertice = nodoVert;
        this.sigAdyacente = nodoAdy;
        this.etiqueta = 1;
    }
    
    public NodoAdy(NodoVert nodoVert, NodoAdy nodoAdy, int etiqueta) {
        this.vertice = nodoVert;
        this.sigAdyacente = nodoAdy;
        this.etiqueta = etiqueta;
    }
    
    public NodoVert getVertice() {
        return vertice;
    }
    
    public void setVertice(NodoVert vert) {
        this.vertice = vert;
    } 
    
    public NodoAdy getSigAdyacente() {
        return sigAdyacente;
    }
    
    public void setSigAdyacente(NodoAdy nodo) {
        this.sigAdyacente = nodo;
    }
    
    public int getEtiqueta(){
        return this.etiqueta;
    }
    
    public void setEtiqueta(int tag) {
        this.etiqueta = tag;
    }
}

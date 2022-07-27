package grafos;

/**
 *
 * @author sebastian.iovaldi
 */

public class TuplaDijkstra implements Comparable {
    
    private int distancia;
    final private NodoVert nodo;
    private NodoVert nodoPrev;
    
    public TuplaDijkstra(int distancia, NodoVert nodo, NodoVert nodoPrev) 
    {
        this.distancia = distancia;
        this.nodo = nodo;
        this.nodoPrev = nodoPrev;
    }
    
    public int getDistancia() 
    {
        return distancia;
    }
    
    public NodoVert getNodo() 
    {
        return nodo;
    }

    public NodoVert getPrev() 
    {
        return nodoPrev;
    }
    
    public void setDistancia(int distancia) 
    {
        this.distancia = distancia;
    }
    
    public void setPrev(NodoVert prev) 
    {
        this.nodoPrev = prev;
    }

    @Override
    public boolean equals(Object other) 
    {
        return this.nodo.equals(((TuplaDijkstra)other).nodo);
    }

    @Override
    public int compareTo(Object o) 
    {
        return Integer.compare(this.distancia, ((TuplaDijkstra)o).distancia);
    }
    
    @Override
    public String toString()
    {
        return String.format("[%s - %d - %s]", nodo, distancia, nodoPrev);
    }
}

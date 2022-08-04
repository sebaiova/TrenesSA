package lineales.dinamicas;

/**
 *
 * @author sebastian.iovaldi
 */
public class CeldaCP implements Comparable{
    
    private final int prioridad;
    private final int ordenLlegada;
    private final Object elemento;
    
    public CeldaCP(Object elemento, int prioridad, int ordenLlegada)
    {
        this.elemento = elemento;
        this.prioridad = prioridad;
        this.ordenLlegada = ordenLlegada;
    }
    
    @Override
    public int compareTo(Object o) {
        int comparacion = Integer.compare(this.prioridad, ((CeldaCP)o).prioridad);
        if(comparacion==0)
            comparacion = Integer.compare(this.ordenLlegada, ((CeldaCP)o).ordenLlegada);
        return comparacion;
    }
    
    public Object getElem()
    {
        return this.elemento;
    }
    
}

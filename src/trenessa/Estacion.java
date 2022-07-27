package trenessa;

/**
 *
 * @author sebastian.iovaldi
 */
public class Estacion implements Comparable {
    
    final private String nombre;
    private final Domicilio domicilio;
    private int cantPlataformas;
    private int cantVias;
    
    public Estacion(String nombre) 
    {
        this.nombre = nombre;
        this.domicilio = null;
        this.cantPlataformas = 1;
        this.cantVias = 1;
    }
    
    public Estacion(String nombre, Domicilio domicilio, int plataformas, int vias) 
    {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.cantPlataformas = plataformas;
        this.cantVias = vias;
    }
    
    public Domicilio getDomicilio()
    {
        return domicilio;
    }
    
    public void setPlataformas(int x)
    {
        this.cantPlataformas = x;
    }
    
    public void setVias(int x)
    {
        this.cantVias = x;
    }
    
    @Override
    public int compareTo(Object other) 
    {
        return this.nombre.compareTo( ((Estacion)other).nombre  );
    }
    
    @Override public String toString() 
    {
        return this.nombre;
    }
    
    public String toStringDetallado() 
    {
        return String.format("[Estacion: Nombre: %s | Domicilio: %s | Cantidad Plataformas/Vias: %d/%d].", nombre, domicilio.toString(), cantPlataformas, cantVias);
    }
}

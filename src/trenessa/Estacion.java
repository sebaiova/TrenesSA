package trenessa;

/**
 *
 * @author sebastian.iovaldi
 */
public class Estacion implements Comparable {
    
    final private String nombre;
    private int cantPlataformas;
    private int cantVias;
    private String calle;
    private int numero;
    private String ciudad;
    private String codPostal;
    
    public Estacion(String nombre) 
    {
        this.nombre = nombre;
        this.cantPlataformas = 1;
        this.cantVias = 1;
    }
    
    public Estacion(String nombre, String calle, int numero, String ciudad, String codPostal, int plataformas, int vias) 
    {
        this.nombre = nombre;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.codPostal = codPostal;
        this.cantPlataformas = plataformas;
        this.cantVias = vias;
    }
    
    public String getCiudad()
    {
        return this.ciudad;
    }
    
    public void setCalle(String calle)
    {
        this.calle = calle;
    }
    
    public void setNumero(int numero)
    {
        this.numero = numero;
    }
    
    public void setCiudad(String ciudad)
    {
        this.ciudad = ciudad;
    }
    
    public void setCodPostal(String cp)
    {
        this.codPostal = cp;
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
        return String.format("[Estacion: Nombre: %s | Domicilio: %s %d - %s - CP: %s | Cantidad Plataformas/Vias: %d/%d].", nombre, calle, numero, ciudad, codPostal, cantPlataformas, cantVias);
    }
}

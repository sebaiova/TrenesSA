package trenessa;

/**
 *
 * @author sebastian.iovaldi
 */
public class Domicilio {
    
    private String calle;
    private int numero;
    private String ciudad;
    private String codPostal;
    
    public Domicilio(String calle, int numero, String ciudad, String codPostal) 
    {
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.codPostal = codPostal;
    }
    
    public void setCalle(String calle)
    {
        this.calle = calle;
    }
    
    public void setNumero(int x)
    {
        this.numero = x;
    }
    
    public void setCiudad(String ciudad)
    {
        this.ciudad = ciudad;
    }
    
    public void setCodPostal(String cp)
    {
        this.codPostal = cp;
    }
    
    public String getCiudad()
    {
        return this.ciudad;
    }
    
    public String getCodigoPostal()
    {
        return this.codPostal;
    }
    
    public String getCalle()
    {
        return this.calle;
    }
    
    public int getNumero()
    {
        return this.numero;
    }
    
    @Override
    public String toString()
    {
        return String.format("%s %d - %s - CP: %s", calle, numero, ciudad, codPostal);
    }
}

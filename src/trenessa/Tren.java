package trenessa;

/**
 *
 * @author sebastian.iovaldi
 */
public class Tren {
    
    private final int ID;
    private String propulsion;
    private int vagonesPersonas;
    private int vagonesCarga;
    private String linea;
    
    public Tren(int id)
    {
        this.ID = id;
        this.propulsion = "carbon";
        this.vagonesPersonas = 20;
        this.vagonesCarga = 0;
        this.linea = "no-asignado";
    }
    
    public Tren(int id, String propuslion, int vagonesPersonas, int vagonesCarga, String linea) 
    {
        this.ID = id;
        this.propulsion = propuslion;
        this.vagonesPersonas = vagonesPersonas;
        this.vagonesCarga = vagonesCarga;
        this.linea = linea;
    }

    public String getLinea()
    {
        return linea;
    }
    
    public void setLinea(String linea)
    {
        this.linea = linea;
    }
    
    public void setPropulsion(String propulsion)
    {
        this.propulsion = propulsion;
    }
    
    public void setVagonesPersonas(int x)
    {
        this.vagonesPersonas = x;
    }
    
    public void setVagonesCarga(int x)
    {
        this.vagonesCarga = x;
    }
    
    @Override
    public String toString() {
        return Integer.toString(this.ID);
    }
    
    public String toStringDetallado() {
        return String.format("[Tren ID: %d | Propulsion: %s | Cantidad Vagones (personas/carga): %d/%d | Linea : %s].", ID, propulsion, vagonesPersonas, vagonesCarga, linea);
    }
}

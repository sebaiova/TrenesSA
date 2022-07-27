package trenessa;

/**
 *
 * @author sebastian.iovaldi
 */
public class TrenesSALogger extends TrenesSA {
    
    TrenesSALogger(String data) {
        super(data);
    }
    
    @Override
    protected boolean cargarEstacion(String nombre, String calle, int numero, String barrio, String codPostal, int plataformas, int vias)
    {    
        boolean success = super.cargarEstacion(nombre, calle, numero, barrio, codPostal, plataformas, vias);
        if(!success)
            System.out.println("No se pudo cargar la Estacion" + nombre);
        return success;
    }
    
    @Override
    protected boolean agregarRiel(String origen, String destino, int kms) 
    {
        boolean success = super.agregarRiel(origen, destino, kms);
        if(!success)
            System.out.println("No se pudo cargar el Riel Desde: " + origen + " Hasta: " + destino);
        return success;
    }
}

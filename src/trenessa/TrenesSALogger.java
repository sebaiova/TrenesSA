package trenessa;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebastian.iovaldi
 */
public final class TrenesSALogger extends TrenesSA implements Closeable {
    
    PrintWriter log;
    DateTimeFormatter dateFormat;
    
    TrenesSALogger(String file) 
    {
        super();
        dateFormat = DateTimeFormatter.ofPattern("dd-MM-yy HH;mm;ss");
        try {
            log = new PrintWriter(String.format("log/[%s].txt", LocalDateTime.now().format(dateFormat)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TrenesSALogger.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String data = leerArchivo(file);
        write(String.format("Leer data \"%s\"...", file), !data.isEmpty());
        load(data);
    }
    
    @Override
    public boolean agregarLinea(String nombre)
    {
        boolean success;
        success = super.agregarLinea(nombre);
        write(String.format("Cargar Linea \"%s\"...", nombre), success);
        return success;
    }
    
    @Override
    public boolean eliminarLinea(String nombre)
    {
        boolean success;
        success = super.eliminarLinea(nombre);
        write(String.format("Eliminar Linea \"%s\"...", nombre), success);
        return success;
    }
    
    @Override
    public boolean agregarEstacionALinea(String estacion, String linea)
    {
        boolean success;
        success = super.agregarEstacionALinea(estacion, linea);
        write(String.format("Agregar Estacion \"%s\" a Linea \"%s\"...", estacion, linea), success);
        return success;
    }

    @Override
    public boolean quitarEstacionALinea(String estacion, String linea)
    {
        boolean success;
        success = super.quitarEstacionALinea(estacion, linea);
        write(String.format("Quitar Estacion \"%s\" a Linea \"%s\"...", estacion, linea), success);
        return success;
    }
    
    @Override
    public boolean cargarEstacion(String nombre, String calle, int numero, String barrio, String codPostal, int plataformas, int vias)
    {    
        boolean success = super.cargarEstacion(nombre, calle, numero, barrio, codPostal, plataformas, vias);
        write(String.format("Cargar Estacion \"%s\"...", nombre), success);        
        return success;
    }
    
    @Override
    public boolean editarEstacion(String nombre, String campo, Object valor)
    {
        boolean success = super.editarEstacion(nombre, campo, valor);
        write(String.format("Editando \"%s\" de estacion \"%s\" con el valor \"%s\"", campo, nombre, valor), success);
        return success;
    }
    
    @Override
    public boolean cargarTren(int id, String propuslion, int vagPersonas, int vagCarga, String linea) 
    {
        boolean success = super.cargarTren(id, propuslion, vagPersonas, vagCarga, linea);
        write(String.format("Cargar Tren \"%d\"...", id), success);        
        return success;  
    }
    
    @Override
    public boolean editarTren(int id, String campo, Object valor)
    {
        boolean success = super.editarTren(id, campo, valor);
        write(String.format("Editando \"%s\" de tren de ID: %d con el valor \"%s\"", campo, id, valor), success);
        return success;
    }
    
    @Override
    protected boolean agregarRiel(String origen, String destino, int kms) 
    {
        boolean success = super.agregarRiel(origen, destino, kms);
        write(String.format("Cargar el Riel Desde \"%s\" Hasta \"%s\"...", origen, destino), success);
        return success;
    }
    
    @Override
    public boolean eliminarRiel(String origen, String destino)
    {
        boolean success = super.eliminarRiel(origen, destino);
        write(String.format("Eliminar el Riel Desde \"%s\" Hasta \"%s\"...", origen, destino), success);
        return success;    
    }
    
    @Override
    public boolean editarDistanciaRiel(String origen, String destino, int distancia)
    {
        boolean success = super.editarDistanciaRiel(origen, destino, distancia);
        write(String.format("Editar distancia Desde \"%s\" Hasta \"%s\" con \"%d\"...", origen, destino, distancia), success);
        return success;  
    }
    
    @Override
    public boolean eliminarTren(int id)
    {
        boolean success = super.eliminarTren(id);
        write(String.format("Eliminar tren \"%d\"...", id), success);
        return success;  
    }
    
    @Override
    public boolean eliminarEstacion(String nombre)
    {
        boolean success = super.eliminarEstacion(nombre);
        write(String.format("Eliminar Estacion \"%s\"...", nombre), success);
        return success;  
    }
    
    @Override
    public void limpiarSistema() 
    {
        super.limpiarSistema();
        write("Limpiar sistema...", true);
    }
    
    @Override
    protected boolean load(String data)
    {
        boolean success = super.load(data);
        log.println();
        write("Finalizando carga de datos...", success);
        log.println();
        printSystem();
        return success;
    }
    
    static private String leerArchivo(String fileName)
    {
        String string = "";
        try {
            string = new String(Files.readAllBytes(Path.of(fileName)));
        }
        catch (IOException ex) {
            System.out.println(fileName + " no encontrado");
        }
        return string;
    }
    
    private void write(String string, boolean success)
    {
        log.printf("[%s] %-80s[%s].\n", LocalDateTime.now().format(dateFormat), string, success ? "SUCCEED" : "FAIL");
        log.flush();
    }

    private void printSystem()
    {
        log.println(getEstacionesRaw());
        log.println();
        log.println(getLineasRaw());
        log.println();
        log.println(getMapaRaw());
        log.println(getTrenesRaw());
        log.println("**************************************************************");
        log.println();
        log.flush();
    }
    
    @Override
    public void close() {
        log.print("Cerrando sistema...\n\n");
        printSystem();
        log.print("Good bye!\n");
        log.flush();
        log.close();
    }
}

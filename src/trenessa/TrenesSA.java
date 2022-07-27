package trenessa;

import conjuntistas.Conjunto;
import conjuntistas.Diccionario;
import grafos.Grafo;
import java.util.HashMap;
import lineales.dinamicas.Lista;

/**
 *
 * @author sebastian.iovaldi
 */

public class TrenesSA {

    private final Diccionario estaciones;
    private final Diccionario trenes;
    private final Grafo mapa;
    private final HashMap lineas;
   
    private boolean cargarData(String data) 
    {
        boolean success = true;
        String ln = "";
        try {
            var arrayLineas = data.split("\n");
            for (String arrayLinea : arrayLineas) {
                ln = arrayLinea;
                var tokens = ln.split(";", -1);
                switch (tokens[0]) {
                    case "E" -> cargarEstacion(tokens[1], tokens[2], Integer.parseInt(tokens[3]), tokens[4], tokens[5], Integer.parseInt(tokens[6]), Integer.parseInt(tokens[7]));
                    case "L" -> {
                        int size = tokens.length-1;
                        String linea = tokens[1];
                        agregarLinea(linea);
                        for(int i=2; i<size; i++)
                            agregarEstacionALinea(tokens[i], linea);
                    }
                    case "T" -> cargarTren(Integer.parseInt(tokens[1]), tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), tokens[5]);
                    case "R" -> agregarRiel(tokens[1], tokens[2], Integer.parseInt(tokens[3]));
                    case "" -> {}
                    default -> throw new Exception();
                }
            }
        }
        catch(Exception e) {
            System.out.println("Datos cargados corruptos.");
            System.out.println("Problemas con la linea: " + ln);
            limpiarSistema();
            success = false;
        }
        return success;
    }
    
    protected boolean cargarEstacion(String nombre, String calle, int numero, String barrio, String codPostal, int plataformas, int vias) 
    {
        boolean success;
        success = estaciones.insertar(nombre, new Estacion(nombre, new Domicilio(calle, numero, barrio, codPostal), plataformas, vias));
        if(success)
            success = mapa.insertarVertice(nombre);
        return success;
    }
    
    public boolean agregarLinea(String nombre)
    {
        return lineas.putIfAbsent(nombre, new Conjunto()) == null;
    }
    
    public boolean eliminarLinea(String nombre)
    {
        return lineas.remove(nombre) != null;
    }
    
    public boolean agregarEstacionALinea(String estacion, String linea)
    {
        boolean success = false;
        Conjunto set = (Conjunto) lineas.get(linea);
        if(set!=null)
            if(set.agregar(estacion))
                success = true;
        return success;
    }
    
    public boolean quitarEstacionALinea(String estacion, String linea)
    {
        boolean success = false;
        Conjunto set = (Conjunto) lineas.get(linea);
        if(set!=null)
            success = set.quitar(estacion);
        return success;
    }
    
    public boolean cargarTren(int id, String propuslion, int vagPersonas, int vagCarga, String linea) 
    {
        return trenes.insertar(id, new Tren(id, propuslion, vagPersonas, vagCarga, linea));
    }
    
    public Tren obtenerTren(int id)
    {
        return (Tren) trenes.obtener(id);
    }
    
    public Estacion obtenerEstacion(String nombre)
    {
        return (Estacion) estaciones.obtener(nombre);
    }
    
    public boolean existeLinea(String linea) 
    {
        return lineas.containsKey(linea);
    }
    
    protected boolean agregarRiel(String origen, String destino, int kms) 
    {
        return mapa.insertarArco(origen, destino, kms);
    }
    
    public boolean eliminarRiel(String origen, String destino)
    {
        return mapa.eliminarArco(origen, destino);
    }
    
    public boolean editarDistanciaRiel(String origen, String destino, int distancia)
    {
        boolean success = mapa.cambiarEtiqueta(origen, destino, distancia);
        if(success)
            mapa.cambiarEtiqueta(destino, origen, distancia);
        return success;
    }
    
    public boolean eliminarTren(int id)
    {
        return trenes.eliminar(id);
    }
    
    public boolean eliminarEstacion(String nombre)
    {
        boolean success;
        success = this.estaciones.eliminar(nombre);
        if(success)
        {
            this.mapa.eliminarVertice(nombre);
            this.lineas.forEach((key, set) -> {
                ((Conjunto)set).quitar(nombre);
            });
        }
        return success;
    }
    
    private void limpiarSistema() 
    {
        estaciones.vaciar();
        trenes.vaciar();
        lineas.clear();
        mapa.vaciar();
    }
    
    public Lista caminoMenorDistancia(String origen, String destino) 
    {
        return mapa.caminoMenorDistancia(origen, destino);
    }
    
    public boolean existeCaminoEntre(String origen, String destino)
    {
        return mapa.existeCamino(origen, destino);
    }
    
    public Lista caminoMenorEstaciones(String origen, String destino)
    {
        return mapa.caminoMenorVertices(origen, destino);
    }
    
    public String getEstacionesAsString()
    {
        return estaciones.listarClaves().toString();
    }
    
    public String getEstacionesRaw()
    {
        return "Arbol AVL con las Estaciones:\n" + estaciones.toString();
    }
    
    public String getTrenesRaw()
    {
        return "Arbol AVL con los Trenes:\n" + trenes.toString();
    }
    
    public String getIDTrenesAsString() 
    {
        return trenes.listarClaves().toString();
    }
    
    public String getLineasRaw()
    {
        return "Mapeo de cada linea en un HashMap con sus estacion en nodos enlazados:\n" + lineas.toString();
    }
    
    public String getMapaRaw()
    {
        return "Estaciones y los rieles con sus respectivas distancias:\n" + mapa.toString();
    }
    
    public String buscarInfoTren(int codigo)
    {
        String string;
        Tren tren = (Tren) trenes.obtener(codigo);
        if(tren==null)
            string = "El tren no existe.";
        else
            string = tren.toStringDetallado();
        return string;
    }
    
    public String buscarInfoEstacion(String nombre)
    {
        String string;
        Estacion estacion = (Estacion) this.estaciones.obtener(nombre);
        if(estacion==null)
            string = "La estacion no existe.";
        else
            string = estacion.toStringDetallado();
        return string;
    }
    
    public String buscarCiudadesTren(int codigo)
    {
        String output;
        Tren tren = (Tren) trenes.obtener(codigo);
        if(tren==null)
            output = "El tren no existe.";
        else {
            Conjunto estacionesSet = (Conjunto)lineas.get(tren.getLinea());
            if(estacionesSet == null)
                output = "El tren no esta asignado a ninguna linea.";
            else {
                if(estacionesSet.esVacio())
                    output = "La linea al que pertenece el tren no posee estacion.";
                else {
                    Lista estDelTren = estacionesSet.listar();
                    Conjunto ciudades = new Conjunto();
                    while(!estDelTren.esVacia()) 
                    {
                        String nombreEstacion = (String) estDelTren.recuperar(1);
                        Estacion estacion = (Estacion) this.estaciones.obtener(nombreEstacion);
                        ciudades.agregar(estacion.getDomicilio().getCiudad());
                        estDelTren.eliminar(1);
                    }
                    output = ciudades.toString();
                }
            }
        }
        return output;    
    }
    
    public String buscarEstacionesDeLinea(String linea)
    {
        String output;
        Conjunto setEstaciones = (Conjunto) lineas.get(linea);
        if(setEstaciones==null)
            output = "La linea no existe.";
        else
            output = setEstaciones.toString();
        return output;
    }
    
    public String buscarEstacionesPrefijadas(String prefix)
    {
        String output = "";
        int lenght = prefix.length();
        char c = (char) (prefix.charAt(lenght-1) + 1);
        String to = prefix.substring(0, lenght-1).concat(String.valueOf(c));
        output = this.estaciones.listarRango(prefix, to).toString();
        return output;
    }
    
    public TrenesSA(String data) 
    {
        this.estaciones = new Diccionario();
        this.trenes = new Diccionario();
        this.mapa = new Grafo();
        this.lineas = new HashMap();
        
        if(!data.isEmpty())
            cargarData(data);
    }
}
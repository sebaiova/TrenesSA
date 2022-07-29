package trenessa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 *
 * @author sebastian.iovaldi
 */
public class Main
{
    private static final Scanner scanner = new Scanner(System.in);
    private static TrenesSA sistema;

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
    
    static private void menuPrincipal()
    {
        int input=-1;
        while(input !=0)
        {
            System.out.println("Menu Principal\n");
            input = preguntar("Salir", "Operar Trenes", "Operar Estaciones", "Operar Lineas", "Operar Rieles", "Ruta con menor Distancia", "Ruta con menor Numero de Estaciones", "Mostrar Sistema");        
            switch (input) 
            {
                case 1 -> menuOperarTrenes();
                case 2 -> menuOperarEstaciones();
                case 3 -> menuOperarLineas();
                case 4 -> menuOperarRieles();
                case 5 -> menuRutaMenorDistancia();
                case 6 -> menuRutaMenorEstaciones();
                case 7 -> menuMostrarSistema(); 
            }
        }
    }
    
    static private void menuRutaMenorDistancia()
    {
        System.out.println("Buscando ruta con menor distancia...");
        String origen = pedirString("Desde estacion: ");
        String destino = pedirString("Hasta estacion: ");
        if(sistema.existeCaminoEntre(origen, destino))
            mostrar(sistema.caminoMenorDistancia(origen, destino).toString());
        else
            mostrar(String.format("No existe camino entre \"%s\" y \"%s\".", origen, destino));
    }
    
    static private void menuRutaMenorEstaciones()
    {
        System.out.println("Buscando ruta con menor numero de estaciones...");
        String origen = pedirString("Desde estacion: ");
        String destino = pedirString("Hasta estacion: ");
        if(sistema.existeCaminoEntre(origen, destino))
            mostrar(sistema.caminoMenorEstaciones(origen, destino).toString());
        else
            mostrar(String.format("No existe camino entre \"%s\" y \"%s\".", origen, destino));
    }
    
    static private void menuOperarRieles()
    {
        int input = -1;
        while(input != 0)
        {
            System.out.println("Menu Operar Rieles");
            input = preguntar("Atras", "Agregar Riel", "Editar Distancia de Riel", "Eliminar Riel");
            switch(input)
            {
                case 1 -> menuAgregarRiel();
                case 2 -> menuEditarRiel();
                case 3 -> menuEliminarRiel();
            }
        }
    }
    
    static private void menuEditarRiel()
    {
        System.out.println("Editando Riel...");
        String desde = pedirString("Desde estacion: ");
        String hasta = pedirString("Hasta estacion: ");
        int distancia = pedirEntero("Ingresa la distancia nueva: ");
        if(sistema.editarDistanciaRiel(desde, hasta, distancia))
            mostrar(String.format("Distancia entre \"%s\" y \"%s\" cambiado a %d exitosamente.", desde, hasta, distancia));
        else
            mostrar(String.format("No existe riel desde \"%s\" hasta \"%s\"", desde, hasta));
    } 
    
    static private void menuAgregarRiel()
    {
        System.out.println("Agregando Riel...");
        String desde = pedirString("Desde estacion: ");
        String hasta = pedirString("Hasta estacion: ");
        int distancia = pedirEntero("Con Distancia (mts): ");
        if(sistema.agregarRiel(desde, hasta, distancia))
            mostrar(String.format("Riel desde \"%s\" hasta \"%s\" agregado exitosamente", desde, hasta));
        else
            mostrar(String.format("No pudo agregarse riel desde \"%s\" hasta \"%s\"", desde, hasta));
    }
    
    static private void menuEliminarRiel()
    {
        System.out.println("Eliminando Riel...");
        String desde = pedirString("Desde estacion: ");
        String hasta = pedirString("Hasta estacion: ");
        if(sistema.eliminarRiel(desde, hasta))
            mostrar(String.format("Riel desde \"%s\" hasta \"%s\" eliminado exitosamente", desde, hasta));
        else
            mostrar(String.format("No se pudo eliminar riel desde \"%s\" hasta \"%s\"", desde, hasta));
    }
    
    static private void menuOperarEstaciones()
    {
        int input = -1;
        while(input !=0)
        {
            System.out.println("Menu Operar Estaciones\n");
            input = preguntar("Atras", "Obtener Informacion de Estaciones", "Buscar Estaciones con Prefijo", "Agregar Estacion", "Editar Estacion", "Eliminar Estacion", "Listar Estaciones");    
            switch (input) 
            {
                case 1 -> mostrar(sistema.buscarInfoEstacion(pedirString("Ingrese el nombre de la Estacion: ")));
                case 2 -> mostrar(sistema.buscarEstacionesPrefijadas(pedirString("Ingrese prefijo: ")));
                case 3 -> menuAgregarEstacion();
                case 4 -> menuEditarEstacion();
                case 5 -> menuEliminarEstacion();
                case 6 -> mostrar(sistema.getEstacionesAsString());
            }
        }
    }
    
    static private void menuOperarLineas()
    {
        int input = -1;
        while(input !=0)
        {
            System.out.println("Menu Operar Lineas\n");
            input = preguntar("Atras", "Agregar Linea", "Eliminar Linea", "Agregar Estacion a Linea", "Quitar Estacion a Linea", "Mostrar Estacion de Linea");    
            switch (input) 
            {
                case 1 -> menuAgregarLinea();
                case 2 -> menuEliminarLinea();
                case 3 -> menuAgregarEstacionALinea();
                case 4 -> menuQuitarEstacionALinea();
                case 5 -> mostrar(sistema.buscarEstacionesDeLinea(pedirString("Ingrese la linea: ")));
            }
        }
    }
    
    static private void menuAgregarLinea()
    {
        System.out.println("Agregando nueva Linea...");
        if(sistema.agregarLinea(pedirString("Ingrese el nombre de la Linea: ")))
            mostrar("Linea cargada exitosamente.");
        else
            mostrar("La linea ya existe.");   
    }
    
    static private void menuEliminarLinea()
    {
        System.out.println("Eliminando Linea...");
        if(sistema.eliminarLinea(pedirString("Ingrese el nombre de la Linea: ")))
            mostrar("Linea eliminada exitosamente.");
        else
            mostrar("La linea a eliminar no existe.");
    }
    
    static private void menuAgregarEstacionALinea()
    {
        System.out.println("Agregando Estacion a Linea...");
        String estacion = pedirString("Ingrese el nombre de la Estacion: ");
        if(sistema.obtenerEstacion(estacion)==null)
            mostrar(String.format("La estacion \"%s\" no existe.", estacion));
        else {
            String linea = pedirString("Ingrese el nombre de la Linea: ");
            if(sistema.agregarEstacionALinea(estacion, linea))
                mostrar(String.format("La estacion \"%s\" fue agregada a la linea \"%s\" exitosamente.", estacion, linea));
            else
                mostrar(String.format("La linea \"%s\" no existe.", linea));
            }
    }
    
    static private void menuQuitarEstacionALinea()
    {
        System.out.println("Quitando Estacion a Linea...");
        String estacion = pedirString("Ingrese el nombre de la Estacion: ");
        String linea = pedirString("Ingrese el nombre de la Linea: ");
        if(sistema.quitarEstacionALinea(estacion, linea))
            mostrar(String.format("La estacion \"%s\" fue removida de la linea \"%s\" exitosamente.", estacion, linea));
        else
            mostrar(String.format("No pudo quitarse la estacion \"%s\" de la linea \"%s\" ", estacion, linea));
    }
    
    static private void menuAgregarEstacion()
    {
        boolean success;
        System.out.println("Agregando nueva Estacion...");
        String nombre = pedirString("Ingrese el nombre: ");
        success = sistema.cargarEstacion(   nombre, 
                                            pedirString("Ingrese la calle: "), 
                                            pedirEntero("Ingrese el numero: "),
                                            pedirString("Ingrese el barrio: "),
                                            pedirString("Ingrese el codigo postal: "),
                                            pedirEntero("Ingrese la cantidad de plataformas: "),
                                            pedirEntero("Ingrese la cantidad de vias: "));
        if(success)
            mostrar(String.format("Estacion \"%s\" cargada exitosamente.", nombre));
        else
            mostrar(String.format("La Estacion %s ya existe, Estacion no cargada.", nombre));
    }
    
    static private void menuEditarEstacion()
    {
        int input = -1;
        System.out.println("Editando estacion...\n");
        String nombre = pedirString("Ingrese el nombre: ");
        Estacion estacion = sistema.obtenerEstacion(nombre);
        if(estacion==null)
            mostrar(String.format("Estacion \"%s\" no existe.", nombre));
        else {
            while(input!=0)
            {
                System.out.printf("Editando estacion %s..\n\n", estacion.toStringDetallado());
                input = preguntar("Atras", "Editar Calle", "Editar Numero", "Editar Ciudad", "Editar Codigo Postal", "Editar cantidad Plataformas", "Editar cantidad Vias");
                switch (input) 
                {
                    case 1 -> estacion.getDomicilio().setCalle(pedirString("Ingrese la calle: "));
                    case 2 -> estacion.getDomicilio().setNumero(pedirEntero("Ingrese el numero: "));
                    case 3 -> estacion.getDomicilio().setCalle(pedirString("Ingrese la Ciudad: "));
                    case 4 -> estacion.getDomicilio().setCodPostal(pedirString("Ingrese el Codigo Postal: "));
                    case 5 -> estacion.setPlataformas(pedirEntero("Ingrese el numero de plataformas: "));
                    case 6 -> estacion.setVias(pedirEntero("Ingrese el numero de vias: "));
                }
            }
        }
    }
    
    static private void menuEliminarEstacion()
    {
        System.out.println("Eliminando estacion...\n");
        String nombre = pedirString("Ingrese el nombre de la estacion: ");
        if(sistema.eliminarEstacion(nombre))
            System.out.println("Estacion eliminada exitosamente.\n");
        else
            mostrar(String.format("La estacion \"%s\" no existe, estacion no eliminada", nombre));
    }    
    
    static private void menuMostrarSistema()
    {
        int input = -1;
        while(input !=0)
        {
            System.out.println("Menu Mostrar Sistema\n");
            input = preguntar("Atras", "Mostrar Estaciones", "Mostrar Lineas", "Mostrar Trenes", "Mostrar Mapa");    
            switch (input) 
            {
                case 1 -> mostrar(sistema.getEstacionesRaw());
                case 2 -> mostrar(sistema.getLineasRaw());
                case 3 -> mostrar(sistema.getTrenesRaw());
                case 4 -> mostrar(sistema.getMapaRaw());
            }
        }
    }
    
    static private void menuOperarTrenes()
    {
        int input = -1;
        while(input !=0)
        {
            System.out.println("Menu Operar Trenes\n");
            input = preguntar("Atras", "Obtener Informacion de Tren", "Obtener Ciudades de Tren", "Agregar Tren", "Editar Tren", "Eliminar Tren", "Listar trenes");    
            switch (input) 
            {
                case 1 -> mostrar(sistema.buscarInfoTren(pedirEntero("Ingrese la ID del tren: ")));
                case 2 -> mostrar(sistema.buscarCiudadesTren(pedirEntero("Ingrese la ID del tren: ")));
                case 3 -> menuAgregarTren();
                case 4 -> menuEditarTren();
                case 5 -> menuEliminarTren();
                case 6 -> mostrar(sistema.getTrenesRaw());
            }
        }
    }
    
    static private void menuEliminarTren()
    {
        System.out.println("Eliminando tren...\n");
        int id = pedirEntero("Ingrese la ID del tren: ");
        if(sistema.eliminarTren(id))
            System.out.println("Tren eliminado exitosamente.\n");
        else
            mostrar(String.format("Tren con ID %d no existe, tren no eliminado", id));
    }
    
    static private void menuEditarTren()
    {
        int input = -1;
        System.out.println("Editando tren...\n");
        int id = pedirEntero("Ingrese la ID del tren: ");
        Tren tren = sistema.obtenerTren(id);
        if(tren==null)
            mostrar(String.format("Tren con ID %d no existe.", id));
        else {
            while(input!=0) {
                System.out.printf("Editando tren %s...\n\n", tren.toStringDetallado());
                input = preguntar("Atras", "Editar Linea", "Editar Propuslion", "Editar Cantidad Vagones de Personas", "Editar Cantidad Vagones de Carga");
                switch (input) 
                {
                    case 1 -> { String linea = pedirString("Ingrese la linea: ");
                                if(!sistema.existeLinea(linea)) {
                                    linea = "no-asignado";
                                    System.out.println("Tren asignado a ninguna linea."); }
                                tren.setLinea(linea);
                    }
                    case 2 -> tren.setPropulsion(pedirString("Ingrese el tipo de propulsion: "));
                    case 3 -> tren.setVagonesPersonas(pedirEntero("Ingrese la cantidad de vagones de personas: "));
                    case 4 -> tren.setVagonesCarga(pedirEntero("Ingrese la cantidad de vagones de carga: "));
                }
            } 
        }  
    }
    
    static private void menuAgregarTren()
    {
        System.out.println("Agregando nuevo Tren...");
        int id = pedirEntero("Ingrese la ID del tren: ");
        String propulsion = pedirString("Ingrese el tipo de propulsion: ");
        int vPersonas = pedirEntero("Ingrese la cantidad de vagones para personas: ");
        int vCarga = pedirEntero("Ingrese la cantidad de vagones para carga: ");
        String linea = pedirString("Ingrese la linea en la que opera: ");
        if(!sistema.existeLinea(linea)) {
            linea = "no-asignado";
            System.out.println("Tren asignado a ninguna linea.");
        }
        if(!sistema.cargarTren(id, propulsion, vPersonas, vCarga, linea))
            mostrar(String.format("La ID %d ya existe, tren no cargado.\n\n", id));
        else
            mostrar("Tren cargado exitosamente.\n");
    }
    
    static private int pedirEntero(String msg)
    {
        int id = -1;
        boolean success = false;
        while(!success)
        {
            System.out.print(msg);
            try {
                id = scanner.nextInt();
                success = true;
            }
            catch(Exception e) {
                System.out.println("Entrada invalida.\n"); 
            }
            finally {
                scanner.nextLine();
            }
        }
        return id;
    }
    
    static private String pedirString(String msg)
    {
        String string = "";
        boolean success = false;
        while(!success)
        {
            System.out.print(msg);
            try{
                string = scanner.nextLine();
                success = true;
            }
            catch(Exception e) {
                scanner.nextLine();
                System.out.println("Entrada invalida.\n"); 
            }
        }
        return string;
    }
    
    static private int preguntar(String ... options)
    {
        int i=0, input=-1;
        boolean success = false;
        
        for(String opt : options)
            System.out.printf("%d) %s.\n", i++, opt);
        
        while(!success)
        {
            System.out.printf("\nIngrese la opcion: ");
            try {
                input = scanner.nextInt();
                if(input<0 || input>i) 
                    throw new Exception();
                success = true;
            }
            catch(Exception ex) {
                System.out.println("Entrada invalida."); 
            }
            finally {
                scanner.nextLine();
            }
        }
        System.out.print("\n");
        return input;
    }
    
    static private void mostrar(String string)
    {
        System.out.printf("%s\n\nPresiona ENTER para continuar...", string);  
        scanner.nextLine();
        System.out.print("\n");
    }
    
    static public void main(String[] args) 
    {
        var data = leerArchivo("data/subtes.txt");
        sistema = new TrenesSALogger(data);
        menuPrincipal();
    }
}

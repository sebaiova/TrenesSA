package grafos;

import java.util.HashMap;
import lineales.dinamicas.Cola;
import lineales.dinamicas.ColaPrioridad;
import lineales.dinamicas.Lista;

/**
 *
 * @author sebastian.iovaldi
 */


/// Grafo No-Dirigido, Etiquetado
public class Grafo {
    
    private NodoVert inicio;
    private int cantVertices;
    
    public Grafo()
    {
        inicio = null;
        cantVertices = 0;
    }
    
    public void vaciar() 
    {
        cantVertices = 0;
        inicio = null;
    }
    
    public boolean insertarVertice(Object nuevoVertice)
    {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if(aux == null) 
        {
            this.inicio = new NodoVert(nuevoVertice, this.inicio);
            exito = true;
            this.cantVertices++;
        }
        return exito;
    }
    
    public boolean insertarArco(Object origen, Object destino, int etiqueta)
    {
        boolean exito = false;
        NodoVert nodoOrigen = null;
        NodoVert nodoDestino = null;
        NodoVert aux = this.inicio;
        //Busco los vertices de ambos objetos
        while(aux != null && (nodoOrigen == null || nodoDestino == null))
        {
            if(aux.getElem().equals(origen))
                nodoOrigen = aux;
            if(aux.getElem().equals(destino))
                nodoDestino = aux;
            aux = aux.getSigVertice();
        }
        if(nodoOrigen != null && nodoDestino != null) 
        {
            nodoOrigen.setPrimerAdy(new NodoAdy(nodoDestino, nodoOrigen.getPrimerAdy(), etiqueta));
            nodoDestino.setPrimerAdy(new NodoAdy(nodoOrigen, nodoDestino.getPrimerAdy(), etiqueta));
            exito = true;
        }
        return exito;
    }
    
    public boolean eliminarArco(Object origen, Object destino)
    {
        boolean exito = false;
        NodoVert nodoOrigen = null;
        NodoVert nodoDestino = null;
        NodoVert aux = this.inicio;
        //Busco los vertices de ambos objetos
        while(aux != null && (nodoOrigen == null || nodoDestino == null))
        {
            if(aux.getElem().equals(origen))
                nodoOrigen = aux;
            if(aux.getElem().equals(destino))
                nodoDestino = aux;
            aux = aux.getSigVertice();
        }
        //Si los encuentro
        if(nodoOrigen!=null && nodoDestino!=null)
            exito = eliminarAdyacente(nodoOrigen, destino) && eliminarAdyacente(nodoDestino, origen);
        
        return exito;
    }
    
    // Busca entre los nodos adyacentes de un vertice el que apunte a destino y lo borra.
    private boolean eliminarAdyacente(NodoVert vert, Object destino)
    {
        boolean exito = false;
        NodoAdy adyAux;
        adyAux = vert.getPrimerAdy();
        if(adyAux.getVertice().getElem().equals(destino))
        {
            vert.setPrimerAdy(adyAux.getSigAdyacente());
            exito = true;
        }
        while(!exito && adyAux.getSigAdyacente() != null)
        {
            if(adyAux.getSigAdyacente().getVertice().getElem().equals(destino))
            {
                adyAux.setSigAdyacente(adyAux.getSigAdyacente().getSigAdyacente());
                exito = true;
            }
            adyAux = adyAux.getSigAdyacente();
        }
        return exito;
    }
    
    public boolean cambiarEtiqueta(Object origen, Object destino, int valor)
    {
        boolean exito = false;
        NodoVert nodoOrigen = null;
        NodoVert nodoDestino = null;
        NodoVert aux = this.inicio;
        //Busco los vertices de ambos objetos
        while(aux != null && (nodoOrigen == null || nodoDestino == null))
        {
            if(aux.getElem().equals(origen))
                nodoOrigen = aux;
            if(aux.getElem().equals(destino))
                nodoDestino = aux;
            aux = aux.getSigVertice();
        }
        if(nodoOrigen!=null && nodoDestino!=null)
        {
            NodoAdy ady;
            //Busco el nodo adyacente y le cambio la distancia
            ady = nodoOrigen.getPrimerAdy();
            while(!exito && ady!=null)
            {
                if(ady.getVertice().getElem().equals(destino))
                {
                    ady.setEtiqueta(valor);
                    exito = true;
                }
                ady = ady.getSigAdyacente();
            }
            exito = false;
            //En la otra direccion
            ady = nodoDestino.getPrimerAdy();
            while(!exito && ady!=null)
            {
                if(ady.getVertice().getElem().equals(origen))
                {
                    ady.setEtiqueta(valor);
                    exito = true;
                }
                ady = ady.getSigAdyacente();
            }
        }
        return exito;
    }
    
    //Elimina el vertice y los arcos que apuntan al mismo.
    //Retorna true si se elimina, false si el vertice no existia.
    //Recorre todos los vertices buscando arcos sin verificar si el vertice a eliminar existe previamente,
    //por lo que es menos eficiente al eliminar con vertices que no existen, pero mas agil si existe.
    public boolean eliminarVertice(Object object)
    {
        boolean success = false;
        NodoVert prev = null;
        NodoVert aux = this.inicio;
        while(aux != null) 
        {
            if(!success && aux.getElem().equals(object)) 
            {
                success = true;
                this.cantVertices--;
                if(prev==null)
                    this.inicio.setSigVertice(aux.getSigVertice());
                else
                    prev.setSigVertice(aux.getSigVertice());
            }
            else {
                NodoAdy ady = aux.getPrimerAdy();
                NodoAdy adyPrev = null;
                while(ady!=null)
                {
                    if(ady.getVertice().getElem().equals(object))
                    {
                        if(adyPrev == null)
                            aux.setPrimerAdy(ady.getSigAdyacente());
                        else
                            adyPrev.setSigAdyacente(ady.getSigAdyacente());
                    }
                    adyPrev = ady;
                    ady = ady.getSigAdyacente();
                }
            }
            prev = aux;
            aux = aux.getSigVertice();            
        }
        return success;
    }
    
    public boolean existeVertice(Object object)
    {
        boolean existe = false;
        NodoVert aux = this.inicio;
        while(!existe && aux != null) 
        {
            if(aux.getElem().equals(object))
                existe = true;
            else
                aux = aux.getSigVertice();
        }
        return existe;
    }
    
    public boolean existeArco(Object origen, Object destino) 
    {
        boolean existe = false;
        NodoVert nodoOrigen = ubicarVertice(origen); 
        if(nodoOrigen != null) {
            NodoAdy aux = nodoOrigen.getPrimerAdy();
            while(!existe && aux != null) 
            {
                if(aux.getVertice().getElem().equals(destino))
                    existe = true;
                else
                    aux = aux.getSigAdyacente();
            } 
        }   
        return existe;
    }
    
    public boolean existeCamino(Object origen, Object destino) 
    {
        boolean exito = false;
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        while((auxO == null || auxD == null ) && aux != null) 
        {
            if(aux.getElem().equals(origen)) auxO=aux;
            if(aux.getElem().equals(destino)) auxD=aux;
            aux = aux.getSigVertice();
        }
        if(auxO != null && auxD != null) 
        {
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, destino, visitados);
        }
        return exito;
    }
    
    private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis) 
    {
        boolean exito = false;
        if(n != null) 
        {
            if(n.getElem().equals(dest))
                exito = true;
            else 
            {
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while(!exito && ady != null) 
                {
                    if(vis.localizar(ady.getVertice().getElem()) < 0)
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }
    
    private NodoVert ubicarVertice(Object buscado)
    {
        NodoVert aux = this.inicio;
        while(aux != null && !aux.getElem().equals(buscado))
            aux = aux.getSigVertice();
        return aux;
    }
    
    
    public Lista listarEnAnchura()
    {
        Lista visitados = new Lista();
        Lista lista = new Lista();
        NodoVert aux = this.inicio;
        while(aux != null) 
        {
            if(visitados.localizar(aux) == -1) 
            {
                lista.insertar(aux);
                listarEnAnchuraDesde(aux, lista, visitados);
            }
            aux = aux.getSigVertice();
        }
        return lista;
    }
    
    private void listarEnAnchuraDesde(NodoVert vert, Lista lista, Lista visitados)
    {
        Cola cola = new Cola();
        visitados.insertar(vert, 1);
        cola.poner(vert);
        while(!cola.esVacia()) 
        {
            NodoAdy ady = ((NodoVert)cola.obtenerFrente()).getPrimerAdy();
            cola.sacar();
            while(ady != null) 
            {
                NodoVert adyVert = ady.getVertice();
                if(visitados.localizar(adyVert) == -1) 
                {
                    visitados.insertar(adyVert, 1);
                    cola.poner(adyVert);
                    lista.insertar(adyVert);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }
 
    // Recorrido en BFS. 
    // PRECONDICION: EL CAMINO DEBE EXISTIR 
    public Lista caminoMenorVertices(Object origen, Object destino)
    {
        boolean found = false;
        HashMap visitados = new HashMap();
        Lista camino = new Lista();
        Cola cola = new Cola();
        NodoVert vert = ubicarVertice(origen);
        cola.poner(vert);
        visitados.put(origen, origen); // El valor es el padre, pero si uso null para el primero, putIfAbsent no funciona.
        while(!cola.esVacia() && !found)
        {
            vert = (NodoVert) cola.obtenerFrente();
            NodoAdy ady = vert.getPrimerAdy();
            cola.sacar();
            while(ady != null && !found)
            {
                NodoVert vertSig = ady.getVertice();
                if(visitados.putIfAbsent(vertSig.getElem(), vert.getElem()) == null)
                {
                    if(vertSig.getElem().equals(destino))
                        found = true;
                    else
                        cola.poner(ady.getVertice());
                }
                ady = ady.getSigAdyacente();
            }
        }
        
        Object traceback = destino;
        do {
            camino.insertar(traceback, 1);
            traceback = visitados.get(traceback);
        }
        while(!traceback.equals(origen));
        camino.insertar(traceback, 1);
        return camino;
    }

    //Algoritmo de Dijkstra
    // PRECONDICION: EL CAMINO DEBE EXISTIR 
    public Lista caminoMenorDistancia(Object origen, Object destino)
    {        
        boolean found = false;
        ColaPrioridad colaPrioridad = new ColaPrioridad(cantVertices*2);
        HashMap visitados = new HashMap();
        Lista camino = new Lista();
        NodoVert vertOrigen = ubicarVertice(origen);
        colaPrioridad.insertar(new TuplaDijkstra(0, vertOrigen, vertOrigen), 0);
        while(!colaPrioridad.esVacio() && !found)
        {
            TuplaDijkstra tupla = (TuplaDijkstra)colaPrioridad.recuperarFrente();
            colaPrioridad.eliminiarFrente();
            if( !visitados.containsKey(tupla.getNodo().getElem()) )
            {
                int distanciaActual = tupla.getDistancia();
                NodoVert vert = tupla.getNodo();
                NodoAdy ady = vert.getPrimerAdy();
                visitados.put(vert.getElem(), tupla.getPrev().getElem());
                if(vert.getElem().equals(destino))
                    found = true;
                else 
                {
                    while(ady != null) 
                    {
                        TuplaDijkstra nuevaTupla = new TuplaDijkstra(distanciaActual + ady.getEtiqueta(), ady.getVertice(), vert);
                        if( !visitados.containsKey(nuevaTupla.getNodo().getElem()) )
                        {
                            colaPrioridad.insertar(nuevaTupla, nuevaTupla.getDistancia());
                        }
                        ady = ady.getSigAdyacente();
                    }
                }
            }
        }
        Object traceback = destino;
        do {
            camino.insertar(traceback, 1);
            traceback = visitados.get(traceback);
        }
        while(!traceback.equals(origen));
        camino.insertar(traceback, 1);

        return camino;
    }
   
    @Override
    public String toString() 
    {
        String stringVertices = "";
        String stringArcos = "";
        if(inicio!=null) {
            stringVertices = "Vertices: ";
            stringArcos = "\nArcos: ";
            NodoVert aux = this.inicio;
            while(aux != null) 
            {
                String origin = "["+aux.getElem().toString()+"]";
                stringVertices = stringVertices.concat(origin);
                NodoAdy ady = aux.getPrimerAdy();
                while(ady != null)
                {
                    String dest = "["+ady.getVertice().getElem().toString()+"]";
                    stringArcos = stringArcos.concat("\t"+origin+"-"+ady.getEtiqueta()+"->"+dest+"\n");
                    ady = ady.getSigAdyacente();
                }
                aux = aux.getSigVertice();
            }
        }
        return stringVertices + stringArcos;
    }
}

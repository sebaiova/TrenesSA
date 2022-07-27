package conjuntistas;

import lineales.dinamicas.Lista;

/**
 *
 * @author sebastian.iovaldi
 */
public class Conjunto { 
    
    private final ArbolAVL arbol;
    
    public Conjunto()
    {
        arbol = new ArbolAVL();
    }
    
    public boolean agregar(Comparable obj)
    {
        return arbol.insertar(obj);
    }
    
    public boolean quitar(Comparable obj)
    {
        return arbol.eliminar(obj);
    }
    
    public boolean pertenece(Comparable obj)
    {
        return arbol.pertenece(obj);
    }
    
    public boolean esVacio()
    {
        return arbol.esVacio();
    }
    
    public Lista listar()
    {
        return arbol.listarInorden();
    }
    
    @Override
    public String toString()
    {
        return arbol.toStringSimple();
    }
            
}

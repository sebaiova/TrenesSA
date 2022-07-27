package lineales.dinamicas;

/**
 *
 * @author sebastian.iovaldi
 */

public class Lista {
    
    private int SIZE;
    private Nodo first;
    
    public Lista()
    {
        SIZE = 0;
        first = null;
    }
    
    public Object recuperar(int posicion) 
    {
        Object object = null;
        if(posicion>0&&posicion<=SIZE) 
        {
            object = recuperarNodo(posicion).getElem();
        }
        return object;
    }
    
    public boolean insertar(Object object, int posicion) 
    {
        boolean success = false;
        if(posicion>0&&posicion<=SIZE+1) 
        { 
            if(posicion!=1) 
            {
                Nodo prev = recuperarNodo(posicion-1);
                prev.setEnlace(new Nodo(object, prev.getEnlace()));
            }
            else 
                first = new Nodo(object, first);

            SIZE++;
            success = true;
        }
           
        return success;
    }
    
    //Sobrecarga para insertar al final.
    public boolean insertar(Object object) 
    {
        return this.insertar(object, SIZE+1);
    }
    
    public boolean eliminar(int posicion) 
    {  
        boolean success = false;
        if(posicion>0&&posicion<=SIZE) {
        
            if(posicion==1)
                first = first.getEnlace();
            else {
                Nodo prev = recuperarNodo(posicion-1);
                prev.setEnlace(prev.getEnlace().getEnlace());   
            }
            SIZE--;
            success = true;
        }
        return success;
    }   
    
    //Elimina la primer ocurrencia del objecto de la lista con el mismo valor. Retorna false si no elimina.
    public boolean eliminarObjeto(Object object)
    {
        boolean success = false;
        if(first.getElem().equals(object))
        {
            first = first.getEnlace();
            success = true;
        }
        Nodo nodo = first;
        while(nodo.getEnlace()!=null && !success) {
            if(nodo.getEnlace().getElem().equals(object))
            {
                nodo.setEnlace(nodo.getEnlace().getEnlace());
                success = true;
            }
            else
                nodo = nodo.getEnlace();            
        }
        return true;
    }
    
    public int localizar(Object object) {
        int index = -1;
        Nodo nodo = first;
        int i = 1;
        while(nodo!=null && index == -1) {
            if(nodo.getElem().equals(object))
                index = i;
            nodo = nodo.getEnlace();
            i++;
        }
        
        return index;
    }
    
    public boolean esVacia() 
    {
        return SIZE == 0;
    }
    
    public void vaciar() {
        SIZE = 0;
        first = null;
    }
    
    public int longitud() {
        return SIZE;
    }
            
    @Override
    public String toString() {
        Nodo nodo = first;
        String string = "";
        while(nodo!=null) {
            string = string+"["+nodo.getElem()+"]";
            nodo = nodo.getEnlace();
        }
        return string;
    }
    
    private Nodo recuperarNodo(int pos) {
        Nodo nodo = first;
        if(nodo!=null)
            while(pos>1)
            {
                nodo = nodo.getEnlace();
                pos--;
            }
        return nodo;
    }
 
    public Lista clone() {
        Lista newLista = new Lista();
        newLista.deepNodoCopy(first);
        newLista.SIZE = this.SIZE;
        return newLista;
    }
    
    //Copia los nodos enlazados al tope de la pila.
    private void deepNodoCopy(Nodo nodo) {
        if(nodo!=null) {
            deepNodoCopy(nodo.getEnlace());
            first = new Nodo(nodo.getElem(), first);
        }
    }
    
    public Lista obtenerMultiplos(int n)
    {
        Lista lista = new Lista();
        obtenerMultiplos(n, first, 1, lista);
        
        return lista;
    }
    
    private void obtenerMultiplos(int n, Nodo nodo, int count, Lista lista)
    {
        if(nodo!=null)
        {
            if(count%n==0) 
                lista.insertar(nodo.getElem());
            obtenerMultiplos(n, nodo.getEnlace(), ++count, lista);
        }
    }
    
    public void eliminarApariciones(Object object)
    {
        if(first!=null)
            while(object.equals(first.getElem()))
                first = first.getEnlace();
        
        eliminarApariciones(object, first);
    }
    
    private void eliminarApariciones(Object object, Nodo nodo)
    {       
        if(nodo!=null) {
            while(nodo.getEnlace()!=null && object.equals(nodo.getEnlace().getElem())) 
                nodo.setEnlace(nodo.getEnlace().getEnlace());
            eliminarApariciones(object, nodo.getEnlace());
        }
    }
}

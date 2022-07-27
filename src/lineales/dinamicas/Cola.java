package lineales.dinamicas;

/**
 *
 * @author sebastian.iovaldi
 */

public class Cola {
    
    private Nodo first;
    private Nodo end;
    
    public Cola() {
        first = null;
        end = null;
    }
    
    public boolean poner(Object object) {
        Nodo newNodo = new Nodo(object, null);
        if(esVacia())
            end=first=newNodo;
        else {
            end.setEnlace(newNodo);
            end = newNodo;
        }
        return true;
    }

    public boolean sacar() {
        boolean success = false;
        if(!esVacia()) {
            first = first.getEnlace();
            success = true;
            if(first==null)
                end = null;
        }
        return success;
    }
    
    public Object obtenerFrente() {
        Object object = null;
        if(first!=null)
            object = first.getElem();
        return object;
    }
    
    public boolean esVacia() {
        return first == null;
    }
    
    public void vaciar() {
        end = null;
        first = null;
    }

    @Override
    public Cola clone() {
        Cola newCola = new Cola();
        newCola.deepNodoCopy(first);
        return newCola;
    }
    
    //Copia los nodos enlazados de la cola.
    private void deepNodoCopy(Nodo nodo) {
        if(nodo!=null) {
            Nodo newNodo = new Nodo(nodo.getElem(), null);
            if(esVacia())
                end=first=newNodo;
            else {
                end.setEnlace(newNodo);
                end = newNodo;
            }
            this.deepNodoCopy(nodo.getEnlace());
        }
    }
    
    @Override
    public String toString() {
        Nodo nodo = this.first;
        String output = "";
        while(nodo!=null) {
            output = output + "["+nodo.getElem()+"]";
            nodo = nodo.getEnlace();
        }
        return output;
    }
}
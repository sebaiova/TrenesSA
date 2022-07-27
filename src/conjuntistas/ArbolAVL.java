package conjuntistas;

import lineales.dinamicas.Lista;

/**
 *
 * @author sebastian.iovaldi
 */

public class ArbolAVL {
    
    NodoAVL raiz;
    
    public ArbolAVL()
    {
        raiz = null;
    }
    
    public boolean insertar(Comparable obj)
    {
        boolean success = true;
        if(raiz==null)
            raiz = new NodoAVL(obj, null, null);
        else {
            success = insertar(obj, raiz, null, 'U');
        }
        return success;
    }
    
    private boolean insertar(Comparable obj, NodoAVL nodo, NodoAVL padre, char wichChild)
    {
        boolean success = true;
        int comparacion = obj.compareTo(nodo.getElem());
        if(comparacion > 0)
        {
            if(nodo.getDerecho() == null)
                nodo.setDerecho(new NodoAVL(obj, null, null));
            else
                success = insertar(obj, nodo.getDerecho(), nodo, 'D');
        }
        else if(comparacion < 0)
        {
            if(nodo.getIzquierdo() == null)
                nodo.setIzquierdo(new NodoAVL(obj, null, null));
            else
                success = insertar(obj, nodo.getIzquierdo(), nodo, 'I');
        }
        else
            success = false;
        
        if(success)
        {
            balancear(nodo, padre, wichChild);
            nodo.recalcularAltura();
        }

        return success;
    }
    
    public boolean eliminar(Comparable obj)
    {
        boolean success = false;
        if(raiz!=null) {
            success = eliminar(obj, raiz, null, 'U'); 
        
        }
        return success;
    }
    
    private boolean eliminar(Comparable obj, NodoAVL nodo, NodoAVL padre, char wichChild) // wichChild: I para izquierdo, D para derecho
    {
        boolean success = false;
        NodoAVL reemplazo = null;
        int comparacion = obj.compareTo(nodo.getElem());
        
        //Busca el nodo a eliminar de forma recursiva
        if(comparacion > 0 && nodo.getDerecho()!=null)
            success = eliminar(obj, nodo.getDerecho(), nodo, 'D');
        else if(comparacion < 0 && nodo.getIzquierdo() != null) 
            success = eliminar(obj, nodo.getIzquierdo(), nodo, 'I');
      
        //Cuando lo encuentra
        else if(comparacion == 0)
        {
            success = true; 
            //Primer caso es default
            
            //Segundo caso
            if(nodo.getIzquierdo()!=null && nodo.getDerecho() == null)
                reemplazo = nodo.getIzquierdo();

            else if(nodo.getIzquierdo()==null && nodo.getDerecho() != null)
                reemplazo = nodo.getDerecho();

            //Tercer caso
            else if(nodo.getIzquierdo()!=null && nodo.getDerecho() != null)
            {
                reemplazo = extraerMayorDelMenor(nodo.getIzquierdo(), nodo);
                reemplazo.setIzquierdo(nodo.getIzquierdo());
                reemplazo.setDerecho(nodo.getDerecho());
            }

            if(reemplazo!=null) {
                reemplazo.recalcularAltura();
                nodo = reemplazo; // <--- al final se balancea nodo
            }
            //Cambia los nodos, el eliminado por su reemplazo
            switch (wichChild) 
            {
                case 'U' -> this.raiz = reemplazo;
                case 'I' -> padre.setIzquierdo(reemplazo);
                case 'D' -> padre.setDerecho(reemplazo);
            }
        }
        
        if(success)
        {
            nodo.recalcularAltura();
            balancear(nodo, padre, wichChild);
            nodo.recalcularAltura();
        }
        return success;
    }
    
    private NodoAVL extraerMayorDelMenor(NodoAVL nodo, NodoAVL padre)
    {
        NodoAVL mayor;
        if(nodo.getDerecho()==null)
        {
            mayor = nodo;
            padre.setIzquierdo(null);
        }
        else 
            mayor = extraerMayorDelMenorR(nodo.getDerecho(), nodo);
        nodo.recalcularAltura();
        return mayor;
    }
    
    private NodoAVL extraerMayorDelMenorR(NodoAVL nodo, NodoAVL padre)
    {
        NodoAVL mayor;
        if(nodo.getDerecho()==null) {
            mayor=nodo;
            padre.setDerecho(null);
        }
        else
            mayor=extraerMayorDelMenorR(nodo.getDerecho(), nodo);
        nodo.recalcularAltura();
        return mayor;
    }
    
    private void balancear(NodoAVL nodo, NodoAVL padre, char wichChild)
    {
        int balance;
        NodoAVL tmp = null;
        balance = obtenerBalance(nodo);
        if(balance == -2) 
        {            
            if(obtenerBalance(nodo.getDerecho()) == 1) {
                nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
            }
            tmp = rotarIzquierda(nodo);
        }
        else if(balance == 2)
        {
            if(obtenerBalance(nodo.getIzquierdo()) == -1) {
                nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
            }
            tmp = rotarDerecha(nodo);
        }
        if(tmp!=null) 
        {
            switch (wichChild) 
            {
                case 'U' -> this.raiz = tmp;
                case 'I' -> padre.setIzquierdo(tmp);
                case 'D' -> padre.setDerecho(tmp);
            }
        }
    }
    
    private int obtenerBalance(NodoAVL nodo)
    {
        int balance = (nodo.getIzquierdo()==null?-1 : nodo.getIzquierdo().getAltura()) - (nodo.getDerecho()==null?-1 : nodo.getDerecho().getAltura());
        return balance;
    }
    
    private NodoAVL rotarIzquierda(NodoAVL r)
    {
        NodoAVL h = r.getDerecho();
        NodoAVL tmp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(tmp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }
    
    private NodoAVL rotarDerecha(NodoAVL r)
    {
        NodoAVL h = r.getIzquierdo();
        NodoAVL tmp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(tmp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }
    
    public boolean pertenece(Comparable obj)
    {
        boolean pertenece = false;
        if(raiz!=null)
            pertenece = perteneceR(obj, raiz);    
        return pertenece;
    }    
    
    private boolean perteneceR(Comparable obj, NodoAVL nodo)
    {
        boolean pertenece = false;
        int comparacion = obj.compareTo(nodo.getElem());
        
        if(comparacion == 0)
            pertenece = true;
        else if(comparacion > 0 && nodo.getDerecho()!=null)
            pertenece = perteneceR(obj, nodo.getDerecho());
        else if(comparacion < 0 && nodo.getIzquierdo()!=null)
            pertenece = perteneceR(obj, nodo.getIzquierdo());
 
        return pertenece;
    }
    
    public Object obtener(Comparable obj)
    {
        Object encontrado = null;
        if(raiz!=null)
            encontrado = obtenerR(obj, raiz);    
        return encontrado;
    }
    
    private Object obtenerR(Comparable obj, NodoAVL nodo)
    {
        Object encontrado = null;
        int comparacion = obj.compareTo(nodo.getElem());
        
        if(comparacion == 0)
            encontrado = nodo.getElem();
        else if(comparacion > 0 && nodo.getDerecho()!=null)
            encontrado = obtenerR(obj, nodo.getDerecho());
        else if(comparacion < 0 && nodo.getIzquierdo()!=null)
            encontrado = obtenerR(obj, nodo.getIzquierdo());
 
        return encontrado;
    }
    
    public Object minimoElem()
    {
        NodoAVL min = raiz;
        Object obj = null;
        
        if(min!=null)
        {       
            while(min.getIzquierdo()!=null)
                min = min.getIzquierdo();

            obj = min.getElem();
        } 
        return obj;
    }
    
    public Object maximoElem()
    {
        NodoAVL max = raiz;
        Object obj = null;
        
        if(max!=null)
        {       
            while(max.getDerecho()!=null)
                max = max.getDerecho();

            obj = max.getElem();
        }
        return obj;  
    }
    
    public Lista listarPreorden() 
    {
        Lista lista = new Lista();
        if(!esVacio())
            listarPreordenR(lista, raiz);
        
        return lista;
    }
    
    private void listarPreordenR(Lista lista, NodoAVL nodo) 
    {
        lista.insertar(nodo.getElem());
        if(nodo.getIzquierdo()!=null)
            listarPreordenR(lista, nodo.getIzquierdo());
        if(nodo.getDerecho()!=null)
            listarPreordenR(lista, nodo.getDerecho());
    }
    
    public Lista listarPosOrden() 
    {
        Lista lista = new Lista();
        if(!esVacio())
            listarPosOrdenR(lista, raiz);
        
        return lista;
    }
    
    private void listarPosOrdenR(Lista lista, NodoAVL nodo) 
    {
        if(nodo.getIzquierdo()!=null)
            listarPosOrdenR(lista, nodo.getIzquierdo());
        if(nodo.getDerecho()!=null)
            listarPosOrdenR(lista, nodo.getDerecho());
        lista.insertar(nodo.getElem());
    }
    
    public Lista listarInorden() 
    {
        Lista lista = new Lista();
        if(!esVacio())
            listarInordenR(lista, raiz);
        
        return lista;
    }
    
    private void listarInordenR(Lista lista, NodoAVL nodo) 
    {
        if(nodo.getIzquierdo()!=null)
            listarInordenR(lista, nodo.getIzquierdo());
        lista.insertar(nodo.getElem());
        if(nodo.getDerecho()!=null)
            listarInordenR(lista, nodo.getDerecho());
    }
    
    public boolean esVacio()
    {
        return raiz == null;
    }    
    
    public void vaciar()
    {
        raiz = null;
    }
    
    @Override
    public String toString() 
    {
        return raiz==null? "" : toString(raiz);
    }
    
    private String toString(NodoAVL nodo) 
    {
        String sLeft = nodo.getIzquierdo()==null? "-" : nodo.getIzquierdo().getElem().toString();
        String sRight = nodo.getDerecho()==null? "-" : nodo.getDerecho().getElem().toString();
        return nodo.getAltura()+" ["+nodo.getElem().toString()+"]: [ "+sLeft+" | "+sRight+" ]" + (nodo.getIzquierdo()==null? "" : "\n" + toString(nodo.getIzquierdo())) + (nodo.getDerecho()==null? "" : "\n" + toString(nodo.getDerecho()));
    }
    
    public String toStringSimple()
    {
        return raiz==null? "" : toStringSimpleR(raiz);
    }
    
    public String toStringSimpleR(NodoAVL nodo)
    {
        String string = "";
        if(nodo.getIzquierdo()!=null)
            string += toStringSimpleR(nodo.getIzquierdo());
        string += String.format("[%s]", nodo.getElem().toString());
        if(nodo.getDerecho()!=null)
            string += toStringSimpleR(nodo.getDerecho());
        
        return string;
    }
}

package conjuntistas;

import lineales.dinamicas.Lista;

/**
 *
 * @author sebastian.iovaldi
 */

public class Diccionario {

    NodoAVLDicc raiz;

    public Diccionario()
    {
        raiz = null;
    }

    public boolean insertar(Comparable clave, Object dato)
    {
        boolean success = true;
        if(raiz==null)
            raiz = new NodoAVLDicc(clave, dato, null, null);
        else 
            success = insertar(clave, dato, raiz, null, 'U');
        
        return success;
    }

    private boolean insertar(Comparable clave, Object dato, NodoAVLDicc nodo, NodoAVLDicc padre, char wichChild)
    {
        boolean success = true;
        int comparacion = clave.compareTo(nodo.getClave());
        if(comparacion > 0)
        {
            if(nodo.getDerecho() == null)
                nodo.setDerecho(new NodoAVLDicc(clave, dato, null, null));
            else
                success = insertar(clave, dato, nodo.getDerecho(), nodo, 'D');
        }
        else if(comparacion < 0)
        {
            if(nodo.getIzquierdo() == null)
                nodo.setIzquierdo(new NodoAVLDicc(clave, dato, null, null));
            else
                success = insertar(clave, dato, nodo.getIzquierdo(), nodo, 'I');
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
    
    public boolean eliminar(Comparable clave)
    {
        boolean success = false;
        if(raiz!=null) 
            success = eliminar(clave, raiz, null, 'U'); 
        return success;
    }
    
    private boolean eliminar(Comparable clave, NodoAVLDicc nodo, NodoAVLDicc padre, char wichChild) // wichChild: I para izquierdo, D para derecho
    {
        boolean success = false;
        NodoAVLDicc reemplazo = null;
        int comparacion = clave.compareTo(nodo.getClave());
        
        //Busca el nodo a eliminar de forma recursiva
        if(comparacion > 0 && nodo.getDerecho()!=null)
            success = eliminar(clave, nodo.getDerecho(), nodo, 'D');
        else if(comparacion < 0 && nodo.getIzquierdo() != null) 
            success = eliminar(clave, nodo.getIzquierdo(), nodo, 'I');
      
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
    
    private NodoAVLDicc extraerMayorDelMenor(NodoAVLDicc nodo, NodoAVLDicc padre)
    {
        NodoAVLDicc mayor;
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
    
    private NodoAVLDicc extraerMayorDelMenorR(NodoAVLDicc nodo, NodoAVLDicc padre)
    {
        NodoAVLDicc mayor;
        if(nodo.getDerecho()==null)
        {
            mayor=nodo;
            padre.setDerecho(null);
        }
        else
            mayor=extraerMayorDelMenorR(nodo.getDerecho(), nodo);
        nodo.recalcularAltura();
        return mayor;
    }
    
    private void balancear(NodoAVLDicc nodo, NodoAVLDicc padre, char wichChild)
    {
        int balance;
        NodoAVLDicc tmp = null;
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
    
    private int obtenerBalance(NodoAVLDicc nodo)
    {
        int balance = (nodo.getIzquierdo()==null?-1 : nodo.getIzquierdo().getAltura()) - (nodo.getDerecho()==null?-1 : nodo.getDerecho().getAltura());
        return balance;
    }
    
    private NodoAVLDicc rotarIzquierda(NodoAVLDicc r)
    {
        NodoAVLDicc h = r.getDerecho();
        NodoAVLDicc tmp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(tmp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }
    
    private NodoAVLDicc rotarDerecha(NodoAVLDicc r)
    {
        NodoAVLDicc h = r.getIzquierdo();
        NodoAVLDicc tmp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(tmp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }
    
    public boolean pertenece(Comparable clave)
    {
        boolean pertenece = false;
        if(raiz!=null)
            pertenece = perteneceR(clave, raiz);    
        return pertenece;
    }
    
    private boolean perteneceR(Comparable clave, NodoAVLDicc nodo)
    {
        boolean pertenece = false;
        int comparacion = clave.compareTo(nodo.getClave());
        
        if(comparacion == 0)
            pertenece = true;
        else if(comparacion > 0 && nodo.getDerecho()!=null)
            pertenece = perteneceR(clave, nodo.getDerecho());
        else if(comparacion < 0 && nodo.getIzquierdo()!=null)
            pertenece = perteneceR(clave, nodo.getIzquierdo());
 
        return pertenece;
    }
    
    public Object obtener(Comparable clave)
    {
        Object encontrado = null;
        if(raiz!=null)
            encontrado = obtenerR(clave, raiz);    
        return encontrado;
    }
    
    private Object obtenerR(Comparable clave, NodoAVLDicc nodo)
    {
        Object encontrado = null;
        int comparacion = clave.compareTo(nodo.getClave());
        
        if(comparacion == 0)
            encontrado = nodo.getDato();
        else if(comparacion > 0 && nodo.getDerecho()!=null)
            encontrado = obtenerR(clave, nodo.getDerecho());
        else if(comparacion < 0 && nodo.getIzquierdo()!=null)
            encontrado = obtenerR(clave, nodo.getIzquierdo());
 
        return encontrado;
    }
    
    //Retorna una lista con las claves ordenadas (inorden)
    public Lista listarClaves() 
    {
        Lista lista = new Lista();
        if(!esVacio())
            listarClavesR(lista, raiz);
        
        return lista;
    }
    
    private void listarClavesR(Lista lista, NodoAVLDicc nodo) 
    {
        if(nodo.getIzquierdo()!=null)
            listarClavesR(lista, nodo.getIzquierdo());
        lista.insertar(nodo.getClave());
        if(nodo.getDerecho()!=null)
            listarClavesR(lista, nodo.getDerecho());
    }
    
    public Object minimoElem()
    {
        NodoAVLDicc min = raiz;
        Object obj = null;
        
        if(min!=null)
        {       
            while(min.getIzquierdo()!=null)
                min = min.getIzquierdo();

            obj = min.getDato();
        } 
        return obj;
    }
    
    public Object maximoElem()
    {
        NodoAVLDicc max = raiz;
        Object obj = null;
        
        if(max!=null)
        {       
            while(max.getDerecho()!=null)
                max = max.getDerecho();

            obj = max.getDato();
        }
        return obj;  
    }

    public Lista listarRango(Comparable min, Comparable max)
    {
        Lista lista = new Lista();
        if(raiz!=null)
            listarRango(min, max, raiz, lista);
        return lista;
    }
    
    private void listarRango(Comparable min, Comparable max, NodoAVLDicc nodo, Lista lista)
    {
        int comparacionIzq = min.compareTo(nodo.getClave());
        int comparacionDer = max.compareTo(nodo.getClave());
        
        if(nodo.getDerecho()!=null && comparacionDer >= 0)
            listarRango(min, max, nodo.getDerecho(), lista);
        
        if(comparacionIzq <=0 && comparacionDer >=0)
            lista.insertar(nodo.getClave(), 1);
        
        if(nodo.getIzquierdo()!=null && comparacionIzq <= 0)
            listarRango(min, max, nodo.getIzquierdo(), lista);
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
    
    private String toString(NodoAVLDicc nodo) 
    {
        String sLeft = nodo.getIzquierdo()==null? "-" : nodo.getIzquierdo().getClave().toString();
        String sRight = nodo.getDerecho()==null? "-" : nodo.getDerecho().getClave().toString();
        return nodo.getAltura()+" ["+nodo.getClave().toString()+"]: [ "+sLeft+" | "+sRight+" ]" + (nodo.getIzquierdo()==null? "" : "\n" + toString(nodo.getIzquierdo())) + (nodo.getDerecho()==null? "" : "\n" + toString(nodo.getDerecho()));
    }
    
    public String toStringSimple()
    {
        return raiz==null? "" : toStringSimpleR(raiz);
    }
    
    public String toStringSimpleR(NodoAVLDicc nodo)
    {
        String string = "";
        if(nodo.getIzquierdo()!=null)
            string += toStringSimpleR(nodo.getIzquierdo());
        string += String.format("[%s]", nodo.getClave().toString());
        if(nodo.getDerecho()!=null)
            string += toStringSimpleR(nodo.getDerecho());
        return string;
    }
}

package conjuntistas;

/**
 *
 * @author sebastian.iovaldi
 */

public class ArbolHeapMin {
    
    int ultimo;
    Comparable[] array;
        
    public ArbolHeapMin(int size)
    {
        this.ultimo = 0;
        this.array = new Comparable[size];
    }
    
    public boolean esVacio()
    {
        return ultimo==0;
    }

    public boolean insertar(Comparable elem)
    {
        array[++ultimo] = elem;
        hacerSubir(ultimo);
        
        return true;
    }
    
    public boolean actualizarValor(Comparable elem)
    {
        boolean success = true;
        int pos = localizarElem(elem);
        if(pos != -1) 
        {
            int comparacion = elem.compareTo(array[pos]);
            array[pos] = elem;
            if(comparacion > 0)
                hacerBajar(pos);
            else if(comparacion < 0)
                hacerSubir(pos);
        }
        else
            success = false;
        return success;
    }
    
    public Object recuperarCima() {
        return array[1];
    }
    
    public boolean eliminiarCima()
    {
        array[1] = array[ultimo];
        array[ultimo--] = null;
        hacerBajar(1);    
        return true;
    }
    
    public Comparable obtener(Comparable elem) 
    {
        Comparable encontrado = null;
        int i=1;
        while( encontrado == null && i<=this.ultimo ) 
        {
            if(array[i].equals(elem))
                encontrado = array[i];
            else 
                i++;
        }
        return encontrado;
    }
    
    private void hacerSubir(int pos)
    {
        if(pos > 1)
        {
            if(array[pos].compareTo(array[pos/2]) < 0)
            {
                swap(pos, pos/2);
                hacerSubir(pos/2);
            }
        }
    }
    
    private void hacerBajar(int pos)
    {
        int min = -1;
        
        if(pos*2+1 <= ultimo)
            min = candidatoABajar(pos*2, pos*2+1);
        
        else if(pos*2 <= ultimo)
            min = pos*2;
        
        if(min!=-1)
            if( array[pos].compareTo(array[min]) > 0 )
            {
                swap(pos, min);
                hacerBajar(min);
            }
    }
    
    int candidatoABajar(int x, int y) 
    {
        return array[x].compareTo(array[y]) < 0 ? x : y;
    }
    
    private void swap(int x, int y)
    {
        Comparable tmp = array[x];
        array[x] = array[y];
        array[y] = tmp;
    }
    
    private int localizarElem(Object elem) 
    {
        int index = -1;
        int i=1;
        while( index == -1 && i<=this.ultimo ) 
        {
            if(array[i].equals(elem))
                index = i;
            else 
                i++;
        }
        return index;
    }
    
    public String toString()
    {
        String string = "";
        for(int i=1; i<=ultimo; i++)
            string += "["+array[i].toString()+"]";
        string += "\n";
        return string;
    }
    
}
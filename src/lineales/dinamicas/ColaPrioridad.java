package lineales.dinamicas;

/**
 *
 * @author sebastian.iovaldi
 */
public class ColaPrioridad {
    
    int ultimo;
    CeldaCP[] array;
    int ordenLlegada;
    
    public ColaPrioridad(int size)
    {
        this.ultimo = 0;
        this.ordenLlegada = 0;
        this.array = new CeldaCP[size];
    }
    
    public boolean esVacio()
    {
        return ultimo==0;
    }

    public boolean insertar(Object elem, int prioridad)
    {
        array[++ultimo] = new CeldaCP(elem, prioridad, ordenLlegada++);
        hacerSubir(ultimo);
        return true;
    }
    
    public Object recuperarFrente() 
    {
        return array[1].getElem();
    }
    
    public boolean eliminiarFrente()
    {
        array[1] = array[ultimo];
        array[ultimo--] = null;
        hacerBajar(1);    
        return true;
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
    
    private int candidatoABajar(int x, int y) 
    {
        return array[x].compareTo(array[y]) < 0 ? x : y;
    }
    
    private void swap(int x, int y)
    {
        CeldaCP tmp = array[x];
        array[x] = array[y];
        array[y] = tmp;
    }
    
    @Override
    public String toString()
    {
        String string = "";
        for(int i=1; i<=ultimo; i++)
            string += "["+array[i].toString()+"]";
        string += "\n";
        return string;
    }
}

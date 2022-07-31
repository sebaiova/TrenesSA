package conjuntistas;


/**
 *
 * @author sebastian.iovaldi
 */

public class TestingArbolHeapMin {
    
    
    public static void main(String[] arg) {
            testArbolHeapMin();
    }
    
    public static void testArbolHeapMin()
    {
        ArbolHeapMin arbol = new ArbolHeapMin(20);
        boolean success = true;
        arbol.insertar(4);
        arbol.insertar(7);
        arbol.insertar(14);
        arbol.insertar(22);
        arbol.insertar(2);
        arbol.insertar(7);
        arbol.insertar(15);
        arbol.insertar(2);
        arbol.insertar(8);
        arbol.insertar(9);
        
        int valorEsperado[] = {2, 2, 4, 7, 7, 8, 9, 14, 15, 22};
        
        int i=0;
        while(success && !arbol.esVacio())
        {
            success = (int)arbol.recuperarCima() == valorEsperado[i];
            arbol.eliminiarCima();
            i++;
        }
        
        System.out.printf("Testing de ArbolHeapMin... [%s].\n\n", success? "OK" : "ERROR");
    }
    
}

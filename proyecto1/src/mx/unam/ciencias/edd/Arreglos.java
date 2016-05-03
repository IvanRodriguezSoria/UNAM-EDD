package mx.unam.ciencias.edd;

/**
 * Clase para manipular arreglos genéricos de elementos comparables.
 */
public class Arreglos {

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param a un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void quickSort(T[] a) {
        int ini = 0; 
        int fin = a.length - 1; 
        sort(a, ini, fin); 
    }
    
    // Metodo auxiliar que ordena un arreglo  
    private static <T extends Comparable<T>> 
    void sort(T[] a, int ini, int fin) { 
        if(fin <= ini) 
            return; 
        int i = ini + 1; 
        int j = fin; 
        while(i < j) 
        { 
            if(a[i].compareTo(a[ini]) > 0 && a[j].compareTo(a[ini]) <= 0) 
                intercambia(a, i++, j--); 
            else if(a[i].compareTo(a[ini]) <= 0) 
                i++; 
            else 
                j--; 
        } 
        if(a[i].compareTo(a[ini]) > 0) 
            i--; 
        intercambia(a, ini, i); 
        sort(a, ini, i - 1); 
        sort(a, i + 1, fin); 
    } 
 
    // Metodo auxiliar que intercambia dos elementos de un array 
    private static <T extends Comparable<T>> 
    void intercambia(T[] a, int i, int j) { 
        T auxiliar = a[i]; 
        a[i] = a[j]; 
        a[j] = auxiliar; 
    } 


    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param a un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void selectionSort(T[] a) {
        int longitud = a.length; 
        int minimo; 
        T auxiliar; 
        for(int i = 0; i < longitud - 1; i++)
        { 
            minimo = i; 
            for(int j = i + 1; j < longitud; j++)
            { 
                if( a[j].compareTo( a[minimo] ) < 0 ) 
                minimo = j; 
            } 
            if(minimo != i)
            { 
                auxiliar = a[i]; 
                a[i] = a[minimo]; 
                a[minimo] = auxiliar; 
            } 
        } 
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param a el arreglo dónde buscar.
     * @param e el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int busquedaBinaria(T[] a, T e) {
        int ini = 0; 
        int fin = a.length - 1; 
        int centro = (ini + fin) / 2; 
        while(ini <= fin) 
        { 
            centro = (ini + fin) / 2; 
            if(a[centro].compareTo(e) > 0) 
            { 
                fin = centro - 1; 
            }
            else if(a[centro].compareTo(e) < 0) 
            { 
                ini = centro + 1; 
            }
            else 
                return centro; 
        }
         
        return -1;  
    }
}

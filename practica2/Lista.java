package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas implementan la interfaz {@link Iterable}, y por lo tanto se
 * pueden recorrer usando la estructura de control <em>for-each</em>. Las listas
 * no aceptan a <code>null</code> como elemento.</p>
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase Nodo privada para uso interno de la clase Lista. */
    private class Nodo {
        public T elemento;
        public Nodo anterior;
        public Nodo siguiente;

        public Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase Iterador privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        public Lista<T>.Nodo anterior;
        public Lista<T>.Nodo siguiente;

        public Iterador() {
            anterior = null;
            siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if( !hasNext() )
                throw new NoSuchElementException();
                
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if( !hasPrevious() )
                throw new NoSuchElementException();
                
            siguiente = anterior;
            anterior = anterior.anterior;
            return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            anterior = null;
            siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            anterior = rabo;
            siguiente = null;
        }

        /* No implementamos este método. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    public int getElementos() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    public boolean esVacio() {
        return cabeza == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. Después de llamar este
     * método, el iterador apunta a la cabeza de la lista. El método es idéntico
     * a {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agrega(T elemento) {
        if(elemento == null)
            throw new IllegalArgumentException();
            
        if(cabeza == null)
        {
            cabeza = new Nodo(elemento);
            rabo = cabeza;
        }
        else
        {
            rabo.siguiente = new Nodo(elemento);
            rabo.siguiente.anterior = rabo;
            rabo = rabo.siguiente;
        }
        
        ++longitud;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último. Después de llamar este
     * método, el iterador apunta a la cabeza de la lista.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if(elemento == null)
            throw new IllegalArgumentException();
            
        if(cabeza == null) 
        {
            cabeza = new Nodo(elemento);
            rabo = cabeza;
        }
        else 
        {
            rabo.siguiente = new Nodo(elemento);
            rabo.siguiente.anterior = rabo;
            rabo = rabo.siguiente;
        }
        
        ++longitud;
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último. Después de llamar este
     * método, el iterador apunta a la cabeza de la lista.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if(elemento == null)
            throw new IllegalArgumentException();
        
        if(cabeza == null) 
        {
            cabeza = new Nodo(elemento);
            rabo = cabeza;
        }
        else 
        {
            cabeza.anterior = new Nodo(elemento);
            cabeza.anterior.siguiente = cabeza;
            cabeza = cabeza.anterior;
        }
        
        ++longitud;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica. Si un elemento de la lista es
     * modificado, el iterador se mueve al primer elemento.
     * @param elemento el elemento a eliminar.
     */
    public void elimina(T elemento) {
        Nodo n = cabeza;
        while(n != null) 
        {
            if(n.elemento.equals(elemento) ) 
            {
                if(n.siguiente == null)
                    eliminaUltimo();
                else if(n.anterior == null)
                    eliminaPrimero();
                else 
                {
                    n.siguiente.anterior = n.anterior;
                    n.anterior.siguiente = n.siguiente;
                    --longitud;
                }
                
                break;
            }
            
            n = n.siguiente;
        }
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if(cabeza == null)
            throw new NoSuchElementException();
            
        T e = cabeza.elemento;
        if(cabeza.siguiente == null) 
        {
            cabeza = null;
            rabo = null;
        }
        else 
        {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        }
        --longitud;
        return e;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if(rabo == null)
            throw new NoSuchElementException();

        T e = rabo.elemento;
        if(rabo.anterior == null) 
        {
            cabeza = null;
            rabo = null;
        }
        else 
        {
            rabo = rabo.anterior;
            rabo.siguiente = null;
        }
        --longitud;
        return e;
    }

    /**
     * Nos dice si un elemento está en la lista. El iterador no se mueve.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(T elemento) {
        Nodo n = cabeza;
        while(n != null) 
        {
            if(n.elemento.equals(elemento) )
                return true;
            n = n.siguiente;
        }
        
        return false;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> lista = new Lista<T>();
        Nodo n = cabeza;
        while(n != null) 
        {
            lista.agregaInicio(n.elemento);
            n = n.siguiente;
        }
        
        return lista;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> lista = new Lista<T>();
        Nodo n = cabeza;
        return copia(lista, n);
    }

    /* Método auxiliar recursivo para copia. */
    private Lista<T> copia(Lista<T> c, Nodo nodo) {
        if(nodo == null)
            return c;
            
        c.agregaFinal(nodo.elemento);
        nodo = nodo.siguiente;
        return copia(c, nodo);
    }

    /**
     * Limpia la lista de elementos. El llamar este método es equivalente a
     * eliminar todos los elementos de la lista.
     */
    public void limpia() {
        cabeza = null;
        rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if(cabeza == null) 
            throw new NoSuchElementException();
            
        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if(rabo == null)
            throw new NoSuchElementException();
            
        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if(i < 0 || i >= longitud)
            throw new ExcepcionIndiceInvalido();
            
        Nodo n = cabeza;
        int contador = 0;
        while(n != null) {
            if(contador == i)
                break;
            n = n.siguiente;
            ++contador;
        }
        
        return n.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si
     *         el elemento no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        Nodo n = cabeza;
        int contador = 0;
        while(n != null) {
            if(n.elemento.equals(elemento) )
                return contador;
            n = n.siguiente;
            ++contador;
        }
        
        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        if(cabeza == null)
            return "[]";
            
        String s = "[";
        Nodo n = cabeza;
        for(int i = 0; i < longitud - 1; ++i) 
        {
            s += n.elemento.toString() + ", ";
            n = n.siguiente;
        }
        
        return s + n.elemento.toString() + "]";
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param o el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (!(o instanceof Lista) )
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)o;
        if(longitud != lista.longitud)
            return false;
            
        Nodo m = lista.cabeza;
        Nodo n = cabeza;
        while(n != null) {
            if( !n.elemento.equals(m.elemento) )
                return false;
            m = m.siguiente;
            n = n.siguiente;
        }
        
        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }
    
    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param l la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> l) {
       if(l.longitud < 2) 
           return l.copia(); 
       Lista<T> mitadIzq = new Lista<T>(); 
       Lista<T> mitadDer = new Lista<T>(); 
       int mitad = l.longitud / 2; 
       int contador = 0; 
       for(T e : l) 
       { 
           if( contador++ < mitad ) 
               mitadIzq.agregaFinal(e); 
           else 
               mitadDer.agregaFinal(e); 
       } 
       Lista<T> izquierda = mergeSort( mitadIzq ); 
       Lista<T> derecha = mergeSort( mitadDer ); 
       return merge( izquierda, derecha ); 
    }
    
    // Une de manera ordenada dos listas. 
    // Return: una lista ordenada que contiene los elementos de las 2 listas 
    // que recive como parametros. 
    // Param: Dos listas a ordenar y unir.  
    private static <T extends Comparable<T>> 
    Lista<T> merge(Lista<T> izquierda, Lista<T> derecha) { 
      Lista<T> lista = new Lista<>(); 
      Lista<T>.Nodo nodoIzq = izquierda.cabeza; 
      Lista<T>.Nodo nodoDer = derecha.cabeza; 
      while( nodoIzq != null && nodoDer != null ) 
      { 
          if( nodoIzq.elemento.compareTo(nodoDer.elemento) <= 0 ) 
          { 
              lista.agregaFinal( nodoIzq.elemento ); 
              nodoIzq = nodoIzq.siguiente; 
          }
          else
          { 
              lista.agregaFinal( nodoDer.elemento ); 
              nodoDer = nodoDer.siguiente; 
          } 
      } 
      while( nodoIzq != null ) 
      { 
          lista.agregaFinal( nodoIzq.elemento ); 
          nodoIzq = nodoIzq.siguiente; 
      } 
      while( nodoDer != null ) 
      { 
          lista.agregaFinal( nodoDer.elemento ); 
          nodoDer = nodoDer.siguiente; 
      } 
      return lista; 
    } 

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param l la lista donde se buscará.
     * @param e el elemento a buscar.
     * @return <tt>true</tt> si e está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> l, T e) {
        for(T elemento : l) 
            if(elemento.compareTo(e) == 0) 
                return true;
                 
        return false; 
    }
}

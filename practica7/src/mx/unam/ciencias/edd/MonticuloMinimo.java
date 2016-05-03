package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>). Podemos crear un montículo
 * mínimo con <em>n</em> elementos en tiempo <em>O</em>(<em>n</em>), y podemos
 * agregar y actualizar elementos en tiempo <em>O</em>(log <em>n</em>). Eliminar
 * el elemento mínimo también nos toma tiempo <em>O</em>(log <em>n</em>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T> {

    /* Clase privada para iteradores de montículos. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return indice < arbol.length && arbol[indice] != null;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
			if (!hasNext() )
				throw new NoSuchElementException();
			return arbol[indice++];
        }

        /* No lo implementamos: siempre lanza una excepción. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* El siguiente índice dónde agregar un elemento. */
    private int siguiente;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] creaArregloGenerico(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Lista)}, pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
		siguiente = 0;
		arbol = creaArregloGenerico(1000);
    }

    /**
     * Constructor para montículo mínimo que recibe una lista. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param lista la lista a partir de la cuál queremos construir el
     *              montículo.
     */
    public MonticuloMinimo(Lista<T> lista) {
		siguiente = 0;
        arbol = creaArregloGenerico(lista.getLongitud() );
		for (T e : lista) {
			e.setIndice(siguiente);
        	arbol[siguiente++] = e;
		}
		
		for (int i = getElementos() - 1; i >= 0; --i)
			minHeapify(i);
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
		if (siguiente >= arbol.length)
			duplicaTamañoArreglo();
			
		elemento.setIndice(siguiente);
        arbol[siguiente] = elemento;
        reordenaArriba(siguiente++);
    }
	
	private void duplicaTamañoArreglo() {
		T[] aux;
		int longitud = arbol.length;
		aux = creaArregloGenerico(longitud * 2);
		for (int i = 0; i < longitud; ++i)
			aux[i] = arbol[i];
		arbol = aux;
	}

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    public T elimina() {
		if (esVacio() )
			throw new IllegalStateException();
			
		T aux = arbol[0];
		elimina(aux);
		return aux;
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        if (elemento == null)
            return;
            
		int indice = elemento.getIndice();
        
        if (indice < 0 || indice >= getElementos() )
            return;
		
		T aux = arbol[indice];
		--siguiente;
		
		arbol[siguiente].setIndice(indice);
		arbol[indice] = arbol[siguiente];
		
        aux.setIndice(-1);
		arbol[siguiente] = aux;
		arbol[siguiente] = null;
		
        reordena(arbol[indice]);
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        for (T e : arbol)
			if (e.equals(elemento) )
				return true;
		
		return false;
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <tt>true</tt> si ya no hay elementos en el montículo,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean esVacio() {
        return arbol == null || arbol[0] == null;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    public void reordena(T elemento) {
        if (elemento == null)
            return;
            
		int indice = elemento.getIndice();
		reordenaArriba(indice);
		minHeapify(indice);
	}
	
	private void reordenaArriba(int i) {
        if (i < 0 || i >= getElementos() )
            return;
        
		T aux;
		int menor = i;
		int padre = (i - 1) / 2;
		
		if (padre >= 0 && arbol[menor].compareTo(arbol[padre]) < 0)
			menor = padre;
			
		if (menor != i) {
			aux = arbol[i];
			
			arbol[i].setIndice(menor);
			arbol[i] = arbol[menor];
			
			arbol[menor].setIndice(i);
			arbol[menor] = aux;
			
			reordenaArriba(menor);
		}
	}
	
	private void minHeapify(int i) {
        if (i < 0 || i >= getElementos() )
            return;
            
		T aux;
		int menor = i;
		int izq = (2 * i) + 1;
		int der = (2 * i) + 2;
		
		if (izq < getElementos() && arbol[menor].compareTo(arbol[izq]) > 0)
			menor = izq;
		if (der < getElementos() && arbol[menor].compareTo(arbol[der]) > 0)
			menor = der;
			
		if (menor != i) {
			aux = arbol[i];
			
			arbol[i].setIndice(menor);
			arbol[i] = arbol[menor];
			
			arbol[menor].setIndice(i);
			arbol[menor] = aux;
			
			minHeapify(menor);
		}
	}

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
		return siguiente;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    public T get(int i) {
        if (i < 0 || i >= getElementos() )
            throw new NoSuchElementException();
            
		return arbol[i];
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}

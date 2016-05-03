package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 * Autor: ivan rodriguez soria.
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios ordenados. */
    private class Iterador implements Iterator<T> {

        /* Pila para emular la pila de ejecución. */
        private Pila<ArbolBinario<T>.Vertice> pila;

        /* Construye un iterador con el vértice recibido. */
        public Iterador() {
            pila = new Pila<>();
            Vertice vi = raiz;

            // Crea una pila con todos los vertices izquierdos del arbol.
            // Esto se hace para preservar el orden al usar el metodo next().
            // El orden es: Recorrer empezando por el vertice mas pequeño hasta
            // llegar al mas grande.
            while (vi != null) {
                pila.mete(vi);
                vi = vi.izquierdo;
            }
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento del árbol en orden. */
        @Override public T next() {
            // Se saca el ultimo vertice de la pila. Su elemento es lo que
            // se regresara.
            Vertice v = pila.saca();
            Vertice auxiliar;

            // Si existe un vertice derecho en el vertice actual, metemos
            // todos los hijos izquierdos que se encuentren a la pila.
            // De esta manera recorremos el arbol en orden. Este orden es:
            // Recorrer el arbol desde el vertice mas pequeño hasta el mas grande.
            if (v.hayDerecho()) {
                auxiliar = v.derecho;
                while (auxiliar != null) {
                    pila.mete(auxiliar);
                    auxiliar = auxiliar.izquierdo;
                }
            }
            return v.elemento;
        }

        /* No lo implementamos: siempre lanza una excepción. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
   @Override public void agrega(T elemento) {
       if (elemento == null)
           throw new IllegalArgumentException();

       Vertice auxVertice = nuevoVertice(elemento);
       ultimoAgregado = auxVertice;
       ++elementos;

        if (raiz == null)
            raiz = auxVertice;
        else
            auxAgrega(raiz, elemento, auxVertice);
    }

    /**
     * Agrega un nuevo elemento al arbol. El arbol conserva su orden in-order.
     * Parametros: v el vertice en el que nos encontramos en la recursion,
     * elemento el elemento a agregar al arbol y auxVertice el vertice que
     * contiene al elemento a agregar al arbol.
     */
    private void auxAgrega(Vertice v, T elemento, Vertice auxVertice) {
        // Caso 1: El vertice con el elemento a agregar es menor o igual al
        // vertice donde nos encontramos en la recursion.
        if (elemento.compareTo(v.elemento) <= 0) {

            // Si no hay izquierdo, se agrega a la izquierda del vertice actual.
            if (!v.hayIzquierdo()) {
                v.izquierdo = auxVertice;
                v.izquierdo.padre = v;
            }

            // Si hay izquierdo hacemos recursion sobre el vertice izquierdo.
            else
                auxAgrega(v.izquierdo, elemento, auxVertice);
        }

        // Caso 2: El vertice con el elemento a agregar es mayor al vertice donde
        // nos encontramos en la recursion.
        else {

            //  Si no hay derecho se agrega ala derecha del vertice actual.
            if (!v.hayDerecho()) {
                v.derecho = auxVertice;
                v.derecho.padre = v;
            }

            // Si hay derecho, hacemos recursion sobre el vertice derecho.
            else
                auxAgrega(v.derecho, elemento, auxVertice);
        }
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Se busca el vertice que contiene el elemento que queremos eliminaro.
        Vertice eliminado = busca(raiz, elemento);
        Vertice auxiliar;

        // Si no se encuenta el vertice con el elemento a eliminar termina.
        if (eliminado == null)
            return;

        // Intercambiamos el vertice a eliminar por el maximo vertice en el
        // subarbol izquierdo en caso de que exista.
        if (eliminado.hayIzquierdo()) {
            auxiliar = eliminado;
            eliminado = maximoEnSubarbol(eliminado.izquierdo);
            auxiliar.elemento = eliminado.elemento;
        }

        // Caso 1: Es hoja el vertice a eliminar.
        if (!eliminado.hayIzquierdo() && !eliminado.hayDerecho()) {
            if (raiz == eliminado) {
                raiz = null;
            } else if (esHijoIzquierdo(eliminado))
                eliminado.padre.izquierdo = null;
            else
                eliminado.padre.derecho = null;
        }

        // Caso 2: No hay hijo derecho pero si izquierdo en el vertice a
        // eliminar.
        else if (!eliminado.hayDerecho()) {
            if (raiz == eliminado) {
                raiz = raiz.izquierdo;
                raiz.padre = null;
            } else {
                eliminado.izquierdo.padre = eliminado.padre;
                if (esHijoIzquierdo(eliminado))
                    eliminado.padre.izquierdo = eliminado.izquierdo;
                else
                    eliminado.padre.derecho = eliminado.izquierdo;
            }
        }

        // Caso 3: No hay hijo izquierdo pero si derecho en el vertice a
        // eliminar. Se usa else if en lugar de else para mayor claridad.
        else if (!eliminado.hayIzquierdo()) {
            if (raiz == eliminado) {
                raiz = raiz.derecho;
                raiz.padre = null;
            } else {
                eliminado.derecho.padre = eliminado.padre;
                if (esHijoIzquierdo(eliminado))
                    eliminado.padre.izquierdo = eliminado.derecho;
                else
                    eliminado.padre.derecho = eliminado.derecho;
            }
        }

        --elementos;
    }

    /**
     * Busca recursivamente un elemento, a partir del vértice recibido.
     * @param vertice el vértice a partir del cuál comenzar la búsqueda. Puede
     *                ser <code>null</code>.
     * @param elemento el elemento a buscar a partir del vértice.
     * @return el vértice que contiene el elemento a buscar, si se encuentra en
     *         el árbol; <code>null</code> en otro caso.
     */
    @Override protected Vertice busca(Vertice vertice, T elemento) {
        if (vertice == null)
            return null;
        else if (elemento.compareTo(vertice.elemento) == 0)
            return vertice;
        else if (elemento.compareTo(vertice.elemento) < 0)
            return busca(vertice.izquierdo, elemento);
        else
            return busca(vertice.derecho, elemento);
    }

    /**
     * Regresa el vértice máximo en el subárbol cuya raíz es el vértice que
     * recibe.
     * @param vertice el vértice raíz del subárbol del que queremos encontrar el
     *                máximo.
     * @return el vértice máximo el subárbol cuya raíz es el vértice que recibe.
     */
    protected Vertice maximoEnSubarbol(Vertice vertice) {
        if (!vertice.hayDerecho())
            return vertice;
        else
            return maximoEnSubarbol(vertice.derecho);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        // TODO falla en muy raros casos.
        if (vertice == null || !vertice.hayIzquierdo())
            return;

        Vertice v = vertice(vertice);
        Vertice verticeIzquierdo = v.izquierdo;
        verticeIzquierdo.padre = v.padre;
        if (v != raiz)
            if (esHijoIzquierdo(v))
                verticeIzquierdo.padre.izquierdo = verticeIzquierdo;
            else
                verticeIzquierdo.padre.derecho = verticeIzquierdo;

        v.izquierdo = verticeIzquierdo.derecho;
        if (verticeIzquierdo.hayDerecho())
            verticeIzquierdo.derecho.padre = v;

        v.padre = verticeIzquierdo;
        verticeIzquierdo.derecho = v;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        // TODO Falla en muy raros casos.
        if (vertice == null || !vertice.hayDerecho())
            return;

        Vertice v = vertice(vertice);
        Vertice verticeDerecho = v.derecho;
        verticeDerecho.padre = v.padre;
        if (v != raiz)
            if (esHijoIzquierdo(v))
                verticeDerecho.padre.izquierdo = verticeDerecho;
            else
                verticeDerecho.padre.derecho = verticeDerecho;
        else
            raiz = verticeDerecho;

        v.derecho = verticeDerecho.izquierdo;
        if (verticeDerecho.hayIzquierdo())
            verticeDerecho.izquierdo.padre = v;

        v.padre = verticeDerecho;
        verticeDerecho.izquierdo = v;
    }

    // Nos dice si es hijo izquierdo. Usar hayPadre() antes de usar
    // este metodo.
    private boolean esHijoIzquierdo(Vertice v) {
        return v.padre.izquierdo == v;
    }
}

package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para árboles binarios completos.</p>
 * Autor: ivan rodriguez soria.
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios completos. */
    private class Iterador implements Iterator<T> {

        private Cola<ArbolBinario<T>.Vertice> cola;

        /* Constructor que recibe la raíz del árbol. */
        public Iterador() {
            cola = new Cola<>();
            cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !cola.esVacia();
        }

        /* Regresa el elemento siguiente. */
        @Override public T next() {
            Vertice v = cola.saca();
            if (v.hayIzquierdo())
                cola.mete(v.izquierdo);
            if (v.hayDerecho())
                cola.mete(v.derecho);
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
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
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

    private void auxAgrega(Vertice vertice, T elemento, Vertice auxVertice) {
        // Creamos una cola y metemos la raiz del arbol para recorrerlo en
        // orden BFS y encontrar el lugar correcto del vertice a agregar.
         Cola<ArbolBinario<T>.Vertice> cola = new Cola<>();
         Vertice v = vertice;
         cola.mete(v);
         while (!cola.esVacia()) {
             v = cola.saca();

             // Si el vertice que sacamos no tiene hijo izquierdo, metemos el
             // vertice a agregar de lado izquierdo.
             if (!v.hayIzquierdo()) {
                v.izquierdo = auxVertice;
                auxVertice.padre = v;
                break;
             }

             // Si el vertice que sacamos no tiene hijo derecho, metemos el
             // vertice a agregar de lado derecho.
             else if (!v.hayDerecho()) {
                v.derecho = auxVertice;
                auxVertice.padre = v;
                break;
             }

             // Si el vertice en el que nos encontramos tiene ambos hijos,
             // metemos ambos hijos a la cola para seguir recorriendo el arbol.
             cola.mete(v.izquierdo);
             cola.mete(v.derecho);
         }
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Cola<ArbolBinarioCompleto<T>.Vertice> cola = new Cola<>();
        Vertice v = raiz;
        Vertice penultimo = null;
        cola.mete(v);
        boolean encontrado = false;
        while (!cola.esVacia()) {
            v = cola.saca();

            // Si encontramos el vertice a aliminar, intercambiamos su elemento
            // con el elemento del ultimo vertice agregado.
            if (!encontrado && v.elemento.equals(elemento)) {
                v.elemento = ultimoAgregado.elemento;
                encontrado = true;
                --elementos;
            }

            // Buscamos el penultimo elemento agregado actualizando la
            // referencia penultimo en cada iteracion menos la ultima.
            if (v != ultimoAgregado)
                penultimo = v;

            // Si ya se encontro el elemento y estamos en el ultimo vertice,
            // se elimina el vertice buscado.
            if (encontrado && v == ultimoAgregado)

                // Si es la raiz solo se cambian referencias a null.
                if (v.padre == null) {
                    raiz = null;
                    ultimoAgregado = null;
                }

                // Como no es la raiz, se actualiza la referencia del utimo
                // agregado y se elimina el vertice.
                else {
                    ultimoAgregado = penultimo;

                    // Si es hijo izquierdo se elimina el vertice izquierdo del padre.
                    if (v.padre.izquierdo == v)
                        v.padre.izquierdo = null;

                    // Si es hijo derecho se elimina el vertice derecho del padre.
                    else
                        v.padre.derecho = null;
                }

            if (v.hayIzquierdo())
                cola.mete(v.izquierdo);
            if (v.hayDerecho())
                cola.mete(v.derecho);
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}

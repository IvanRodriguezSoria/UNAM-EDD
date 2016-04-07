package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles AVL. La única diferencia
     * con los vértices de árbol binario, es que tienen una variable de clase
     * para la altura del vértice.
     */
    protected class VerticeAVL extends ArbolBinario<T>.Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            super(elemento);
            altura = 0;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        public String toString() {
            // Aquí va su código.
            return elemento + " " + altura + "/" + balance(this);
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null)
                return false;
            if (getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)o;
            return auxEquals(this, vertice);
        }
        
        private boolean auxEquals(VerticeAVL v1, VerticeAVL v2) {
            VerticeAVL v1Izq, v2Izq, v1Der, v2Der;
            
            if (v1 == null && v2 == null)
                return true;

            if ((v1 == null && v2 != null) || (v1 != null && v2 == null) || !v1.elemento.equals(v2.elemento) )
                return false;
                
            if (v1.altura != v2.altura)
                return false;

            v1Izq = verticeAVL(v1.izquierdo);
            v2Izq = verticeAVL(v2.izquierdo);
            v1Der = verticeAVL(v1.derecho);
            v2Der = verticeAVL(v2.derecho);
            return auxEquals(v1Izq, v2Izq) && auxEquals(v1Der, v2Der);
        }
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario. La complejidad en tiempo del método es <i>O</i>(log
     * <i>n</i>) garantizado.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeAVL v = verticeAVL(ultimoAgregado);
        rebalanceaArbol(v);
    }
    
    private void rebalanceaArbol(VerticeAVL v) {
        if (v == null)
            return;
            
        VerticeAVL vi = verticeAVL(v.izquierdo);
        VerticeAVL vd = verticeAVL(v.derecho);
        
        cambiaAltura(v);
        if (v.padre != null)
            cambiaAltura(v.padre);
            
        if (balance(v) == -2) {
            if (balance(vd) == 1) {
                super.giraDerecha(vd);
                cambiaAltura(vd);
                cambiaAltura(vd.padre);
            }
            
            super.giraIzquierda(v);
            cambiaAltura(v);
        }
        
        else if (balance(v) == 2) {
            if (balance(vi) == -1) {
                super.giraIzquierda(vi);
                cambiaAltura(vi);
                cambiaAltura(vi.padre);
            }
            
            super.giraDerecha(v);
            cambiaAltura(v);
        }
        
        rebalanceaArbol(verticeAVL(v.padre) );
    }
    
    private void cambiaAltura(VerticeArbolBinario<T> vertice) {
        VerticeAVL v = verticeAVL(vertice);
        VerticeAVL vi = verticeAVL(v.izquierdo);
        VerticeAVL vd = verticeAVL(v.derecho);
        v.altura = 1 + Math.max(getAltura(vi), getAltura(vd) );
    }
    
    private int balance(VerticeArbolBinario<T> vertice) {
        VerticeAVL v = verticeAVL(vertice);
        VerticeAVL vi = verticeAVL(v.izquierdo);
        VerticeAVL vd = verticeAVL(v.derecho);
        return getAltura(vi) - getAltura(vd);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo. La
     * complejidad en tiempo del método es <i>O</i>(log <i>n</i>) garantizado.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeAVL eliminado = verticeAVL(super.busca(raiz, elemento) );
        VerticeAVL auxiliar;
        
        if (eliminado == null)
            return;
            
        if (eliminado.hayIzquierdo() ) {
            auxiliar = eliminado;
            eliminado = verticeAVL(maximoEnSubarbol(eliminado.izquierdo) );
            auxiliar.elemento = eliminado.elemento;
        }
        
        if (!eliminado.hayIzquierdo() && !eliminado.hayDerecho()) {
            if (raiz == eliminado) {
                raiz = null;
            } else if (esHijoIzquierdo(eliminado))
                eliminado.padre.izquierdo = null;
            else
                eliminado.padre.derecho = null;
        }
        
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
        
        rebalanceaArbol(verticeAVL(eliminado.padre) );
    }
    
    private boolean esHijoIzquierdo(VerticeArbolBinario<T> vertice) {
        VerticeAVL v = verticeAVL(vertice);
        return v.padre.izquierdo == v;
    }

    /**
     * Regresa la altura del vértice AVL.
     * @param vertice el vértice del que queremos la altura.
     * @return la altura del vértice AVL.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeAVL}.
     */
    public int getAltura(VerticeArbolBinario<T> vertice) {
        if (vertice == null)
            return -1;
            
        return verticeAVL(vertice).altura;
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeAVL(elemento);
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * VerticeAVL}). Método auxililar para hacer esta audición en un único
     * lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice AVL.
     * @return el vértice recibido visto como vértice AVL.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeAVL}.
     */
    protected VerticeAVL verticeAVL(VerticeArbolBinario<T> vertice) {
        return (VerticeAVL)vertice;
    }
}

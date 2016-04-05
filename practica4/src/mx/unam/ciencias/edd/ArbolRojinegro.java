package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *  Autor: ivan rodriguez soria
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros son autobalanceados, y por lo tanto las operaciones de
 * inserción, eliminación y búsqueda pueden realizarse en <i>O</i>(log
 * <i>n</i>).
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles rojinegros. La única
     * diferencia con los vértices de árbol binario, es que tienen un campo para
     * el color del vértice.
     */
    protected class VerticeRojinegro extends ArbolBinario<T>.Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            String s = "";
            if (color == Color.NEGRO)
                s = "N";
            else
                s = "R";
                
            return s + "{" + elemento.toString() + "}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null)
                return false;
            if (getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeRojinegro vertice = (VerticeRojinegro)o;
            return auxEquals(this, vertice);
        }
        
        private boolean auxEquals(VerticeRojinegro v1, VerticeRojinegro v2) {
            VerticeRojinegro v1Izq, v2Izq, v1Der, v2Der;
            
            if (v1 == null && v2 == null)
                return true;

            if ((v1 == null && v2 != null) || (v1 != null && v2 == null) || !v1.elemento.equals(v2.elemento) )
                return false;
                
            if (v1.color != v2.color)
                return false;

            v1Izq = verticeRojinegro(v1.izquierdo);
            v2Izq = verticeRojinegro(v2.izquierdo);
            v1Der = verticeRojinegro(v1.derecho);
            v2Der = verticeRojinegro(v2.derecho);
            return auxEquals(v1Izq, v2Izq) && auxEquals(v1Der, v2Der);
        }
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del
     *         mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * VerticeRojinegro}). Método auxililar para hacer esta audición en un único
     * lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice
     *                rojinegro.
     * @return el vértice recibido visto como vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = (VerticeRojinegro)vertice;
        return v;
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        if (!(vertice instanceof ArbolRojinegro.VerticeRojinegro) )
            throw new ClassCastException();
            
        VerticeRojinegro v = verticeRojinegro(vertice);
        return v.color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeRojinegro v = verticeRojinegro(ultimoAgregado);
        v.color = Color.ROJO;
        balanceaAgrega(v);
    }

    private void balanceaAgrega(VerticeRojinegro v) {
        VerticeRojinegro padre;
        VerticeRojinegro abuelo;
        VerticeRojinegro aux;
        
        // Caso 1:
        if (!v.hayPadre()) {
            v.color = Color.NEGRO;
            return;
        }
        // Caso 2:
        padre = verticeRojinegro(v.padre);
        if (getColor(padre) == Color.NEGRO) {
            return;
        }
        // Caso 3:
        abuelo = verticeRojinegro(padre.padre);
        if (existeTio(v) && getTio(v).color == Color.ROJO ) {
            padre.color = Color.NEGRO;
            getTio(v).color = Color.NEGRO;
            abuelo.color = Color.ROJO;
            balanceaAgrega(abuelo);
            return;
        }
            
        // Caso 4:
        if (esVerticeCruzado(v) ) {
            if (esHijoIzquierdo(padre) )
                giraIzquierda(padre);
            else 
                giraDerecha(padre);
            aux = padre;
            padre = v;
            v = aux;
        }
            
        // Caso 5:
        padre.color = Color.NEGRO;
        abuelo.color = Color.ROJO;
        if (esHijoIzquierdo(v) )
            giraDerecha(abuelo);
        else 
            giraIzquierda(abuelo); 
    }

    // Regresa true en caso de que el vertice sea hijo izquierdo y false en otro caso.
    private boolean esHijoIzquierdo(ArbolBinario<T>.Vertice vertice) {
        if (!vertice.hayPadre() )
            return false;
            
        return vertice.padre.izquierdo == vertice;
    }

    // Regresa al tio del vertice. Se debe usar en conjunto con existeTio().
    private VerticeRojinegro getTio(ArbolBinario<T>.Vertice v) {
        if (esHijoIzquierdo(v.padre) )
            return verticeRojinegro(v.padre.padre.derecho);
        else
            return verticeRojinegro(v.padre.padre.izquierdo); 
    }
    
    // Regresa true en caso de que exista un tio del vertice y false en otro caso.
    private boolean existeTio(ArbolBinario<T>.Vertice v) {
        return v.padre.padre.derecho != null && v.padre.padre.izquierdo != null;
    }

    // Regresa true si el vertice es cruzado con su padre, es decir: 
    // Si el padre es hijo derecho y el vertice es hijo izquierdo regresa true.
    // Si el padre es hijo izquierdo y el vertice es hijo derecho regresa true.
    // false en otro caso.
    private boolean esVerticeCruzado(ArbolBinario<T>.Vertice v) {
        return esHijoIzquierdo(v) != esHijoIzquierdo(v.padre);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        VerticeRojinegro v = verticeRojinegro(busca(elemento) );
        VerticeRojinegro eliminado;
        boolean existeFantasma = false;
        
        if (v == null)
            return;
        
        // Buscamos el maximo vertice del subarbol izquierdo.
        // En caso de no existir eliminado es el mismo vertice.    
        if (v.hayIzquierdo() )
            eliminado = verticeRojinegro(maximoEnSubarbol(v.izquierdo) );
        else 
            eliminado = v;
            
        // Intercambia elemento con el vertice maximo del subarbol izquierdo.
        // O con el mismo en caso de no haber subarbol izquierdo.
        v.elemento = eliminado.elemento;
        eliminado.elemento = elemento;
        
        // Si no tiene hijos creamos un vertice fantasma.
        if (!tieneHijos(eliminado) ) {
            eliminado.izquierdo = fantasma();
            existeFantasma = true;
        }
        
        // Subimos el hijo izquierdo o derecho.
        if (eliminado.hayIzquierdo() )    
            eliminado = subirHijoIzquierdo(eliminado);
        else 
            eliminado = subirHijoDerecho(eliminado);
        
        // Cambiamos el color del vertice si este es rojo.
        if (eliminado.color == Color.ROJO)
            eliminado.color = Color.NEGRO;
            
        // Entramos a los 6 casos de rebalanceo.
        balanceaElimina(eliminado);
        
        // Si existe un fantasma llamamos a los caza fantasmas para quitarlo.
        if (existeFantasma)
            ghostBusters(ultimoAgregado);
            
        // Restamos el numero de elementos.
        --elementos;
    }
    
    // TODO 
    private void balanceaElimina(ArbolBinario<T>.Vertice vertice) {
        // TODO 
        VerticeRojinegro hermano, padre, aux, v;
        v = verticeRojinegro(vertice);
        
        // Caso 1:
        if (v.padre == null) {
            raiz = v;
            return;
        }
        
        // Caso 2:
        padre = verticeRojinegro(v.padre);
        hermano = getHermano(v);
        if (hermano != null && hermano.color == Color.ROJO) {
            hermano.color = Color.NEGRO;
            padre.color = Color.ROJO;
            if (esHijoIzquierdo(v))
                giraIzquierda(padre);
            else 
                giraDerecha(padre);
            
            aux = padre;
            padre = v;
            v = aux;
        }
        
        // Caso 3:
        if (padre.color == Color.NEGRO && hermano.color == Color.NEGRO && sonHijosNegros(hermano)) {
            hermano.color = Color.ROJO;
            balanceaElimina(padre);
            return;
        }
        
        // Caso 4:
        if (padre.color == Color.ROJO && hermano.color == Color.NEGRO && sonHijosNegros(hermano)) {
            padre.color = Color.NEGRO;
            hermano.color = Color.ROJO;
            return;
        }
    }
    
    // Regresa al hermano del vertice. Ya se debe saber si existe padre.
    private VerticeRojinegro getHermano(ArbolBinario<T>.Vertice v) {
        if (esHijoIzquierdo(v))
            return verticeRojinegro(v.padre.derecho);
        else 
            return verticeRojinegro(v.padre.izquierdo);
    }
    
    // Regresa true en caso de que los hijos sean negros, false de lo contrario.
    private boolean sonHijosNegros(ArbolBinario<T>.Vertice v) {
        if (!tieneHijos(v) )
            return false;
        
        if (v.derecho != null && v.izquierdo != null)
            return verticeRojinegro(v.derecho).color == Color.NEGRO && verticeRojinegro(v.izquierdo).color == Color.NEGRO;
            
        if (v.derecho == null && v.izquierdo != null)
            return verticeRojinegro(v.izquierdo).color == Color.NEGRO;
            
        if (v.derecho != null && v.izquierdo == null)
            return verticeRojinegro(v.derecho).color == Color.NEGRO;
            
        return false;
    }
    
    // Regresa true en caso de que el vertice tenga almenos un hijo, false en otro caso.
    private boolean tieneHijos(ArbolBinario<T>.Vertice v) {
        return v.izquierdo != null || v.derecho != null;
    }
    
    // Crea un nuevo vertice con su elemento igual a null y color rojo.
    // Actualiza ultimoAgregado para poder eliminarlo sin buscar en todo el arbol.
    private VerticeRojinegro fantasma() {
        VerticeRojinegro v = new VerticeRojinegro(null);
        v.color = Color.NEGRO;
        ultimoAgregado = v;
        return v;
    }
    
    // Intercambia un vertice por su hijo.
    // Regresa el hijo que ocupo el lugar del vertice.
    private VerticeRojinegro subirHijoIzquierdo(ArbolBinario<T>.Vertice v) {
        v.izquierdo.padre = v.padre;
        if (v.hayPadre())
            if (esHijoIzquierdo(v) )
                v.padre.izquierdo = v.izquierdo;
            else
                v.padre.derecho = v.izquierdo;
        else 
            raiz = v.izquierdo;
        
        return verticeRojinegro(v.izquierdo);
    }
    
    // Intercambia un vertice por su hijo.
    // Regresa el hijo que ocupo el lugar del vertice.
    private VerticeRojinegro subirHijoDerecho(ArbolBinario<T>.Vertice v) {
        v.derecho.padre = v.padre;
        if (v.hayPadre())
            if (esHijoIzquierdo(v) )
                v.padre.izquierdo = v.derecho;
            else 
                v.padre.derecho = v.derecho;
        else
            raiz = v.derecho;
        
        return verticeRojinegro(v.derecho);
    }
    
    // Caza fantasmas que destruyen al maldito.
    private void ghostBusters(ArbolBinario<T>.Vertice fantasma) {
        fantasma.padre.izquierdo = null;
        fantasma.padre = null;
    }
    
}

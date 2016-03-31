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
            color = Color.ROJO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            // TODO 
            // Si falla agrega() toString() pasa. Sospecho es culpa de los giros.
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
        balanceaAgrega(v);
    }

    private void balanceaAgrega(VerticeRojinegro v) {
        // TODO 
        // Falla en algunas ocaciones. Sospecho que es por los giros.
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
    private boolean esHijoIzquierdo(VerticeRojinegro vertice) {
        return verticeRojinegro(vertice.padre.izquierdo) == vertice;
    }

    // Regresa al tio del vertice. Se debe usar en conjunto con existeTio().
    private VerticeRojinegro getTio(VerticeRojinegro v) {
        VerticeRojinegro vp = verticeRojinegro(v.padre);
        if (esHijoIzquierdo(vp) )
            return verticeRojinegro(vp.padre.derecho);
        else
            return verticeRojinegro(vp.padre.izquierdo); 
    }
    
    // Regresa true en caso de que exista un tio del vertice y false en otro caso.
    private boolean existeTio(VerticeRojinegro vertice) {
        VerticeRojinegro v = verticeRojinegro(vertice);
        return v.padre.padre.derecho != null && v.padre.padre.izquierdo != null;
    }

    // Regresa true si el vertice es cruzado con su padre, es decir: 
    // Si el padre es hijo derecho y el vertice es hijo izquierdo regresa true.
    // Si el padre es hijo izquierdo y el vertice es hijo derecho regresa true.
    // false en otro caso.
    private boolean esVerticeCruzado(VerticeRojinegro v) {
        VerticeRojinegro vp = verticeRojinegro(v.padre);
        return esHijoIzquierdo(v) != esHijoIzquierdo(vp);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
    }
}

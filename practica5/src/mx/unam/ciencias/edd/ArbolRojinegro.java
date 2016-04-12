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
            if (elemento == null)
                color = Color.NEGRO;
            else 
                color = Color.ROJO;
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
        if (vertice == null)
            return Color.NEGRO;    
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
        // TODO fallo equals una vez.
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
                super.giraIzquierda(padre);
            else 
                super.giraDerecha(padre);
            aux = padre;
            padre = v;
            v = aux;
        }
            
        // Caso 5:
        padre.color = Color.NEGRO;
        abuelo.color = Color.ROJO;
        if (esHijoIzquierdo(v) )
            super.giraDerecha(abuelo);
        else 
            super.giraIzquierda(abuelo); 
    }

    // Regresa true en caso de que el vertice sea hijo izquierdo y false en otro caso.
    private boolean esHijoIzquierdo(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = verticeRojinegro(vertice);
        return verticeRojinegro(v.padre.izquierdo) == v;
    }

    // Regresa al tio del vertice. Se debe usar en conjunto con existeTio().
    private VerticeRojinegro getTio(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = verticeRojinegro(vertice);
        if (esHijoIzquierdo(v.padre) )
            return verticeRojinegro(v.padre.padre.derecho);
        else
            return verticeRojinegro(v.padre.padre.izquierdo); 
    }
    
    // Regresa true en caso de que exista un tio del vertice y false en otro caso.
    private boolean existeTio(VerticeArbolBinario<T> vertice) {
        if (vertice == null)
            return false;
            
        VerticeRojinegro v = verticeRojinegro(vertice);
        
        if (v.padre == null || v.padre.padre == null)
            return false;
            
        return v.padre.padre.derecho != null && v.padre.padre.izquierdo != null;
    }

    // Regresa true si el vertice es cruzado con su padre, es decir: 
    // Si el padre es hijo derecho y el vertice es hijo izquierdo regresa true.
    // Si el padre es hijo izquierdo y el vertice es hijo derecho regresa true.
    // false en otro caso.
    private boolean esVerticeCruzado(VerticeArbolBinario<T> vertice) {
        if (vertice == null)
            return false;
            
        VerticeRojinegro v = verticeRojinegro(vertice);
        
        if (v.padre == null)
            return false;
            
        return esHijoIzquierdo(v) != esHijoIzquierdo(v.padre);
    }
    
    ///////////////////////////////////////////////////////////////////////////////
    // DEBO COMPRENDER MEJOR ESTE BLOQUE.
    ///////////////////////////////////////////////////////////////////////////////
    
    private void eliminaHoja(Vertice eliminar) {
        if (this.raiz == eliminar) {
            this.raiz = null;
            this.ultimoAgregado = null;
        } else if (this.esHijoIzquierdo(eliminar)) {
            eliminar.padre.izquierdo = null;
        } else {
            eliminar.padre.derecho = null;
        }
    }

    private void eliminaSinHijoIzquierdo(Vertice eliminar) {
        if (this.raiz == eliminar) {
            this.raiz = this.raiz.derecho;
            eliminar.derecho.padre = null;
        } else {
            eliminar.derecho.padre = eliminar.padre;
            if (this.esHijoIzquierdo(eliminar)) {
                eliminar.padre.izquierdo = eliminar.derecho;
            } else {
                eliminar.padre.derecho = eliminar.derecho;
            }
        }
        this.elementos--;
    }

    private void eliminaSinHijoDerecho(Vertice eliminar) {
        if (this.raiz == eliminar) {
            this.raiz = this.raiz.izquierdo;
            eliminar.izquierdo.padre = null;
        } else {
            eliminar.izquierdo.padre = eliminar.padre;
            if (this.esHijoIzquierdo(eliminar)) {
                eliminar.padre.izquierdo = eliminar.izquierdo;
            } else {
                eliminar.padre.derecho = eliminar.izquierdo;
            }
        }
        this.elementos--;
    }

    private boolean sonVerticesBicoloreados(VerticeRojinegro v1, VerticeRojinegro v2) {
        return this.esNegro(v1) != this.esNegro(v2);
    }

    private void subirUnicoHijo(Vertice padre) {
        if (!padre.hayIzquierdo()) {
            this.eliminaSinHijoIzquierdo(padre);
        } else {
            this.eliminaSinHijoDerecho(padre);
        }
    }

    private VerticeRojinegro getUnicoHijo(VerticeRojinegro padre) {
        if (padre.hayIzquierdo()) {
            return verticeRojinegro(padre.izquierdo);
        }
        return verticeRojinegro(padre.derecho);
    }

    private VerticeRojinegro getHermano(VerticeRojinegro vertice) {
        if (this.esHijoIzquierdo(vertice)) {
            return verticeRojinegro(vertice.padre.derecho);
        }
        return verticeRojinegro(vertice.padre.izquierdo);
    }

    private boolean esNegro(VerticeRojinegro vertice) {
        return vertice == null || vertice.color == Color.NEGRO;
    }

    private void rebalanceoElimina(VerticeRojinegro vertice) {
        VerticeRojinegro hermano, padre, sobrinoIzq, sobrinoDer;
        
        // Caso 1: El padre es null.
        if (!vertice.hayPadre()) {
            // Asignamos la raiz al vertice.
            this.raiz = vertice;
            // Terminamos
            return;
        }
        padre = verticeRojinegro(vertice.padre);
        hermano = this.getHermano(vertice);
        
        // Caso 2: El hermano es rojo.
        if (!this.esNegro(hermano)) {
            // Coloreamos la hermano de Negro.
            hermano.color = Color.NEGRO;
            // Coloreamos la padre de Rojo.
            padre.color = Color.ROJO;
            // Giramos sobre al padre en la direccion del vertice.
            if (this.esHijoIzquierdo(vertice)) {
                super.giraIzquierda(padre);
            } else {
                super.giraDerecha(padre);
            }
            // Cambiamos referencias de padre y hermano.
            padre = verticeRojinegro(vertice.padre);
            hermano = this.getHermano(vertice);
        }
        sobrinoIzq = verticeRojinegro(hermano.izquierdo);
        sobrinoDer = verticeRojinegro(hermano.derecho);
        
        // Caso 3: El padre, el hermano y los sobrinos son negros.
        if (this.esNegro(hermano) && this.esNegro(sobrinoIzq) && this.esNegro(sobrinoDer)) {
            if (this.esNegro(padre)) {
                // Coloreamos al hermano de Rojo.
                hermano.color = Color.ROJO;
                // Hacemos recursion sobre el padre.
                this.rebalanceoElimina(padre);
                // Terminamos
                return;
            }
            
            // Caso 4: Hermano y sobrinos negros, padre rojo.
            
            // Coloreamos al padre de Negro.
            padre.color = Color.NEGRO;
            // Coloreamos al hermano de Rojo.
            hermano.color = Color.ROJO;
            // Terminados.
            return;
        }
        
        // Caso 5: Los sobrinos son bicoloreados y el sobrino cruzado es Negro.
        if (this.sonVerticesBicoloreados(sobrinoIzq, sobrinoDer) && (
            // Evaluando si un sobrino es cruzado
            (this.esNegro(sobrinoIzq) && !this.esHijoIzquierdo(vertice)) || (this.esNegro(sobrinoDer) && this.esHijoIzquierdo(vertice)))) {
            // Coloreamos al sobrino Rojo de Negro
            if (!this.esNegro(sobrinoIzq)) {
                sobrinoIzq.color = Color.NEGRO;
            } else {
                sobrinoDer.color = Color.NEGRO;
            }
            // Coloreamos al hermano de Rojo
            hermano.color = Color.ROJO;
            //Giramos sobre el hermano en la direccion contraria al vertice
            if (this.esHijoIzquierdo(vertice)) {
                super.giraDerecha(hermano);
            } else {
                super.giraIzquierda(hermano);
            }
            hermano = this.getHermano(vertice);
            sobrinoIzq = verticeRojinegro(hermano.izquierdo);
            sobrinoDer = verticeRojinegro(hermano.derecho);
        }
        
        // Caso 6: El sobrino cruzado es rojo.
        
        // Coloreamos al hermano del color del padre
        hermano.color = padre.color;
        // Coloreamos al padre de negro
        padre.color = Color.NEGRO;
        // Coloreamos al sobrino cruzado de Negro
        if (this.esHijoIzquierdo(vertice)) {
            sobrinoDer.color = Color.NEGRO;
        } else {
            sobrinoIzq.color = Color.NEGRO;
        }
        // Giramos sobre el padre en la direccion del vertice
        if (this.esHijoIzquierdo(vertice)) {
            super.giraIzquierda(padre);
        } else {
            super.giraDerecha(padre);
        }
    }

    private void eliminarFantasma(VerticeRojinegro eliminar) {
        if (eliminar.elemento == null) {
            eliminaHoja(eliminar);
        }
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeRojinegro aux, hijo;
        // Buscamos el vertice que tiene el elemento que queremos eliminar.
        VerticeRojinegro eliminar = this.verticeRojinegro(super.busca(elemento));
        // Si no lo encontro, simplemente terminamos.
        if (eliminar == null) {
            return;
        }
        // Si tiene hijo izquierdo.
        if (eliminar.hayIzquierdo()) {
            // Obtenemos el Vertice que es maximo en el subarbol izquierdo del vertice que
            // queremos eliminar.
            aux = verticeRojinegro(maximoEnSubarbol(eliminar.izquierdo));
            // Intercambiamos el elemento que tiene el vertice que queremos eliminar
            // con el del maximo en el subarbol izquierdo.
            eliminar.elemento = aux.elemento;
            // Ahora ya queremos eliminar al maximo del subarbol izquierdo.
            eliminar = aux;
        }
        // Vertificamos si el que queremos eliminar es hoja.
        if (!eliminar.hayIzquierdo() && !eliminar.hayDerecho()) {
            // Creamos un vertice fantasma y lo ponemos como hijo del vertice que queremos
            // eliminar.
            eliminar.izquierdo = this.nuevoVertice(null);
            eliminar.izquierdo.padre = eliminar;
        }
        // En esta parte el vertice que queremos eliminar siempre tiene solo un hijo.
        // Obtenemos el unico hijo que tiene el vertice que queremos eliminar.
        hijo = getUnicoHijo(eliminar);
        // Subimos el unico hijo del vertice que queremos eliminar.
        this.subirUnicoHijo(eliminar);
        // Si tenian diferentes colores el hijo y el vertice que queremos eliminar, rebalanceamos.
        if (!this.sonVerticesBicoloreados(eliminar, hijo)) {
            hijo.color = Color.NEGRO;
            this.rebalanceoElimina(hijo);
        } else {
            hijo.color = Color.NEGRO;
        }
        // Eliminamos el vertice fantasma si lo hay
        this.eliminarFantasma(hijo);
    }
    
    ///////////////////////////////////////////////////////////////////////////////
    // DEBO COMPRENDER MEJOR ESTE BLOQUE.
    ///////////////////////////////////////////////////////////////////////////////
    
    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles Rojinegro
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles Rojinegros no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }
    
    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles Rojinegros
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles Rojinegros no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}

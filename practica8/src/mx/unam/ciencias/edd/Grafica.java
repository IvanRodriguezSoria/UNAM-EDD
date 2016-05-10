package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** 
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase privada para iteradores de gráficas. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Grafica<T>.Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            return iterador.next().getElemento();
        }

        /* No lo implementamos: siempre lanza una excepción. */
        @Override public void remove() {
            throw new UnsupportedOperationException("Eliminar con el iterador " +
                                                    "no está soportado");
        }
    }

    /* Vecinos para gráficas; un vecino es un vértice y el peso de la arista que
     * los une. Implementan VerticeGrafica. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vecino del vértice. */
        public Grafica<T>.Vertice vecino;
        /* El peso de vecino conectando al vértice con el vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Grafica<T>.Vertice vecino, double peso) {
            this.vecino = vecino;
            this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T getElemento() {
            return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            return vecino.vecinos.getLongitud();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            return vecino.color;
        }

        /* Define el color del vecino. */
        @Override public void setColor(Color color) {
            vecino.color = color;
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecino.vecinos;
        }
    }

    /* Vertices para gráficas; implementan la interfaz ComparableIndexable y
     * VerticeGrafica */
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* La lista de vecinos del vértice. */
        public Lista<Grafica<T>.Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
            color = Color.NINGUNO;
            vecinos = new Lista<Vecino>();
            distancia = -1;
            indice = 0; // TODO. No se realmente cual es el indice inicial.
        }

        /* Regresa el elemento del vértice. */
        @Override public T getElemento() {
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return vecinos.getLongitud();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            return color;
        }

        /* Define el color del vértice. */
        @Override public void setColor(Color color) {
            this.color = color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            if (distancia > vertice.distancia)
                return 1;
            else if (distancia < vertice.distancia)
                return -1;
            else 
                return 0;
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue de la vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Lista<Vertice>();
        aristas = 0;
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return vertices.getLongitud();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null || contiene(elemento) )
            throw new IllegalArgumentException();
            
        Vertice v = new Vertice(elemento);
        vertices.agregaFinal(v);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la vecino que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        
        // Llamo al metodo conecta con peso igual a -1 (Pero por omision).
        conecta(a, b, -1);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b, double peso) {
        
        // Obtengo los vertices que contienen a los elementos a y b.
        // El metodo verticeAux() ya avienta una excepcion NoSuchElementException de ser necesaria.
        Vertice va = verticeAux(a);
        Vertice vb = verticeAux(b);
        
        if (!vertices.contiene(va) || !vertices.contiene(vb) )
            throw new NoSuchElementException();
            
        // Veo si estan conectados o no.
        Vecino vaVecino = vecino(va, vb);
        Vecino vbVecino = vecino(vb, va);
        
        if (a == b || vaVecino != null || vbVecino != null )
            throw new IllegalArgumentException();
            
        vaVecino = new Vecino(va, peso);
        vbVecino = new Vecino(vb, peso);
            
        // Agrego los vertices va y vb a la lista vecinos de los dos vertices.
        // De esta manera se hace la coneccion entre los vertices.
        va.vecinos.agregaFinal(vbVecino);
        vb.vecinos.agregaFinal(vaVecino);
        
        ++aristas;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        
        // Obtengo los vertices que contienen a los elementos a y b.
        // El metodo verticeAux() ya avienta una excepcion NoSuchElementException de ser necesaria.
        Vertice va = verticeAux(a);
        Vertice vb = verticeAux(b);
        
        if (!vertices.contiene(va) || !vertices.contiene(vb) )
            throw new NoSuchElementException();
            
        // Busco si estan conectados o no.
        Vecino vaVecino = vecino(va, vb);
        Vecino vbVecino = vecino(vb, va);
        
        if (a == b || vaVecino == null || vbVecino == null)
            throw new IllegalArgumentException();
        
        // Elimino los vertices va y vb de la lista vecinos de cada vertice.
        // De esta manera elimino la coneccion.
        va.vecinos.elimina(vbVecino);
        vb.vecinos.elimina(vaVecino);
        
        --aristas;
    }
    
    // Si v esta en la lista de vecinos de v1, regresa v como vecino. Null en otro caso.
    private Vecino vecino(Vertice v, Vertice v1) {
        for (Vecino v2 : v1.vecinos)
            if (v2.getElemento().equals(v.getElemento() ) )
                return v2;
        return null;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <tt>true</tt> si el elemento está contenido en la gráfica,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        for (Vertice v : vertices)
            if (v.elemento.equals(elemento) )
                return true;
                
        return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        // El metodo verticeAux() ya avienta una excepcion NoSuchElementException de ser necesaria.
        Vertice v = verticeAux(elemento);
        destruyeConexiones(v);
        vertices.elimina(v);
    }
    
    // Metodo auxiliar para eliminar todas las conexiones.
    private void destruyeConexiones(Vertice v) {
        for (Vertice v1 : vertices) {
            for (Vecino v2 : v1.vecinos)
                if (v.getElemento().equals(v2.getElemento() ) ) {
                    v1.vecinos.elimina(v2);
                    --aristas;
                }
        }
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <tt>true</tt> si a y b son vecinos, <tt>false</tt> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        Vertice va = verticeAux(a);
        for (Vecino v : va.vecinos)
            if (v.getElemento().equals(b) )
                return true;
                
        return false;
    }

    /**
     * Regresa el peso de la vecino que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la vecino que comparten los vértices que contienen a
     *         los elementos recibidos, o -1 si los elementos no están
     *         conectados.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public double getPeso(T a, T b) {
        // El metodo verticeAux() ya avienta NoSuchElementException en caso de ser necesario.
        Vertice v1 = verticeAux(a);
        Vertice v2 = verticeAux(b);
        
        Vecino v3 = vecino(v1, v2);
        Vecino v4 = vecino(v2, v1);
        
        if (v3 == null || v4 == null)
            return -1;
            
        return v4.peso;
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        for (Vertice v : vertices)
            if (v.elemento.equals(elemento) )
                return v;
                
        throw new NoSuchElementException();
    }
    
    // Metodo auxiliar que regresa el vertice que contiene el elemento dado.
    private Vertice verticeAux(T elemento) {
        for (Vertice v : vertices)
            if (v.elemento.equals(elemento) )
                return v;
                
        throw new NoSuchElementException();
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        for (Vertice v : vertices)
            accion.actua(v);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        
        // Creo una cola y llamo al metodo recorrido que recorre la grafica usando la cola.
        Cola<Vertice> cola = new Cola<>();
        recorrido(elemento, accion, cola);
        
        // Cambio el color de todos los vertices a NINGUNO (Los desmarco).
        paraCadaVertice(v3 -> v3.setColor(Color.NINGUNO) );
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        
        // Creo una pila y llamo al metodo recorrido que recorre la grafica usando la pila.
        Pila<Vertice> pila = new Pila<>();
        recorrido(elemento, accion, pila);
        
        // Cambio el color de todos los vertices a NINGUNO (Los desmarco).
        paraCadaVertice(v3 -> v3.setColor(Color.NINGUNO) );
    }

    // Metodo auxiliar que recorre la grafica usando una estrcutura de datos cola o pila.
    // Recibe el elemento del vertice con que iniciaremos el recorrido, la accion que se 
    // realizara en cada uno de los vertice y la estructura de la que nos ayudaremos para 
    // recorrer la grafica.
    private void recorrido(T elemento, AccionVerticeGrafica<T> accion, MeteSaca<Vertice> edd) {
		
        // Obtengo al vertice que contiene al elemento.
        // El metodo verticeAux() ya avienta una excepcion NoSuchElementException de ser necesaria.
        Vertice v1 = verticeAux(elemento);
        Vertice aux = null;
        
        // Marco el vertice con el que iniciare cambiando su color.
        v1.setColor(Color.ROJO);
        edd.mete(v1);
        while (!edd.esVacia() ) {
            aux = edd.saca();
            accion.actua(aux);
            
            // Si el vertice esta marcado no lo meteremos a la edd, de otra forma lo meteremos 
            // y cambiaremos su color para no volverlo a meter.
            for (Vecino v2 : aux.vecinos)
                if (v2.getColor() != Color.ROJO) {
                    v2.setColor(Color.ROJO);
                    edd.mete(v2.vecino);
                }
        }
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacio() {
        return vertices.esVacio();
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <tt>a</tt> y
     *         <tt>b</tt>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        Lista<VerticeGrafica<T>> lista = new Lista<>();
        Vertice v1 = verticeAux(origen);
        v1.distancia = 0;
        Vertice v2 = verticeAux(destino);
        
        calculaDistancia(v1);
        
        return getListaTrayectoria(v2, lista).reversa();
    }
    
    // Calcula la distancia desde el origen a todos los vertices.
    // Recive el vertice origen desde donde se calculara la distancia.
    private void calculaDistancia(Vertice origen) {
        for (Vecino v : origen.vecinos)
            if (v.vecino.distancia > origen.distancia + 1 || v.vecino.distancia == -1) {
                v.vecino.distancia = origen.distancia + 1;
                calculaDistancia(v.vecino);
            }
    }
    
    // Regresa una lista que contiene el camino mas corto desde un vertice destino hasta
    // un vertice origen (Osea empieza del final).
    // Recive el vertice destino y la lista que se regresara con la trayectoria.
    private Lista<VerticeGrafica<T>> getListaTrayectoria(Vertice destino, Lista<VerticeGrafica<T>> lista) {
        
        lista.agregaFinal(destino);
        
        // Si la distancia del vertice con el que entramos a la recursion es 0 significa que llegamos
        // al origen y acabamos. Si es -1 significa que no hay camino desde el origen al destino y terminamos.
        // Cambiamos la distancia de todos los vertices a -1 y regresamos la lista. 
        if (destino.distancia == -1 || destino.distancia == 0) {
            for (Vertice v : vertices)
                v.distancia = -1;
            return lista;
        }
        
        // Buscamos el vertice con la distancia mas pequeña de entre los vecinos y entramos a la recursion.
        Vertice aux = destino;
        for (Vecino v : destino.vecinos)
            if (v.vecino.distancia < aux.distancia)
                aux = v.vecino;
        
        return getListaTrayectoria(aux, lista);
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <tt>origen</tt> y
     *         el vértice <tt>destino</tt>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        Lista<VerticeGrafica<T>> lista = new Lista<>();
        Vertice v1 = verticeAux(origen);
        v1.distancia = 0;
        Vertice v2 = verticeAux(destino);
        
        calculaTrayectoriaPesoMinimo(v1);
        
        return getListaDijkstra(v2, lista).reversa();
    }
    
    private void calculaTrayectoriaPesoMinimo(Vertice origen) {
        for (Vecino v : origen.vecinos)
            if (origen.distancia + v.peso < v.vecino.distancia || v.vecino.distancia == -1) {
                v.vecino.distancia = origen.distancia + v.peso;
                calculaTrayectoriaPesoMinimo(v.vecino);
            }
    }
    
    private Lista<VerticeGrafica<T>> getListaDijkstra(Vertice destino, Lista<VerticeGrafica<T>> lista) {
        
        lista.agregaFinal(destino);
        
        // Si la distancia del vertice con el que entramos a la recursion es 0 significa que llegamos
        // al origen y acabamos. Si es -1 significa que no hay camino desde el origen al destino y terminamos.
        // Cambiamos la distancia de todos los vertices a -1 y regresamos la lista. 
        if (destino.distancia == -1 || destino.distancia == 0) {
            for (Vertice v : vertices)
                v.distancia = -1;
            return lista;
        }
        
        // Buscamos el vertice con la distancia mas pequeña de entre los vecinos y entramos a la recursion.
        Vertice aux = destino;
        for (Vecino v : destino.vecinos)
            if (aux.distancia - v.peso == v.vecino.distancia)
                aux = v.vecino;
        
        return getListaTrayectoria(aux, lista);
    }
}

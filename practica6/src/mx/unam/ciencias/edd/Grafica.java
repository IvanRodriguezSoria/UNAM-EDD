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
            throw new UnsupportedOperationException();
        }
    }

    /* Vertices para gráficas; implementan la interfaz VerticeGrafica */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* Lista de vecinos. */
        public Lista<Grafica<T>.Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
            color = Color.NINGUNO;
            vecinos = new Lista<Grafica<T>.Vertice>();
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

        /* Regresa un iterador para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return null;
        }
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
    public int getElementos() {
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
        if (this.contiene(elemento) ) {
            throw new IllegalArgumentException();
        }
            
        Vertice v = new Vertice(elemento);
        vertices.agregaFinal(v);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
		
		// Obtengo los vertices que contienen a los elementos a y b.
		// El metodo vertice() ya avienta una excepcion NoSuchElementException de ser necesaria.
        Vertice va = (Vertice) this.vertice(a);
        Vertice vb = (Vertice) this.vertice(b);
        
        if (va == vb) {
            throw new IllegalArgumentException();
        }
            
        if (va.vecinos.contiene(vb) ) {
            throw new IllegalArgumentException();
        }
            
		// Agrego los vertices va y vb a la lista vecinos de los dos vertices.
		// De esta manera se hace la coneccion entre los vertices.
        va.vecinos.agregaFinal(vb);
        vb.vecinos.agregaFinal(va);
        
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
		// El metodo vertice() ya avienta una excepcion NoSuchElementException de ser necesaria.
        Vertice va = (Vertice) this.vertice(a);
        Vertice vb = (Vertice) this.vertice(b);
        
        if (va == vb) {
            throw new IllegalArgumentException();
        }
            
        if (!va.vecinos.contiene(vb) ) {
            throw new IllegalArgumentException();
        }
            
		// Elimino los vertices va y vb de la lista vecinos de cada vertice.
		// De esta manera elimino la coneccion.
        va.vecinos.elimina(vb);
        vb.vecinos.elimina(va);
        
        --aristas;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <tt>true</tt> si el elemento está contenido en la gráfica,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        for (Vertice v : vertices) {
            if (v.elemento.equals(elemento) ) {
                return true;
            }
        }
                
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
		
		// Obtengo el vertice que contiene al elemento.
		// El metodo vertice() ya avienta una excepcion NoSuchElementException de ser necesaria.
        Vertice v = (Vertice) this.vertice(elemento);
        vertices.elimina(v);
		
		// Elimino la conexion del vertice eliminado con cada uno de los vertices
		// eliimnando el vertice eliminado de la lista vecinos en cada uno de los vertices.
        for (Vertice vertice : vertices) {
            if (vertice.vecinos.contiene(v) ) {
                vertice.vecinos.elimina(v);
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
        Vertice va = (Vertice) this.vertice(a);
        Vertice vb = (Vertice) this.vertice(b);
        
        return va.vecinos.contiene(vb);
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        for (Vertice v : vertices) {
            if (v.elemento.equals(elemento) ) {
                return v;
            }
        }
                
        throw new NoSuchElementException();
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
		for (Vertice v : vertices) {
			accion.actua(v);
		}
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
		
		// Obtengo al vertice que contiene al elemento.
		// El metodo vertice() ya avienta una excepcion NoSuchElementException de ser necesaria.
		Vertice v1 = (Vertice) this.vertice(elemento);
		Vertice aux = null;
		Cola<Vertice> cola = new Cola<>();
		
		// Marco el vertice con el que iniciare cambiando su color.
		v1.setColor(Color.ROJO);
		cola.mete(v1);
		while (!cola.esVacia() ) {
			aux = cola.saca();
			accion.actua(aux);
			
			// Si el vertice esta marcado no lo meteremos a la cola, de otra forma lo meteremos 
			// y cambiaremos su color para no volverlo a meter.
			for (Vertice v2 : aux.vecinos) {
				if (v2.getColor() != Color.ROJO) {
					v2.setColor(Color.ROJO);
					cola.mete(v2);
				}
			}
		}
		
		// Cambio el color de todos los vertices a NINGUNO (Los desmarco).
		this.paraCadaVertice(v3 -> v3.setColor(Color.NINGUNO) );
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
		
		// Obtengo al vertice que contiene al elemento.
		// El metodo vertice() ya avienta una excepcion NoSuchElementException de ser necesaria.
		Vertice v1 = (Vertice) this.vertice(elemento);
		Vertice aux = null;
		Pila<Vertice> pila = new Pila<>();
		
		// Marco el vertice con el que iniciare cambiando su color.
		v1.setColor(Color.ROJO);
		pila.mete(v1);
		while (!pila.esVacia() ) {
			aux = pila.saca();
			accion.actua(aux);
			
			// Si el vertice esta marcado no lo meteremos a la cola, de otra forma lo meteremos 
			// y cambiaremos su color para no volverlo a meter.
			for (Vertice v2 : aux.vecinos) {
				if (v2.getColor() != Color.ROJO) {
					v2.setColor(Color.ROJO);
					pila.mete(v2);
				}
			}
		}
		
		// Cambio el color de todos los vertices a NINGUNO (Los desmarco).
		this.paraCadaVertice(v3 -> v3.setColor(Color.NINGUNO) );
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
}

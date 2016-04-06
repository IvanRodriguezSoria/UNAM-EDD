package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 * Autor: ivan rodriguez soria.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     */
    @Override public void mete(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
            
        Nodo n = new Nodo(elemento);
        if(this.esVacia()) {
            cabeza = n;
            rabo = cabeza;
        } else {
            rabo.siguiente = n;
            rabo = rabo.siguiente;
        }
    }
}
